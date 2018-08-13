/**
 *  PDVM
 *  Copyright (C) 2017-2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.pdvm;

import java.nio.ByteBuffer;

import static org.atoiks.games.pdvm.Opcode.*;

public class Core implements Unit {

    private final CPU unit;

    private ByteBuffer code = ByteBuffer.allocateDirect(0);

    private Memory mem = null;

    // Absence of value = null, has value = whatever the value is
    public Short inputBuffer = null;

    // Registers
    public short a;
    public short p;
    public int c;
    public int stackPointer;
    public int instrPointer;

    public Core(final CPU unit) {
        this.unit = unit;
    }

    public void attachCode(final byte[] code) {
        this.code = ByteBuffer.wrap(code);
    }

    @Override
    public void reset() {
        // This does not reset the code
        a = 0;
        p = 0;
        c = 0;
        stackPointer = 0;
        instrPointer = 0;
    }

    @Override
    public void invokeNext() {
        if (instrPointer >= code.capacity()) return;

        final int op = fetch8Bit();
        switch (op) {
            case OP_HLT: --instrPointer; return;
            case OP_CH2A: a = (short) (c >> Short.SIZE); break;
            case OP_CH2P: p = (short) (c >> Short.SIZE); break;
            case OP_CL2A: a = (short) c; break;
            case OP_CL2P: p = (short) c; break;
            case OP_AP2C: c = (a << Short.SIZE) | Short.toUnsignedInt(p); break;
            case OP_SEND:
                if (unit.getCore(p).inputBuffer == null) {
                    // Send value in register A
                    unit.getCore(p).inputBuffer = a;
                } else {
                    // Value already exists.
                    // Rewind PC, this operation blocks
                    --instrPointer;
                }
                break;
            case OP_RECV:
                if (this.inputBuffer == null) {
                    // Waits for other core to send value
                    // Rewind PC, this operation blocks
                    --instrPointer;
                } else {
                    // Store value into register A
                    a = this.inputBuffer;
                    this.inputBuffer = null;
                }
                break;
            case OP_LDA: a = (short) fetch16Bit(); break;
            case OP_LDP: p = (short) fetch16Bit(); break;
            case OP_JMP: instrPointer = fetch32Bit(); break;
            case OP_JALP: {
                final int addr = fetch32Bit();
                if (a < p) instrPointer = addr;
                break;
            }
            case OP_JAGP: {
                final int addr = fetch32Bit();
                if (a > p) instrPointer = addr;
                break;
            }
            case OP_JAEP: {
                final int addr = fetch32Bit();
                if (a == p) instrPointer = addr;
                break;
            }
            case OP_JANP: {
                final int addr = fetch32Bit();
                if (a != p) instrPointer = addr;
                break;
            }
            case OP_STHA:
                mem.data.put(calculateEffectiveAddress(fetch16Bit(), fetch8Bit()), (byte) a);
                break;
            case OP_STFA:
                mem.data.putShort(calculateEffectiveAddress(fetch16Bit(), fetch8Bit()), a);
                break;
            case OP_LDHA:
                a = mem.data.get(calculateEffectiveAddress(fetch16Bit(), fetch8Bit()));
                break;
            case OP_LDFA:
                a = mem.data.getShort(calculateEffectiveAddress(fetch16Bit(), fetch8Bit()));
                break;
            case OP_SWAP: {
                final short tmp = p;
                p = a;
                a = tmp;
                break;
            }
            case OP_PHC:
                mem.data.putInt(stackPointer, c);
                stackPointer += Integer.BYTES;
                break;
            case OP_PPC:
                stackPointer -= Integer.BYTES;
                c = mem.data.getInt(stackPointer);
                break;
            case OP_RET:
                stackPointer -= Integer.BYTES;
                instrPointer = mem.data.getInt(stackPointer);
                break;
            case OP_JSR: {
                final int newIp = fetch32Bit();
                mem.data.putInt(stackPointer, instrPointer);
                stackPointer += Integer.BYTES;
                instrPointer = newIp;
                break;
            }
            case OP_C2S: instrPointer = c; break;
            case OP_S2C: c = instrPointer; break;
            case OP_SWSC: {
                final int tmp = c;
                c = instrPointer;
                instrPointer = tmp;
                break;
            }
            case OP_LDS: stackPointer = fetch32Bit(); break;
            case OP_ALU: c = handleALU(fetch8Bit()); break;
            default:
                throw new IllegalStateException("PANIC: Unknown opcode " + op);
        }
    }

    private int handleALU(final int k) {
        // Behaviour is specified by Opcode.java
        final int regValue = getRegisterValueFromIndex(k & 0x0F);
        switch (k & 0xF0) {
            case 0x00: return c + regValue;
            case 0x10: return c - regValue;
            case 0x20: return c * regValue;
            case 0x30: return c / regValue;
            case 0x40: return c % regValue;
            case 0x50: return regValue;
            default:   return -1;
        }
    }

    private int calculateEffectiveAddress(final int raw, int k) {
        // Behaviour is specified by Opcode.java
        final int regValue = getRegisterValueFromIndex(k & 0x0F);
        switch (k & 0xF0) {
            case 0x00: return raw;
            case 0x10: return raw + regValue;
            case 0x20: return mem.data.getShort(raw + regValue);
            case 0x30: return mem.data.getShort(raw) + regValue;
            default:   return -1;
        }
    }

    private int getRegisterValueFromIndex(final int k) {
        switch (k) {
            case 0:  return a;
            case 1:  return p;
            case 2:  return c;
            case 3:  return instrPointer;
            case 4:  return stackPointer;
            default: return -1;
        }
    }

    private int fetch8Bit() {
        final byte k = code.get(instrPointer);
        instrPointer += Byte.BYTES;
        return Byte.toUnsignedInt(k);
    }

    private int fetch16Bit() {
        final short k = code.getShort(instrPointer);
        instrPointer += Short.BYTES;
        return Short.toUnsignedInt(k);
    }

    private int fetch32Bit() {
        final int k = code.getInt(instrPointer);
        instrPointer += Integer.BYTES;
        return k;
    }

    @Override
    public void mapMemory(final Memory mem) {
        this.mem = mem;
    }
}