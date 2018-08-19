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

    private static byte registerToByte(AssemblyParser.RegisterContext ctx) {
        if (ctx.a != null) return 0;
        if (ctx.p != null) return 1;
        if (ctx.c != null) return 2;
        if (ctx.ip != null) return 3;
        if (ctx.sp != null) return 4;
        throw new AssertionError("Unhandled register " + ctx.getText());
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
    public void enterOpAdd(AssemblyParser.OpAddContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x00 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpSub(AssemblyParser.OpSubContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x10 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpMul(AssemblyParser.OpMulContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x20 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpDiv(AssemblyParser.OpDivContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x30 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpMod(AssemblyParser.OpModContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x40 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpM2C(AssemblyParser.OpM2CContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES;
        } else {
            code.put(OP_ALU);
            code.put((byte) (0x50 | registerToByte(ctx.r)));
        }
    }

    @Override
    public void enterOpJl(AssemblyParser.OpJlContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJl(AssemblyParser.OpJlContext ctx) {
        if (code != null) {
            code.put(OP_JL);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJg(AssemblyParser.OpJgContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJg(AssemblyParser.OpJgContext ctx) {
        if (code != null) {
            code.put(OP_JG);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJle(AssemblyParser.OpJleContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJle(AssemblyParser.OpJleContext ctx) {
        if (code != null) {
            code.put(OP_JLE);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJge(AssemblyParser.OpJgeContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJge(AssemblyParser.OpJgeContext ctx) {
        if (code != null) {
            code.put(OP_JGE);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJe(AssemblyParser.OpJeContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJe(AssemblyParser.OpJeContext ctx) {
        if (code != null) {
            code.put(OP_JE);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
            code.putInt(retInt);
        }
    }

    @Override
    public void enterOpJn(AssemblyParser.OpJnContext ctx) {
        if (code == null) {
            address += Byte.BYTES + Byte.BYTES + Integer.BYTES;
        }
    }

    @Override
    public void exitOpJn(AssemblyParser.OpJnContext ctx) {
        if (code != null) {
            code.put(OP_JN);
            code.put((byte) ((registerToByte(ctx.ra) << (Byte.SIZE / 2)) | registerToByte(ctx.rb)));
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
        retByte = (byte) (0x10 | registerToByte(ctx.r));
    }

    @Override
    public void exitIndirectX(AssemblyParser.IndirectXContext ctx) {
        retByte += 0x10;
    }

    @Override
    public void exitIndirectY(AssemblyParser.IndirectYContext ctx) {
        retShort = (short) numberTextToInt(ctx.addr.getText());
        retByte = (byte) (0x30 | registerToByte(ctx.r));
    }

    private static int numberTextToInt(final String str) {
        if (str.charAt(0) == '$') {
            // number is hex
            return Integer.parseInt(str.substring(1), 16);
        }
        return Integer.parseInt(str);
    }
}