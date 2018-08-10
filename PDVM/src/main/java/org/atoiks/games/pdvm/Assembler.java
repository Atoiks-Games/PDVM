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

import java.util.HashMap;

import static org.atoiks.games.pdvm.Opcode.*;

public class Assembler {

    public static byte[] assemble(final String txt) {
        final HashMap<String, Integer> lbladdr = new HashMap<>();
        final String[] tokens = txt.split("\\s+");
        int address = 0;

        // Calculate label offsets
        outer:
        for (int i = 0; i < tokens.length; ++i) {
            final String op = tokens[i];
            switch (op.toLowerCase()) {
                case "hlt":   address += Byte.BYTES; break;
                case "add_a": address += Byte.BYTES; break;
                case "add_p": address += Byte.BYTES; break;
                case "sub_a": address += Byte.BYTES; break;
                case "sub_p": address += Byte.BYTES; break;
                case "mul_a": address += Byte.BYTES; break;
                case "mul_p": address += Byte.BYTES; break;
                case "div_a": address += Byte.BYTES; break;
                case "div_p": address += Byte.BYTES; break;
                case "mod_a": address += Byte.BYTES; break;
                case "mod_p": address += Byte.BYTES; break;
                case "a2c":   address += Byte.BYTES; break;
                case "p2c":   address += Byte.BYTES; break;
                case "ch2a":  address += Byte.BYTES; break;
                case "ch2p":  address += Byte.BYTES; break;
                case "cl2a":  address += Byte.BYTES; break;
                case "cl2p":  address += Byte.BYTES; break;
                case "ap2c":  address += Byte.BYTES; break;
                case "send":  address += Byte.BYTES; break;
                case "recv":  address += Byte.BYTES; break;
                case "lda":   address += Byte.BYTES + Short.BYTES; ++i; break;
                case "ldp":   address += Byte.BYTES + Short.BYTES; ++i; break;
                case "jmp":   address += Byte.BYTES + Integer.BYTES; ++i; break;
                case "jalp":  address += Byte.BYTES + Integer.BYTES; ++i; break;
                case "jagp":  address += Byte.BYTES + Integer.BYTES; ++i; break;
                case "jaep":  address += Byte.BYTES + Integer.BYTES; ++i; break;
                case "janp":  address += Byte.BYTES + Integer.BYTES; ++i; break;
                default:
                    if (op.charAt(0) == ':') {
                        lbladdr.put(op, address);
                    } else {
                        break outer;
                    }
            }
        }

        final ByteBuffer code = ByteBuffer.allocate(address);

        // Generate code bytes
        outer:
        for (int i = 0; i < tokens.length; ++i) {
            final String op = tokens[i];
            switch (op.toLowerCase()) {
                case "hlt":   code.put(OP_HLT); break;
                case "add_a": code.put(OP_ADD_A); break;
                case "add_p": code.put(OP_ADD_P); break;
                case "sub_a": code.put(OP_SUB_A); break;
                case "sub_p": code.put(OP_SUB_P); break;
                case "mul_a": code.put(OP_MUL_A); break;
                case "mul_p": code.put(OP_MUL_P); break;
                case "div_a": code.put(OP_DIV_A); break;
                case "div_p": code.put(OP_DIV_P); break;
                case "mod_a": code.put(OP_MOD_A); break;
                case "mod_p": code.put(OP_MOD_P); break;
                case "a2c":   code.put(OP_A2C); break;
                case "p2c":   code.put(OP_P2C); break;
                case "ch2a":  code.put(OP_CH2A); break;
                case "ch2p":  code.put(OP_CH2P); break;
                case "cl2a":  code.put(OP_CL2A); break;
                case "cl2p":  code.put(OP_CL2P); break;
                case "ap2c":  code.put(OP_AP2C); break;
                case "send":  code.put(OP_SEND); break;
                case "recv":  code.put(OP_RECV); break;
                case "lda":   code.put(OP_LDA); code.putShort((short) Integer.parseInt(tokens[++i])); break;
                case "ldp":   code.put(OP_LDP); code.putShort((short) Integer.parseInt(tokens[++i])); break;
                case "jmp":   code.put(OP_JMP);  code.putInt(lbladdr.get(tokens[++i])); break;
                case "jalp":  code.put(OP_JALP); code.putInt(lbladdr.get(tokens[++i])); break;
                case "jagp":  code.put(OP_JAGP); code.putInt(lbladdr.get(tokens[++i])); break;
                case "jaep":  code.put(OP_JAEP); code.putInt(lbladdr.get(tokens[++i])); break;
                case "janp":  code.put(OP_JANP); code.putInt(lbladdr.get(tokens[++i])); break;
                default:
                    if (op.charAt(0) != ':') {
                        break outer;
                    }
            }
        }
        return code.array();
    }
}