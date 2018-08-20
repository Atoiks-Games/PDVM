Architecture
========================

The initial screen you are introduced to is referred to as the run screen which can be accessed by pressing :kbd:`Control-Alt-1`.
During this time, the processing unit will continue executing the code and repaint the screen according to the data
memory.

Quick info table:

+-----------------------+---------------+
| Data Width            | 16 bits       |
+-----------------------+---------------+
| Address Width         | 32 bits       |
+-----------------------+---------------+
| Endianness            | Big Endian    |
+-----------------------+---------------+
| Instruction Length    | Not fixed     |
+-----------------------+---------------+

Processing Unit and Cores
------------------------

Each PDVM processing unit has four cores labelled 0, 1, 2 and 3.
Each core contains registers A, P, C, a stack pointer named SP and a program counter (instruction pointer) named IP.
All of these initialize to zero on start up.
Apart from registers A and P, which are 16 bits, all other registers are 32 bit wide.

Register A is the data register.
All instructions involving data transfering between cores use this register as either its data source or destination.

Register P is the port register.
Instructions that interact with other cores use this register to specify which core will be interacted with.

Register C is the calculation register.
It is impossible to perform a numerical calculation without involving this register.

Relocating the stack before first use if highly recommended.
When using stack operations, each core does not check if they are overwriting the stack of other cores.

Each core also contains a input buffer.
This buffer can only store one value, and its state is either filled or empty.

When the processing unit steps through the next instruction, core 0 is updated first and core 3 is updated last. This is
important as it may affect the input buffer's state or accessing from the data memory.

Memory Accessing
------------------------

Each core is equiped with its own code memory. This memory is private and readonly meaning self modifying code is impossible.
Furthermore, PDVM does not have instructions that allow loading values from the code memory.