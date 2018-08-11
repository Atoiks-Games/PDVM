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

public class CPU implements Unit {

    private Core[] cores;

    public CPU(final int cores) {
        if (cores < 1) throw new IllegalArgumentException("Must be at least one core: " + cores);
        this.cores = new Core[cores];
        for (int i = 0; i < cores; ++i) {
            this.cores[i] = new Core(this);
        }
    }

    public Core getCore(int id) {
        return cores[id];
    }

    public int getNumberOfCores() {
        return cores.length;
    }

    @Override
    public void reset() {
        for (final Core core : cores) {
            core.reset();
        }
    }

    @Override
    public void invokeNext() {
        for (final Core core : cores) {
            core.invokeNext();
        }
    }

    @Override
    public void mapMemory(final Memory mem) {
        for (final Core core : cores) {
            core.mapMemory(mem);
        }
    }
}