Data Memory
========================

This memory is separate from each core's code memory. Unlike the code memory,
the data memory is shared across all cores. The environment provides you with
2^24 bytes of data memory.

Layout
------------------------

Various offsets of the data memory is provided or used by the runtime:

+-------------------+---------------------------------------+
| Address           | Purpose                               |
+===================+=======================================+
| $0000-$0002       | 16 bit random number                  |
+-------------------+---------------------------------------+
| $0002-$0004       | 16 bit keycode of last pressed key    |
+-------------------+---------------------------------------+
| $0004-$0010       | Currently unused, reserved            |
+-------------------+---------------------------------------+
| $0010-$5c8a       | Display memory (see below)            |
+-------------------+---------------------------------------+
| $5c8a-$1000000    | Open, store custom data or allocate   |
|                   | the stack here                        |
+-------------------+---------------------------------------+

Display Memory
------------------------

The display memory maps from address $0010 to $5c8a (exclusive) on the data memory. Each memory cell maps to one (virtual)
pixel on the screen. The screen uses 8-bit colors meaning the maximum number of colors is 256. The color scheme is defined as
RRRGGGBB with zero defined as black and $FF defined as white. Each row contains $B2 pixels, and the first row of pixels
correspond from address $0010 to $00C2 (exclusive).
