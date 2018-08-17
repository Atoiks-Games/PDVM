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

package org.atoiks.games.pdvm.scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.pdvm.CPU;
import org.atoiks.games.pdvm.Core;
import org.atoiks.games.pdvm.Assembler;

import static org.atoiks.games.pdvm.App.HEIGHT;
import static org.atoiks.games.pdvm.App.MONOSPACE_FONT;

public class CPUState extends Scene {

    private static final int EDITOR_START_X = 102;
    private static final int MAX_LINES_PER_PAGE = 36;

    private int currentCore;

    private CPU cpu;
    private LinkedList<StringBuilder>[] lines;
    private int[] offsets;

    private StringBuilder currentBuffer;

    @Override
    public void enter(int from) {
        if (this.cpu == null) {
            this.cpu = (CPU) scene.resources().get("cpu");
            this.lines = new LinkedList[cpu.getNumberOfCores()];
            this.offsets = new int[cpu.getNumberOfCores()];
            for (int i = 0; i < lines.length; ++i) {
                lines[i] = new LinkedList<>();
                lines[i].add(new StringBuilder());
            }
            currentBuffer = lines[currentCore].peekFirst();
        }

        scene.keyboard().captureTypedChars(true);
    }

    @Override
    public void leave() {
        scene.keyboard().captureTypedChars(false);
    }

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(MONOSPACE_FONT);

        final Core core = cpu.getCore(currentCore);
        g.drawString("Core " + currentCore + ':', 5, 15);
        g.drawString("A:  " + core.a, 15, 15 + 1 * MONOSPACE_FONT.getSize());
        g.drawString("P:  " + core.p, 15, 15 + 2 * MONOSPACE_FONT.getSize());
        g.drawString("C:  " + core.c, 15, 15 + 3 * MONOSPACE_FONT.getSize());
        g.drawString("SP: " + core.stackPointer, 15, 15 + 4 * MONOSPACE_FONT.getSize());
        g.drawString("IP: " + core.instrPointer, 15, 15 + 5 * MONOSPACE_FONT.getSize());
        g.drawString("IB: " + core.inputBuffer, 15, 15 + 6 * MONOSPACE_FONT.getSize());

        g.drawLine(EDITOR_START_X, 0, EDITOR_START_X, HEIGHT);

        final int startIndex = Math.max(0, offsets[currentCore] - MAX_LINES_PER_PAGE);
        final ListIterator<StringBuilder> it = lines[currentCore].listIterator(startIndex);
        for (int virtIndex = 0; virtIndex <= MAX_LINES_PER_PAGE && it.hasNext(); ++virtIndex) {
            final int realIndex = virtIndex + startIndex;
            String render = it.next().toString();
            if (realIndex != lines[currentCore].size() - 1) {
                render += '¬';
            }

            final int height = 15 + virtIndex * MONOSPACE_FONT.getSize();
            g.drawString(render, EDITOR_START_X + 12, height);
            if (realIndex == offsets[currentCore]) {
                g.drawString("–>", EDITOR_START_X, height);
            }
        }
    }

    @Override
    public boolean update(final float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_LEFT)) {
            if (currentCore < 1) currentCore = cpu.getNumberOfCores();
            --currentCore;
            currentBuffer = lines[currentCore].get(offsets[currentCore]);
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_RIGHT)) {
            currentCore = (currentCore + 1) % cpu.getNumberOfCores();
            currentBuffer = lines[currentCore].get(offsets[currentCore]);
        }

        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (offsets[currentCore] > 0) {
                currentBuffer = lines[currentCore].get(--offsets[currentCore]);
            }
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            if (offsets[currentCore] < lines[currentCore].size() - 1) {
                currentBuffer = lines[currentCore].get(++offsets[currentCore]);
            }
        }

        if (scene.keyboard().isKeyDown(KeyEvent.VK_CONTROL)) {
            // Transition into Screen.java
            if (scene.keyboard().isKeyDown(KeyEvent.VK_ALT) && scene.keyboard().isKeyPressed(KeyEvent.VK_1)) {
                scene.keyboard().captureTypedChars(false);
                scene.switchToScene(0);
                return true;
            }

            // Assemble and update code
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_B)) {
                if (scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT)) {
                    for (int i = 0; i < lines.length; ++i) {
                        assembleAndAttach(i);
                    }
                } else {
                    assembleAndAttach(currentCore);
                }
            }

            // Invoke next instruction
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_S)) {
                if (scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT)) {
                    cpu.invokeNext();
                } else {
                    final Core core = cpu.getCore(currentCore);
                    core.invokeNext();
                }
            }

            // Reset state
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_R)) {
                if (scene.keyboard().isKeyDown(KeyEvent.VK_SHIFT)) {
                    cpu.reset();
                } else {
                    final Core core = cpu.getCore(currentCore);
                    core.reset();
                }
            }

            scene.keyboard().getTypedChars();
            return true;
        }

        final String typed = scene.keyboard().getTypedChars();
        switch (typed) {
            case "":
                break;
            case "\t":      // tab key
                lines[currentCore].add(offsets[currentCore], currentBuffer = new StringBuilder());
                break;
            case "\n":      // enter key
                lines[currentCore].add(++offsets[currentCore], currentBuffer = new StringBuilder());
                break;
            case "\b":      // backspace key
                if (currentBuffer.length() > 0) {
                    currentBuffer.deleteCharAt(currentBuffer.length() - 1);
                }
                break;
            case "\u007F":  // delete key
                if (offsets[currentCore] > 0) {
                    lines[currentCore].remove(offsets[currentCore]--);
                } else if (offsets[currentCore] == 0 && lines[currentCore].size() > 1) {
                    lines[currentCore].remove(offsets[currentCore]);
                }
                currentBuffer = lines[currentCore].get(offsets[currentCore]);
                break;
            default:
                currentBuffer.append(typed);
                break;
        }
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Unused
    }

    private void assembleAndAttach(int coreId) {
        final byte[] code = Assembler.assemble(lines[coreId].stream().collect(Collectors.joining("\n")));
        final Core core = cpu.getCore(coreId);
        core.attachCode(code);
    }
}