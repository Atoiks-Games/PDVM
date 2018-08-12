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

import java.util.Random;

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

    public static final int SYS_RAND_NUM = 0x0000;  // Short.SIZE
    public static final int SYS_LAST_KEY = 0x0002;  // Short.SIZE

    public static final int START_VIDMEM = 0x0010;
    public static final int END_VIDMEM   = START_VIDMEM + RD_WIDTH * RD_HEIGHT;

    // This table must have 256 entries. Each color works like this: (0bRRRGGGBB)
    // (populate this array in static constructor)
    public static final int MAX_COLORS = 256;
    private static final Color[] colorTable = new Color[MAX_COLORS];

    static {
        for (int i = 0; i < MAX_COLORS; ++i) {
            colorTable[i] = new Color((i >> 5) * 32, ((i & 28) >> 2) * 32, (i & 3) * 64);
        }
    }

    private final Random rand = new Random();

    private Unit unit;
    private Memory mem;

    private int lastKeySave;

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

        for (int offset = START_VIDMEM, i = 0; offset < END_VIDMEM; ++offset, ++i) {
            g.setColor(colorTable[Byte.toUnsignedInt(mem.data.get(offset))]);
            paintReducedPixel(g, i % RD_WIDTH, i / RD_WIDTH);
        }
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

        // Update system variables if needed
        mem.data.putShort(SYS_RAND_NUM, (short) rand.nextInt(2 << (Short.SIZE - 1)));
        mem.data.putShort(SYS_LAST_KEY, (short) scene.keyboard().getLastDownKey());

        unit.invokeNext();
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Unused
    }
}