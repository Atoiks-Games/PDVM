Architecture
========================

The initial screen you are introduced to is referred to as the run screen which can be accessed by pressing ^-Alt-1.
During this time, the processing unit will continue executing the code and repaint the screen according to the data
memory.

Processing Unit and Cores
------------------------

Each PDVM processing unit has four cores labelled 0, 1, 2 and 3. Each core contains registers A, P, C, a stack pointer named SP
and a program counter (instruction pointer) named IP. Registers A and P are 16 bits wide while C and the program counter is 32
bits wide. Register A is the data register. All instructions involving data transfering between cores use this register as
either its data source or destination. Register P is the port register. Instructions that interact with other cores use this
register to specify which core will be interacted with. Register C is the calculation register. It is impossible to perform a
numerical calculation without involving this register. Each core also contains a input buffer. This buffer can only store one
value, and its state is either filled or empty.

When the processing unit steps through the next instruction, core 0 is updated first and core 3 is updated last. This is
important as it may affect the input buffer's state or reading from the data memory.

Memory Accessing
------------------------

Each core is equiped with its own code memory. This memory is private and readonly meaning self modifying code is impossible.
Furthermore, PDVM does not have instructions that allow loading values from the code memory.