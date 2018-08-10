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

import static org.atoiks.games.pdvm.Opcode.*;

public class Core implements Unit {

    private final CPU unit;

    private byte[] code = new byte[0];

    // Absence of value = null, has value = whatever the value is
    public Short inputBuffer = null;

    // Registers
    public short a;
    public short p;
    public int c;
    public int programCounter;

    public Core(final CPU unit) {
        this.unit = unit;
    }

    public void attachCode(final byte[] code) {
        this.code = code;
    }

    @Override
    public void reset() {
        // This does not reset the code
        a = 0;
        p = 0;
        c = 0;
        programCounter = 0;
    }

    @Override
    public void invokeNext() {
        if (programCounter >= code.length) return;

        final byte op = code[programCounter++];
        switch (op) {
            case OP_HLT: return;
            case OP_ADD_A: c += a; break;
            case OP_ADD_P: c += p; break;
            case OP_SUB_A: c -= a; break;
            case OP_SUB_P: c -= p; break;
            case OP_MUL_A: c *= a; break;
            case OP_MUL_P: c *= p; break;
            case OP_DIV_A: c /= a; break;
            case OP_DIV_P: c /= p; break;
            case OP_MOD_A: c %= a; break;
            case OP_MOD_P: c %= p; break;
            case OP_A2C: c = a; break;
            case OP_P2C: c = p; break;
            case OP_CH2A: a = (short) (a >> Short.BYTES); break;
            case OP_CH2P: p = (short) (a >> Short.BYTES); break;
            case OP_CL2A: c = (short) a; break;
            case OP_CL2P: c = (short) p; break;
            case OP_AP2C: c = (a << Short.BYTES) | p; break;
            case OP_SEND:
                if (unit.getCore(p).inputBuffer == null) {
                    // Send value in register A
                    unit.getCore(p).inputBuffer = a;
                } else {
                    // Value already exists.
                    // Rewind PC, this operation blocks
                    --programCounter;
                }
                break;
            case OP_RECV:
                if (this.inputBuffer == null) {
                    // Waits for other core to send value
                    // Rewind PC, this operation blocks
                    --programCounter;
                } else {
                    // Store value into register A
                    a = this.inputBuffer;
                    this.inputBuffer = null;
                }
                break;
            case OP_LDA: a = fetch16Bit(); break;
            case OP_LDP: p = fetch16Bit(); break;
            case OP_JMP: programCounter = fetch32Bit(); break;
            case OP_JALP: {
                final int addr = fetch32Bit();
                if (a < p) programCounter = addr;
                break;
            }
            case OP_JAGP: {
                final int addr = fetch32Bit();
                if (a > p) programCounter = addr;
                break;
            }
            case OP_JAEP: {
                final int addr = fetch32Bit();
                if (a == p) programCounter = addr;
                break;
            }
            case OP_JANP: {
                final int addr = fetch32Bit();
                if (a != p) programCounter = addr;
                break;
            }
            default:
                throw new IllegalStateException("PANIC: Unknown opcode " + op);
        }
    }

    private short fetch16Bit() {
        final byte hb = code[programCounter++];
        final byte lb = code[programCounter++];
        return (short) ((hb << Byte.BYTES) | lb);
    }

    private int fetch32Bit() {
        final short hs = fetch16Bit();
        final short ls = fetch16Bit();
        return (int) ((hs << Short.BYTES) | ls);
    }
}