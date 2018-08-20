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

    // <op> <0x0[reg]> => c = c + R
    // <op> <0x1[reg]> => c = c - R
    // <op> <0x2[reg]> => c = c * R
    // <op> <0x3[reg]> => c = c / R
    // <op> <0x4[reg]> => c = c % R
    // <op> <0x5[reg]> => c = R
    public static final byte OP_ALU = 0x1;

    // <op>
    public static final byte OP_CH2A = 0x2;
    public static final byte OP_CH2P = 0x3;
    public static final byte OP_CL2A = 0x4;
    public static final byte OP_CL2P = 0x5;
    public static final byte OP_AP2C = 0x6;

    // <op>
    public static final byte OP_SEND = 0x7;
    public static final byte OP_RECV = 0x8;

    // <op> <16 bit>
    public static final byte OP_LDA = 0x9;
    public static final byte OP_LDP = 0xA;

    // <op> <32 bit>
    public static final byte OP_LDS = 0xB;

    // <op> <32 bit>
    public static final byte OP_JSR = 0xC;

    // <op>
    public static final byte OP_RET = 0xD;

    // <op> <32 bit>
    public static final byte OP_JMP = 0xE;

    // <op> <0x[reg1][reg2]> <32 bit>
    public static final byte OP_JL = 0xF;
    public static final byte OP_JG = 0x10;
    public static final byte OP_JE = 0x11;
    public static final byte OP_JN = 0x12;

    // <op> <16 bit> <0x0[reg]> => *(addr)
    // <op> <16 bit> <0x1[reg]> => *(addr + R)
    // <op> <16 bit> <0x2[reg]> => *(*(addr + R))
    // <op> <16 bit> <0x3[reg]> => *(*(addr) + R)
    public static final byte OP_STHA = 0x13;
    public static final byte OP_STFA = 0x14;
    public static final byte OP_LDHA = 0x15;
    public static final byte OP_LDFA = 0x16;

    // <op>
    public static final byte OP_SWAP = 0x17;

    // <op>
    public static final byte OP_PHC = 0x18;
    public static final byte OP_PPC = 0x19;

    // <op>
    public static final byte OP_C2S = 0x1A;
    public static final byte OP_SWSC = 0x1B;

    // <op> <0x[reg1][reg2]> <32 bit>
    public static final byte OP_JLE = 0x1C;
    public static final byte OP_JGE = 0x1D;

    // <op> <32 bit>
    public static final byte OP_JBF = 0x1E;
    public static final byte OP_JBE = 0x1F;

    // <op>
    public static final byte OP_FSND = 0x20;
    public static final byte OP_DSND = 0x21;

    // <op> <16 bit>
    public static final byte OP_DRCV = 0x22;

    private Opcode() {
        //
    }
}