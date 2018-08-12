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

import java.io.IOException;

import java.awt.Font;
import java.awt.FontFormatException;

import org.atoiks.games.framework2d.FrameInfo;
import org.atoiks.games.framework2d.swing.Frame;

import org.atoiks.games.pdvm.scenes.*;

public class App {

    public static final int WIDTH  = 712;
    public static final int HEIGHT = 532;

    public static final Font MONOSPACE_FONT;

    static {
        Font local = null;
        try {
            local = Font.createFont(Font.PLAIN, App.class.getResourceAsStream("/VT323-Regular.ttf"));
        } catch (IOException | FontFormatException ex) {
            local = new Font("Monospace", Font.PLAIN, 14);
        } finally {
            MONOSPACE_FONT = local.deriveFont(14f);
        }
    }

    public static void main(String[] args) {
        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - PDVM")
                .setFps(24f)
                .setResizable(false)
                .setSize(WIDTH + (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? 12 : 0), HEIGHT)
                .setScenes(new Screen(), new CPUState(), new HelpPage());
        processResources(info);
        try (final Frame frame = new Frame(info)) {
            frame.init();
            frame.loop();
        }
    }

    private static void processResources(final FrameInfo info) {
        final CPU cpu = new CPU(4);
        final Memory mem = new Memory();
        cpu.mapMemory(mem);

        info.addResource("cpu", cpu);
        info.addResource("ram", mem);
    }
}