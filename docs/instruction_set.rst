Instruction Set
========================

hlt           - To halt the execution
add  <reg>    - Register C = register C + specified register
sub  <reg>    - Register C = register C - specified register
mul  <reg>    - Register C = register C * specified register
div  <reg>    - Register C = register C / specified register
mod  <reg>    - Register C = register C mod specified register
m2c  <reg>    - Register C = specified register
ch2a          - Moves the upper 16 bits from register C to register A
ch2p          - Moves the upper 16 bits from register C to register P
cl2a          - Moves the lower 16 bits from register C to register A
cl2p          - Moves the lower 16 bits from register C to register P
ap2c          - Moves register A as upper 16 bits and register P as lower 16 bits into register C
send          - Sends value in register A to the core specified by register P. It will keep retrying until the destination
                core's input buffer is empty
recv          - Moves the value from the core's input buffer into register A. It will keep retrying until the input buffer is
                filled
lda  <imm:16> - Stores an immediate value into register A
ldp  <imm:16> - Stores an immediate value into register P
jmp  <imm:32> - Branches execution to specified address
jalp <imm:32> - Branches execution if register A < register P
jagp <imm:32> - Branches execution if register A > register P
jaep <imm:32> - Branches execution if register A = register P
janp <imm:32> - Branches execution if register A ≠ register P
stha <memory> - Stores the lower 8 bits of register A into the data memory
stfa <memory> - Stores the value of register A into the data memory. It will take two slots since each memory slot is 8 bits
ldha <memory> - Loads value from data memory to register A. Register A now contains an 8 bit value
ldfa <memory> - Loads two values from two consecutive data memory slots to register A. Register A now contains a 16 bit value
swap          - Swaps values of register A and P
phc           - Pushes value of register C on to stack, increments stack pointer
ppc           - Pops from stack and stores into register C, decrements stack pointer
ret           - Pops from stack and jumps to it, decrements stack pointer, used with jsr
jsr  <imm:32> - Branches execution and saves the return address, increments stack pointer, used with ret
c2s           - Stores register C's value into stack pointer
swsc          - Swaps value of stack pointer and register C
lds  <imm:32> - Stores an immediate value into stack pointer

<reg>     - Any register. The registers can be %a, %p, %c, %sp or %ip (case insensitive)
<imm:16>  - A 16 bit immediate value, will truncate value if greater. Examples are base 10 ints (123), base 16 ints ($1f) and
            labels (:loop)
<imm:32>  - A 32 bit immediate value, will truncate value if greater. Has same literals has <imm:16>
<memory>  - A memory address. It can be absolute (($1234)), indirect ((10, %a)), indexed indirect (((10, %a))) or indirect
            indexed (((10), %a)). The registers can be %a, %p, %c, %sp or %ip (case insensitive)

Here is a program that counts as an example (to avoid notation misunderstandings):

::

        lda 1
    :loop
        add %a
        jmp :loop