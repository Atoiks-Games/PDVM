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

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.pdvm.Unit;
import org.atoiks.games.pdvm.Memory;

import static org.atoiks.games.pdvm.App.WIDTH;
import static org.atoiks.games.pdvm.App.HEIGHT;

public class Screen extends Scene {

    public static final int PIXEL_SIDE = 4;
    public static final int RD_WIDTH   = WIDTH / PIXEL_SIDE;
    public static final int RD_HEIGHT  = HEIGHT / PIXEL_SIDE;

    public static final int START_VIDMEM = 0x0000;
    public static final int END_VIDMEM   = START_VIDMEM + RD_WIDTH * RD_HEIGHT / 2;

    // This table must have 16 entries. Bottom row is normally the darker variation of the top row
    private static final Color[] colorTable = {
        Color.black, Color.white, Color.red, Color.green, Color.blue, Color.magenta, Color.cyan, Color.yellow,
        Color.darkGray, Color.gray, new Color(0x8c, 0x00, 0x00), new Color(0x00, 0x6c, 0x00), new Color(0x00, 0x00, 0x8c), Color.pink, new Color(0x00, 0x8c, 0x8c), Color.orange,
    };

    private Unit unit;
    private Memory mem;

    @Override
    public void enter(int from) {
        if (this.unit == null) {
            this.unit = (Unit) scene.resources().get("cpu");
            this.mem = (Memory) scene.resources().get("ram");
        }
    }

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        for (int offset = START_VIDMEM, i = 0; offset < END_VIDMEM; ++offset, i += 2) {
            final byte value = mem.data.get(offset);
            final int lb = value & 0xF;
            final int hb = (value & 0xF0) >> Byte.SIZE / 2;

            // Technically, the high byte goes first if we draw from left to right
            g.setColor(colorTable[hb]);
            final int x1 = i % RD_WIDTH;
            final int y1 = i / RD_WIDTH;
            paintReducedPixel(g, x1, y1);

            g.setColor(colorTable[lb]);
            final int x2 = (i + 1) % RD_WIDTH;
            final int y2 = (i + 1) / RD_WIDTH;
            paintReducedPixel(g, x2, y2);
        }

        // // The following section prints out all the possible colors, in rows of 8
        // for (int i = 0; i < colorTable.length; ++i) {
        //     g.setColor(colorTable[i]);
        //     final int x = i % 8;
        //     final int y = i / 8;
        //     paintReducedPixel(g, x, y);
        // }
    }

    private void paintReducedPixel(final IGraphics g, final int px, final int py) {
        g.fillRect(px * PIXEL_SIDE, py * PIXEL_SIDE, (px + 1) * PIXEL_SIDE, (py + 1) * PIXEL_SIDE);
    }

    @Override
    public boolean update(float dt) {
        if (scene.keyboard().isKeyDown(KeyEvent.VK_CONTROL)) {
            if (scene.keyboard().isKeyDown(KeyEvent.VK_ALT) && scene.keyboard().isKeyPressed(KeyEvent.VK_2)) {
                scene.gotoNextScene();
                return true;
            }
        }
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Unused
    }
}