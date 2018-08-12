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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import org.atoiks.games.pdvm.grammar.AssemblyLexer;
import org.atoiks.games.pdvm.grammar.AssemblyParser;
import org.atoiks.games.pdvm.grammar.AssemblyBaseListener;

import static org.atoiks.games.pdvm.Opcode.*;

public class Assembler extends AssemblyBaseListener {

    private final HashMap<String, Integer> lbladdr = new HashMap<>();

    private int address;

    private ByteBuffer code;

    private int retInt;
    private short retShort;
    private byte retByte;

    public static byte[] assemble(final String txt) {
        final AssemblyLexer lexer = new AssemblyLexer(CharStreams.fromString(txt));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final AssemblyParser parser = new AssemblyParser(tokens);
        final AssemblyParser.ProgramContext tree = parser.program();

        final Assembler asm = new Assembler();
        ParseTreeWalker.DEFAULT.walk(asm, tree);
        asm.code = ByteBuffer.allocate(asm.address);
        ParseTreeWalker.DEFAULT.walk(asm, tree);

        return asm.code.array();
    }

    @Override
    public void enterRegA(AssemblyParser.RegAContext ctx) {
        retByte = 0;
    }

    @Override
    public void enterRegP(AssemblyParser.RegPContext ctx) {
        retByte = 1;
    }

    @Override
    public void enterRegC(AssemblyParser.RegCContext ctx) {
        retByte = 2;
    }

    @Override
    public void enterRegIP(AssemblyParser.RegIPContext ctx) {
        retByte = 3;
    }

    @Override
    public void enterRegSP(AssemblyParser.RegSPContext ctx) {
        retByte = 4;
    }

    @Override
    public void enterOpHlt(AssemblyParser.OpHltContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_HLT);
        }
    }

    @Override
    public void enterOpAddA(AssemblyParser.OpAddAContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_ADD_A);
        }
    }

    @Override
    public void enterOpAddP(AssemblyParser.OpAddPContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_ADD_P);
        }
    }

    @Override
    public void enterOpSubA(AssemblyParser.OpSubAContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_SUB_A);
        }
    }

    @Override
    public void enterOpSubP(AssemblyParser.OpSubPContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_SUB_P);
        }
    }

    @Override
    public void enterOpMulA(AssemblyParser.OpMulAContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_MUL_A);
        }
    }

    @Override
    public void enterOpMulP(AssemblyParser.OpMulPContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_MUL_P);
        }
    }

    @Override
    public void enterOpDivA(AssemblyParser.OpDivAContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_DIV_A);
        }
    }

    @Override
    public void enterOpDivP(AssemblyParser.OpDivPContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_DIV_P);
        }
    }

    @Override
    public void enterOpModA(AssemblyParser.OpModAContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_MOD_A);
        }
    }

    @Override
    public void enterOpModP(AssemblyParser.OpModPContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_MOD_P);
        }
    }

    @Override
    public void enterOpA2C(AssemblyParser.OpA2CContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_A2C);
        }
    }

    @Override
    public void enterOpP2C(AssemblyParser.OpP2CContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_P2C);
        }
    }

    @Override
    public void enterOpCh2a(AssemblyParser.OpCh2aContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_CH2A);
        }
    }

    @Override
    public void enterOpCh2p(AssemblyParser.OpCh2pContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_CH2P);
        }
    }

    @Override
    public void enterOpCl2a(AssemblyParser.OpCl2aContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_CL2A);
        }
    }

    @Override
    public void enterOpCl2p(AssemblyParser.OpCl2pContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_CL2P);
        }
    }

    @Override
    public void enterOpAp2c(AssemblyParser.OpAp2cContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_AP2C);
        }
    }

    @Override
    public void enterOpSend(AssemblyParser.OpSendContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_SEND);
        }
    }

    @Override
    public void enterOpRecv(AssemblyParser.OpRecvContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_RECV);
        }
    }

    @Override
    public void enterOpLda(AssemblyParser.OpLdaContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES;
        }
    }

    @Override
    public void exitOpLda(AssemblyParser.OpLdaContext ctx) {
        if (code != null) {
            code.put(OP_LDA);
            code.putShort((short) retInt);
        }
    }

    @Override
    public void enterOpLdp(AssemblyParser.OpLdpContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES;
        }
    }

    @Override
    public void exitOpLdp(AssemblyParser.OpLdpContext ctx) {
        if (code != null) {
            code.put(OP_LDP);
            code.putShort((short) retInt);
        }
    }

    @Override
    public void enterOpJmp(AssemblyParser.OpJmpContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJmp(AssemblyParser.OpJmpContext ctx) {
        if (code != null) {
            code.put(OP_JMP);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJalp(AssemblyParser.OpJalpContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJalp(AssemblyParser.OpJalpContext ctx) {
        if (code != null) {
            code.put(OP_JALP);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJagp(AssemblyParser.OpJagpContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJagp(AssemblyParser.OpJagpContext ctx) {
        if (code != null) {
            code.put(OP_JAGP);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJaep(AssemblyParser.OpJaepContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJaep(AssemblyParser.OpJaepContext ctx) {
        if (code != null) {
            code.put(OP_JAEP);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJanp(AssemblyParser.OpJanpContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJanp(AssemblyParser.OpJanpContext ctx) {
        if (code != null) {
            code.put(OP_JANP);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpStha(AssemblyParser.OpSthaContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES + Byte.BYTES;
        }
        retShort = retByte = 0;
    }

    @Override
    public void exitOpStha(AssemblyParser.OpSthaContext ctx) {
        if (code != null) {
            code.put(OP_STHA);
            code.putShort(retShort);
            code.put(retByte);
        }
    }

    @Override
    public void enterOpStfa(AssemblyParser.OpStfaContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES + Byte.BYTES;
        }
        retShort = retByte = 0;
    }

    @Override
    public void exitOpStfa(AssemblyParser.OpStfaContext ctx) {
        if (code != null) {
            code.put(OP_STFA);
            code.putShort(retShort);
            code.put(retByte);
        }
    }

    @Override
    public void enterOpLdha(AssemblyParser.OpLdhaContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES + Byte.BYTES;
        }
        retShort = retByte = 0;
    }

    @Override
    public void exitOpLdha(AssemblyParser.OpLdhaContext ctx) {
        if (code != null) {
            code.put(OP_LDHA);
            code.putShort(retShort);
            code.put(retByte);
        }
    }

    @Override
    public void enterOpLdfa(AssemblyParser.OpLdfaContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Short.BYTES + Byte.BYTES;
        }
        retShort = retByte = 0;
    }

    @Override
    public void exitOpLdfa(AssemblyParser.OpLdfaContext ctx) {
        if (code != null) {
            code.put(OP_LDFA);
            code.putShort(retShort);
            code.put(retByte);
        }
    }

    @Override
    public void enterOpSwap(AssemblyParser.OpSwapContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_SWAP);
        }
    }

    @Override
    public void enterOpPhc(AssemblyParser.OpPhcContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_PHC);
        }
    }

    @Override
    public void enterOpPpc(AssemblyParser.OpPpcContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_PPC);
        }
    }

    @Override
    public void enterOpRet(AssemblyParser.OpRetContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_RET);
        }
    }

    @Override
    public void enterOpJsr(AssemblyParser.OpJsrContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJsr(AssemblyParser.OpJsrContext ctx) {
        if (code != null) {
            code.put(OP_JSR);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpC2S(AssemblyParser.OpC2SContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_C2S);
        }
    }

    @Override
    public void enterOpS2C(AssemblyParser.OpS2CContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_S2C);
        }
    }

    @Override
    public void enterOpSwsc(AssemblyParser.OpSwscContext ctx) {
        if (code == null) {
            address += Byte.BYTES;
        } else {
            code.put(OP_SWSC);
        }
    }

    @Override
    public void enterOpLds(AssemblyParser.OpLdsContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpLds(AssemblyParser.OpLdsContext ctx) {
        if (code != null) {
            code.put(OP_LDS);
            code.putInt(retInt);
        }
    }

    @Override
    public void enterDefLabel(AssemblyParser.DefLabelContext ctx) {
        if (code == null) {
            lbladdr.put(ctx.getText(), address);
        }
    }

    @Override
    public void enterImmediate(AssemblyParser.ImmediateContext ctx) {
        if (code != null) {
            if (ctx.n == null) {
                retInt = lbladdr.get(ctx.getText());
            } else {
                retInt = numberTextToInt(ctx.getText());
            }
        }
    }

    @Override
    public void enterMemory(AssemblyParser.MemoryContext ctx) {
        if (ctx.n != null) {
            retShort = (short) numberTextToInt(ctx.n.getText());
        }
    }

    @Override
    public void exitIndirect(AssemblyParser.IndirectContext ctx) {
        retShort = (short) numberTextToInt(ctx.addr.getText());
        retByte += 0x10;
    }

    @Override
    public void exitIndirectX(AssemblyParser.IndirectXContext ctx) {
        retByte += 0x10;
    }

    @Override
    public void exitIndirectY(AssemblyParser.IndirectYContext ctx) {
        retShort = (short) numberTextToInt(ctx.addr.getText());
        retByte += 0x30;
    }

    private static int numberTextToInt(final String str) {
        if (str.charAt(0) == '$') {
            // number is hex
            return Integer.parseInt(str.substring(1), 16);
        }
        return Integer.parseInt(str);
    }
}