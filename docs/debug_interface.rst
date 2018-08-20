Debugging Interface
========================

This interface can be accessed by pressing :kbd:`Control-Alt-2`.

As soon as you enter this interface, program execution is temporarily halted. This screen contains information about each core. The
left and right arrow keys change the core being inspected. The top left of the screen is the core's name and the values of the
registers described earlier. On the right is the code editor. Using the up and down arrows will change the current editing
line. Unfortunately, there is no cursor in the horizontal direction. If a typo is made, you will have to backspace and retype
the whole line. Note the Tab key adds a new line before the current line, and the Return key adds a new line after the current
line. The Delete key deletes an entire line, unless if that line is the only in the editor.
:kbd:`Control-B` assembles the code and flashes it into the current core.
Similarly, :kbd:`Control-Shift-B` assembles the code for each core and flashes them into their respected cores.
:kbd:`Control-S` invokes the next instruction while :kbd:`Control-Shift-S` invokes the next instruction of the CPU.
:kbd:`Control-R` resets -- clearing out all the registers -- the current core while :kbd:`Control-Shift-R` resets the CPU.

Assembler
------------------------

The assembler does not perform any sensible error handling. For example, when an unrecognized text is reached, the assembler
treats it as if the code ended there and assembles the code based on the content before that errornous token. Labels are only
supported by the branching instruction and will most likely crash the assembler if appeared anywhere else. To declare a label,
you prefix the name with a colon; there must not be a space in between the colon and its name. Comments start with a semicolon
and stop at the end of the line.