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

public final class Opcode {

    // <op>
    public static final byte OP_HLT = 0x0;

    // <op>
    public static final byte OP_ADD_A = 0x1;
    public static final byte OP_ADD_P = 0x2;
    public static final byte OP_SUB_A = 0x3;
    public static final byte OP_SUB_P = 0x4;
    public static final byte OP_MUL_A = 0x5;
    public static final byte OP_MUL_P = 0x6;
    public static final byte OP_DIV_A = 0x7;
    public static final byte OP_DIV_P = 0x8;
    public static final byte OP_MOD_A = 0x9;
    public static final byte OP_MOD_P = 0xA;

    // <op>
    public static final byte OP_A2C  = 0xB;
    public static final byte OP_P2C  = 0xC;
    public static final byte OP_CH2A = 0xD;
    public static final byte OP_CH2P = 0xE;
    public static final byte OP_CL2A = 0xF;
    public static final byte OP_CL2P = 0x10;
    public static final byte OP_AP2C = 0x11;

    // <op>
    public static final byte OP_SEND = 0x12;
    public static final byte OP_RECV = 0x13;

    // <op> <16 bit>
    public static final byte OP_LDA = 0x14;
    public static final byte OP_LDP = 0x15;

    // <op> <32 bit>
    public static final byte OP_JMP = 0x16;
    public static final byte OP_JALP = 0x17;
    public static final byte OP_JAGP = 0x18;
    public static final byte OP_JAEP = 0x19;
    public static final byte OP_JANP = 0x1A;

    // <op> <16 bit> <0x0[reg]> => *(addr)
    // <op> <16 bit> <0x1[reg]> => *(addr + R)
    // <op> <16 bit> <0x2[reg]> => *(*(addr + R))
    // <op> <16 bit> <0x3[reg]> => *(*(addr) + R)
    public static final byte OP_STHA = 0x1B;
    public static final byte OP_STFA = 0x1C;
    public static final byte OP_LDHA = 0x1D;
    public static final byte OP_LDFA = 0x1E;

    // <op>
    public static final byte OP_SWAP = 0x1F;

    // <op>
    public static final byte OP_PHC = 0x20;
    public static final byte OP_PPC = 0x21;
    public static final byte OP_RET = 0x22;

    // <op> <32 bit>
    public static final byte OP_JSR = 0x23;

    // <op>
    public static final byte OP_C2S = 0x24;
    public static final byte OP_S2C = 0x25;
    public static final byte OP_SWSC = 0x26;

    // <op> <32 bit>
    public static final byte OP_LDS = 0x27;

    // <op> <0x0[reg]> => c = c + R
    // <op> <0x1[reg]> => c = c - R
    // <op> <0x2[reg]> => c = c * R
    // <op> <0x3[reg]> => c = c / R
    // <op> <0x4[reg]> => c = c % R
    // <op> <0x5[reg]> => c = R
    public static final byte OP_ALU = 0x28;

    private Opcode() {
        //
    }
}