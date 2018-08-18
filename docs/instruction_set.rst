Instruction Set
========================

List of Instructions
------------------------

hlt
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``hlt``   | ``$00``   |
+-----------+-----------+-----------+

To halt the execution

add
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``add %?``    | ``$01 $0?``   |
+-----------+---------------+---------------+

Register C = register C + specified register

sub
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``sub %?``    | ``$01 $1?``   |
+-----------+---------------+---------------+

Register C = register C - specified register

mul
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``mul %?``    | ``$01 $2?``   |
+-----------+---------------+---------------+

Register C = register C * specified register

div
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``div %?``    | ``$01 $3?``   |
+-----------+---------------+---------------+

Register C = register C / specified register

mod
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``mod %?``    | ``$01 $4?``   |
+-----------+---------------+---------------+

Register C = register C mod specified register

m2c
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Register  | ``m2c %?``    | ``$01 $5?``   |
+-----------+---------------+---------------+

Register C = specified register

ch2a
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``ch2a``  | ``$02``   |
+-----------+-----------+-----------+


Moves the upper 16 bits from register C to register A

ch2p
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``ch2p``  | ``$03``   |
+-----------+-----------+-----------+


Moves the upper 16 bits from register C to register P

cl2a
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``cl2a``  | ``$04``   |
+-----------+-----------+-----------+


Moves the lower 16 bits from register C to register A

cl2p
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``cl2p``  | ``$05``   |
+-----------+-----------+-----------+


Moves the lower 16 bits from register C to register P

ap2c
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``ap2c``  | ``$06``   |
+-----------+-----------+-----------+

Moves register A as upper 16 bits and register P as lower 16 bits into register C

send
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``send``  | ``$07``   |
+-----------+-----------+-----------+

Sends value in register A to the core specified by register P. It will keep retrying until the destination core's input buffer is empty

recv
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``recv``  | ``$08``   |
+-----------+-----------+-----------+

Moves the value from the core's input buffer into register A. It will keep retrying until the input buffer is filled

lda
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Immediate | ``lda $1234`` | ``$09 $1234`` |
+-----------+---------------+---------------+

Stores an immediate value into register A

ldp
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+---------------+---------------+
| Mode      | Syntax        | Hex           |
+===========+===============+===============+
| Immediate | ``ldp $1234`` | ``$0A $1234`` |
+-----------+---------------+---------------+

Stores an immediate value into register P

lds
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``lds $1234abcd`` | ``$0B $1234abcd`` |
+-----------+-------------------+-------------------+

Stores an immediate value into stack pointer

jmp
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``jmp $1234abcd`` | ``$0E $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution to specified address

jalp
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``jalp $1234abcd``| ``$0F $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution if register A < register P

jagp
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``jagp $1234abcd``| ``$10 $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution if register A > register P

jaep
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``jaep $1234abcd``| ``$11 $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution if register A = register P

janp
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``janp $1234abcd``| ``$12 $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution if register A does not equals to register P

stha
^^^^^^^^^^^^^^^^^^^^^^^^

+-------------------+-----------------------+-------------------+
| Mode              | Syntax                | Hex               |
+===================+=======================+===================+
| Absolute          | ``stha ($2e3c)``      | ``$13 $2e3c $00`` |
+-------------------+-----------------------+-------------------+
| Indirect          | ``stha ($2e3c, %?)``  | ``$13 $2e3c $1?`` |
+-------------------+-----------------------+-------------------+
| Indexed indirect  | ``stha (($2e3c, %?))``| ``$13 $2e3c $2?`` |
+-------------------+-----------------------+-------------------+
| Indirect indexed  | ``stha (($2e3c), %?)``| ``$13 $2e3c $3?`` |
+-------------------+-----------------------+-------------------+

Stores the lower 8 bits of register A into the data memory

stfa
^^^^^^^^^^^^^^^^^^^^^^^^

+-------------------+-----------------------+-------------------+
| Mode              | Syntax                | Hex               |
+===================+=======================+===================+
| Absolute          | ``stfa ($2e3c)``      | ``$14 $2e3c $00`` |
+-------------------+-----------------------+-------------------+
| Indirect          | ``stfa ($2e3c, %?)``  | ``$14 $2e3c $1?`` |
+-------------------+-----------------------+-------------------+
| Indexed indirect  | ``stfa (($2e3c, %?))``| ``$14 $2e3c $2?`` |
+-------------------+-----------------------+-------------------+
| Indirect indexed  | ``stfa (($2e3c), %?)``| ``$14 $2e3c $3?`` |
+-------------------+-----------------------+-------------------+

Stores the value of register A into the data memory. It will take two slots since each memory slot is 8 bits

ldha
^^^^^^^^^^^^^^^^^^^^^^^^

+-------------------+-----------------------+-------------------+
| Mode              | Syntax                | Hex               |
+===================+=======================+===================+
| Absolute          | ``ldha ($2e3c)``      | ``$15 $2e3c $00`` |
+-------------------+-----------------------+-------------------+
| Indirect          | ``ldha ($2e3c, %?)``  | ``$15 $2e3c $1?`` |
+-------------------+-----------------------+-------------------+
| Indexed indirect  | ``ldha (($2e3c, %?))``| ``$15 $2e3c $2?`` |
+-------------------+-----------------------+-------------------+
| Indirect indexed  | ``ldha (($2e3c), %?)``| ``$15 $2e3c $3?`` |
+-------------------+-----------------------+-------------------+

 Loads value from data memory to register A. Register A now contains an 8 bit value

ldfa
^^^^^^^^^^^^^^^^^^^^^^^^

+-------------------+-----------------------+-------------------+
| Mode              | Syntax                | Hex               |
+===================+=======================+===================+
| Absolute          | ``ldfa ($2e3c)``      | ``$16 $2e3c $00`` |
+-------------------+-----------------------+-------------------+
| Indirect          | ``ldfa ($2e3c, %?)``  | ``$16 $2e3c $1?`` |
+-------------------+-----------------------+-------------------+
| Indexed indirect  | ``ldfa (($2e3c, %?))``| ``$16 $2e3c $2?`` |
+-------------------+-----------------------+-------------------+
| Indirect indexed  | ``ldfa (($2e3c), %?)``| ``$16 $2e3c $3?`` |
+-------------------+-----------------------+-------------------+

Loads two values from two consecutive data memory slots to register A. Register A now contains a 16 bit value

swap
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``swap``  | ``$17``   |
+-----------+-----------+-----------+

Swaps values of register A and P

phc
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``phc``   | ``$18``   |
+-----------+-----------+-----------+

Pushes value of register C on to stack, increments stack pointer.
Overwrites previous data mapped on the memory

ppc
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``ppc``   | ``$19``   |
+-----------+-----------+-----------+

Pops from stack and stores into register C, decrements stack pointer.
Memory of the mapped data is preserved instead of being zeroed out

ret
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``ret``   | ``$0D``   |
+-----------+-----------+-----------+

Pops from stack and jumps to it, decrements stack pointer, used with jsr

jsr
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-------------------+-------------------+
| Mode      | Syntax            | Hex               |
+===========+===================+===================+
| Immediate | ``jsr $1234abcd`` | ``$0C $1234abcd`` |
+-----------+-------------------+-------------------+

Branches execution and saves the return address, increments stack pointer, used with ret

c2s
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``c2s``   | ``$1A``   |
+-----------+-----------+-----------+

Stores register C's value into stack pointer

swsc
^^^^^^^^^^^^^^^^^^^^^^^^

+-----------+-----------+-----------+
| Mode      | Syntax    | Hex       |
+===========+===========+===========+
| Implied   | ``swsc``  | ``$1B``   |
+-----------+-----------+-----------+

Swaps value of stack pointer and register C

Parameters
------------------------

In syntax section above, ``%?`` means a register. The registers can be (case insensitive):

* ``%a`` which has value of 0
* ``%p`` which has value of 1
* ``%c`` which has value of 2
* ``%ip`` which has value of 3
* ``%sp`` which has value of 4

The numerical constants used in immediate mode can be:

* ``123`` which has value of 123
* ``$ff`` which has value of 255
* ``:lb`` which is a label and value depende on where it is placed

Example
------------------------

The following program counts from zero.
It will reach the largest 32 bit value and then restart at zero again.
The register storing the counter is ``%c``.

::

        lda 1
    :loop
        add %a
        jmp :loop