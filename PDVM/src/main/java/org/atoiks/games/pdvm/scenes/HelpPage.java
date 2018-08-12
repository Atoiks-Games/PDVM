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

import java.util.Arrays;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

import static org.atoiks.games.pdvm.App.MONOSPACE_FONT;

public class HelpPage extends Scene {

    private static final int MAX_LINES_PER_PAGE = 37;

    // All INFO_MSG strings are max 127 characters long.
    // anything longer will *not* be wrapped by the render function
    private static final String[] INFO_MSG = {
        "Welcome to the PDVM debugging interface!",
        "",
        "Some information to PDVM before giving you the useful help:",
        "The PDVM project began on August 8th 2018 with the goal of creating an imaginary architecture that features an extremely simple",
        "instruction set while providing a multi-core environment. As stated on the project page, the name PDVM was not supposed to be a",
        "meaningful acronym. Of course, you are free to make up your own, as long as it is not offensive.",
        "",
        "Each PDVM processing unit has four cores labelled 0, 1, 2 and 3. Each core contains registers A, P, C, a stack pointer named SP",
        "and a program counter (instruction pointer) named IP. Registers A and P are 16 bits wide while C and the program counter is 32",
        "bits wide. Register A is the data register. All instructions involving data transfering between cores use this register as",
        "either its data source or destination. Register P is the port register. Instructions that interact with other cores use this",
        "register to specify which core will be interacted with. Register C is the calculation register. It is impossible to perform a",
        "numerical calculation without involving this register. Each core also contains a input buffer. This buffer can only store one",
        "value, and its state is either filled or empty.",
        "",
        "The PDVM debugging interface provides you with 2^24 bytes of random accessible data memory. This memory is separate from each",
        "core's code memory. Since there is no way to load or store values onto the code memory, self modifying code is impossible. The",
        "data memory is shared across all cores.",
        "",
        "The previous screen can be accessed after pressing Q on your keyboard. That screen contains information about each core. The",
        "left and right arrow keys change the core being inspected. The top left of the screen is the core's name and the values of the",
        "registers described earlier. On the right is the code editor. Using the up and down arrows will change the current editing",
        "line. Unfortunately, there is no cursor in the horizontal direction. If a typo is made, you will have to backspace and retype",
        "the whole line. Note the Tab key adds a new line before the current line, and the Return key adds a new line after the current",
        "line. The Delete key deletes an entire line, unless if that line is the only in the editor. ^-B assembles the code and flashes",
        "it into the current core. Similarly, ^-Shift-B assembles the code for each core and flashes them into their respected cores.",
        "^-S invokes the next instruction while ^-Shift-S invokes the next instruction of the CPU. ^-R resets -- clearing out all the",
        "registers -- the current core while ^-Shift-R resets the CPU.",
        "",
        "Pressing ^-Alt-1 will bring you to the run screen. This is will run the latest build on all cores. In addition, the screen is",
        "rendered based on the data stored in the data memory. To return to the editor interface, press ^-Alt-2. The execution on all",
        "cores will be halted and the current state of all the cores can be examined.",
        "",
        "The display memory maps from address $0010 to $5c8a (exclusive) on the data memory. Each memory cell maps to one (virtual)",
        "pixel on the screen. The screen uses 8-bit colors meaning the maximum number of colors is 256. The color scheme is defined as",
        "RRRGGGBB with zero defined as black and $FF defined as white. Each row contains $B2 pixels, and the first row of pixels",
        "correspond from address $0010 to $00C2 (exclusive).",
        "",
        "The data memory also contains values provided by the runtime. See below table:",
        "$0000-$0002 - 16 bit random number",
        "$0002-$0004 - 16 bit keycode of last pressed key",
        "",
        "The assembler does not perform any sensible error handling. For example, when an unrecognized text is reached, the assembler",
        "treats it as if the code ended there and assembles the code based on the content before that errornous token. Labels are only",
        "supported by the branching instruction and will most likely crash the assembler if appeared anywhere else. To declare a label,",
        "you prefix the name with a colon; there must not be a space in between the colon and its name. Comments start with a semicolon",
        "and stop at the end of the line.",
        "",
        "Here is the list of instructions: (they are case insensitive)",
        "hlt           - To halt the execution",
        "add_a         - Add register A to register C",
        "add_p         - Add register P to register C",
        "sub_a         - Subtract register A to register C",
        "sub_p         - Subtract register P to register C",
        "mul_a         - Multiply register A to register C",
        "mul_p         - Multiply register P to register C",
        "div_a         - Divide register C by register A, keeps integer part",
        "div_p         - Divide register C by register P, keeps integer part",
        "mod_a         - Divide register C by register A, keeps remainder",
        "mod_p         - Divide register C by register P, keeps remainder",
        "a2c           - Moves value in register A to register C",
        "p2c           - Moves value in register P to register C",
        "ch2a          - Moves the upper 16 bits from register C to register A",
        "ch2p          - Moves the upper 16 bits from register C to register P",
        "cl2a          - Moves the lower 16 bits from register C to register A",
        "cl2p          - Moves the lower 16 bits from register C to register P",
        "ap2c          - Moves register A as upper 16 bits and register P as lower 16 bits into register C",
        "send          - Sends value in register A to the core specified by register P. It will keep retrying until the destination",
        "                core's input buffer is empty",
        "recv          - Moves the value from the core's input buffer into register A. It will keep retrying until the input buffer is",
        "                filled",
        "lda  <imm:16> - Stores an immediate value into register A",
        "ldp  <imm:16> - Stores an immediate value into register P",
        "jmp  <imm:32> - Branches execution to specified address",
        "jalp <imm:32> - Branches execution if register A < register P",
        "jagp <imm:32> - Branches execution if register A > register P",
        "jaep <imm:32> - Branches execution if register A = register P",
        "janp <imm:32> - Branches execution if register A ≠ register P",
        "stha <memory> - Stores the lower 8 bits of register A into the data memory",
        "stfa <memory> - Stores the value of register A into the data memory. It will take two slots since each memory slot is 8 bits",
        "ldha <memory> - Loads value from data memory to register A. Register A now contains an 8 bit value",
        "ldfa <memory> - Loads two values from two consecutive data memory slots to register A. Register A now contains a 16 bit value",
        "swap          - Swaps values of register A and P",
        "phc           - Pushes value of register C on to stack, increments stack pointer",
        "ppc           - Pops from stack and stores into register C, decrements stack pointer",
        "ret           - Pops from stack and jumps to it, decrements stack pointer, used with jsr",
        "jsr  <imm:32> - Branches execution and saves the return address, increments stack pointer, used with ret",
        "c2s           - Stores register C's value into stack pointer",
        "s2c           - Stores stack pointer's value into register",
        "swsc          - Swaps value of stack pointer and register C",
        "lds  <imm:32> - Stores an immediate value into stack pointer",
        "",
        "<imm:16>  - A 16 bit immediate value, will truncate value if greater. Examples are base 10 ints (123), base 16 ints ($1f) and",
        "            labels (:loop)",
        "<imm:32>  - A 32 bit immediate value, will truncate value if greater. Has same literals has <imm:16>.",
        "<memory>  - A memory address. It can be absolute (($1234)), indirect ((10, %a)), indexed indirect (((10, %a))) or indirect",
        "            indexed (((10), %a)). The registers can be %a, %p, %c, %sp or %ip (case insensitive)",
        "",
        "Here is a program that counts as an example (to avoid notation misunderstandings):",
        "",
        "    lda 1",
        ":loop",
        "    add_a",
        "    jmp :loop"
    };

    private int displayFromLine;

    @Override
    public void enter(int from) {
        displayFromLine = 0;
    }

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        g.setColor(Color.white);
        g.setFont(MONOSPACE_FONT);

        final int cached = displayFromLine;
        final int upperBound = Math.min(MAX_LINES_PER_PAGE, INFO_MSG.length - cached);
        for (int i = 0; i < upperBound; ++i) {
            final int offset = cached + i;
            String str = INFO_MSG[offset];
            if (str.isEmpty()) {
                final char[] arr = new char[128];
                Arrays.fill(arr, ' ');
                arr[arr.length - 1] = '¬';
                INFO_MSG[offset] = str = new String(arr);
            } else if (str.charAt(str.length() - 1) != '¬') {
                // Perform string padding
                final StringBuilder sb = new StringBuilder(127).append(str);
                final int delta = 127 - sb.length();
                if (delta > 0) {
                    final char[] arr = new char[delta];
                    Arrays.fill(arr, ' ');
                    sb.append(arr);
                }
                sb.append('¬');
                INFO_MSG[offset] = str = sb.toString();
            }
            g.drawString(str, 8, 15 + i * MONOSPACE_FONT.getSize());
        }
    }

    @Override
    public boolean update(final float dt) {
        if (scene.keyboard().isKeyDown(KeyEvent.VK_CONTROL)) {
            // Transition into Screen.java
            if (scene.keyboard().isKeyDown(KeyEvent.VK_ALT) && scene.keyboard().isKeyPressed(KeyEvent.VK_1)) {
                scene.keyboard().captureTypedChars(false);
                scene.switchToScene(0);
                return true;
            }
        }

        if (scene.keyboard().isKeyPressed(KeyEvent.VK_Q)) {
            scene.switchToScene(1);
            return true;
        }

        if (scene.keyboard().isKeyPressed(KeyEvent.VK_UP)) {
            if (displayFromLine > 0) --displayFromLine;
        }
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_DOWN)) {
            if (displayFromLine < INFO_MSG.length - 1) ++displayFromLine;
        }

        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Unused
    }
}