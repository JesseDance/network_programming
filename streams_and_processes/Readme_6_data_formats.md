
Data Formats
============

The two most basic stream classes in Java are `InputStream` and `OutputStream`.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/InputStream.html">InputStrem</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/OutputStream.html">OutputSstream</a></li>
</ul>

All data that enters a process must pass through an `InputStream` object.
All data produced by a process must pass through an `OutputStream` object.
If you look at the list of methods in the `OutputStream` class, you will
see that the *only* type of data that an `OutputStream` object can write
is `byte`. Similarly for `InputStream`. So how do processes manage to
read and write all the kinds of data that we are used to, `int`,
`double`, `String`, `Color`, etc?

The answer to this question comes in two parts. First, every kind of data
type that we work with can be converted to and from bytes. Second, Java
provides a large number of specialized streams that do byte conversions
automatically.

Why do `InputStream` and `OutputStream` only work with bytes? Why not make
these two classes more versatile? The answer is that these two classes are
the interface to the computer's physical devices, the memory (RAM), storage
drive (SSD), network interface card (NIC), graphics card (GPU), keyboard,
etc. All of these devices communicate using bytes. For example, everything
on a storage device is stored as a sequence of bytes. The storage device does
not have one way to store text and another way to store video. All data is
converted to bytes and then the storage device only knows how to store bytes.
Same for the NIC. All data sent over a network is sent as a sequence of bytes.
The NIC never knows if it is transmitting a text document or a music stream.
The NIC only knows how to transmit and receive bytes.

`InputStream` and `OutputStrea` reflect this low level idea that everything
is a sequence of bytes. But that still doesn't answer the question of why
these two classes read and write *only* bytes. Why not give them methods to
read and write `String` objects and have those methods do the conversions
to and from sequences of bytes? The answer is a couple of related design
principles, the "Single-responsibility principle" and "Separation of concerns".
Classes should be as simple as possible and be designed to do just one task.
The `InputStream` class is designed to be an interface to the outside world
of bytes and so it only reads bytes. Some other class should have the task
(the responsibility) of converting a sequence of bytes into an `int` value
or a `String` object.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Single-responsibility_principle">Single-responsibility principle</a></li>
<li><a href="https://en.wikipedia.org/wiki/Separation_of_concerns">Separation of concerns</a></li>
</ul>

The Java library defines a number of classes and methods with the responsibility
to convert data types into sequences of bytes and sequences of bytes into data
types. Java also has a library of layered stream classes that can use the byte
conversion methods to make streams easier to use.

This document explains the methods that convert data types into bytes. A later
document will look at Java's library of streams that build on the `InputStream`
and `OutputStream` classes to let us conveniently read and write any data type
over streams.

All the example code mentioned in this document is in the sub folder called
"data_formats" from the following zip file.

* <http://cs.pnw.edu/~rlkraft/cs33600/for-class/streams_and_processes.zip>



## Bytes (and bits)

Let's quickly review what we mean by a byte value. A **byte** is a number
with eight binary digits. Each **binary digit** (or "bit") is either `0`
or `1`.

A byte is the basic unit of storage in a computer hardware system. Every
byte must contain exactly 8 bits. A byte is the smallest unit of data you
can store or retrieve. You cannot set or get a particular bit value in a
byte (but you can do "bitwise" logical operations on bytes). *All* data
stored in a computer system is represented as a sequence of one or more bytes.

Every byte holds an eight digit binary number. Binary numbers are a lot like
decimal numbers but they have only two digit values while decimal numbers
have ten digit values, `0` through `9`.

Remember that a decimal number like `1234` represents 4 "ones", 3 "tens",
2 "hundreds", and 1 "thousands".
```text
    1234 = (1 * 10^3) + (2 * 10^2) + (3 * 10^1) + (4 * 10^0)
```
We treat binary numbers in the same manner. The binary number `1101` has
1 "ones", 0 "twos", 1 "fours", and 1 "eights".
```text
    1101 = (1 * 2^3) + (1 * 2^2) + (0 * 2^1) + (1 * 2^0)
```
Using the above formula, you can translate the binary integer `1101` into
a decimal integer.

Since a byte *always* has eight binary digits, the place values in a byte
are from the "ones" place up to the "128's" place. The largest integer value
a byte can hold is:

<ul>
<li><a href="https://www.google.com/search?q=2%5E7+%2B+2%5E6+%2B+2%5E5+%2B+2%5E4+%2B+2%5E3+%2B+2%5E2+%2B+2%5E1+%2B+2%5E0">
     11111111 = (2^7) + (2^6) + (2^5) + (2^4) + (2^3) + (2^2) + (2^1) + (2^0) = 255.
</a></li>
</ul>

The bit that is on the left end of a byte is called the **most-significant-bit**.
The bit on the right end of a byte is called the **least-significant-bit**.
Think of a number like $2,001. In a bank account, a mistake in the 2 is much
more significant than a mistake in the 1.

What we just described are "hardware bytes", the eight bit values stored
in computer hardware. Java has a `byte` data type that is similar, but not
quite the same as a "hardware byte". A Java `byte` is eight binary digits,
but it is considered by Java to be a "two's complement binary number" (not
a "binary number", as in a hardware byte). The difference is in the
interpretation of the most-significant-bit. In a Java `byte`, the
most-significant-bit represents the place value "-128" (instead of "128").
For example, the Java `byte` value `10000001` represents the decimal number
-128 + 1 = -127. You can verify this using JShell.

```text
    jshell> (byte) 0b10000001
```

You can think of the most-significant-bit in a Java `byte` as the "sign bit".
A Java byte is a negative number if, and only if, its most-significant-bit
is `1`.

We have said that *all* data in a computer system is made up of (hardware)
bytes. It is not accurate to say that all Java values are made up of Java
bytes. A Java `int` is not made up of four Java `byte` values (a Java `int`
is made up of four hardware byte values). The difference between a hardware
byte and a Java `byte` can be confusing. When talking about representing data,
we are usually talking about hardware bytes. Java does not have a data type
for hardware bytes (an "unsigned byte"). When we are forced to use Java `byte`
values to represent hardware bytes, we have to be very careful and often resort
to some trickery (like casting).

<ul>
<li><a href="http://www.javapuzzlers.com/java-puzzlers-sampler.pdf#page=7">Puzzle 3: A Big Delight in Every Byte - Java Puzzlers</a></li>
</ul>

In the C and C++ programming languages, the "unsigned byte" data type is
a hardware byte and the "signed byte" data type is like a Java `byte`,
an eight bit 2's complement binary integer.

The "hardware bytes" we have described are sometimes called **octets**.
The word "octet" is used in Internet literature instead of the word
"byte" because byte has too many meanings and has become ambiguous.
An octet is just 8 bits.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Octet_(computing)">Octet</a></li>
</ul>

Since there are 8 bits in a `byte`, there are `2^8 = 256` distinct `byte`
values. The range of values in Java's `byte` data type is from `-128` to
`127`. Notice the slight asymmetry. That's because `0` is considered a
positive number. There are 128 negative numbers and 128 positive numbers.

The `byte` data type is an integer data type, so we can do all the usual
arithmetic operations on `byte` values. But as an integer data type, `byte`
is not useful since it holds so few values. The main use for `byte` values
is to represent the "hardware bytes" that are used in physical devices.

When we use a `byte` value to represent a hardware byte, we are usually
interested in the actual bit values in the `byte`. But representing a `byte`
value with a decimal number, like `113`, does not help us see what the
individual bits are in the `byte`. It is possible to convert the decimal
number into a binary number, but that is time consuming and tedious. There's
a better way to represent a `byte` value so that we can (almost) immediately
see what its individual bit values are.

The trick is to represent the value in a `byte` using hexadecimal notation.

In **hexadecimal notation**, we assign to every possible 4 bit word a digit
from `0` to 'F'. There are 16 possible 4 bit binary words, and there are 10
decimal digits `0` to `9`, and then there are six letters `A` through `F`.
Here is how the 16 hexadecimal digits are assigned to 4 bit words. You
should remember this table!

```text
    0000   0
    0001   1
    0010   2
    0011   3
    0100   4
    0101   5
    0110   6
    0111   7
    1000   8
    1001   9
    1010   A
    1011   B
    1100   C
    1101   D
    1110   E
    1111   F
```

A good trick to remember is that `A`, which is decimal 10, is binary `1010`.

For an 8 bit `byte` we need two hexadecimal digits to specify the value in
the `byte`.

In Java (and in almost every current programming language) we write a hexadecimal
literal by putting a `0x` in front of hexadecimal digits. So `0xA` is a one digit
hexadecimal number (it specifies 4 bits), `0xB7` is a two digit hexadecimal
number (it specifies 8 bits), and `0xF3A` is a three digit hexadecimal number
(it specifies 12 bits).

One quirk of the Java language is that every hexadecimal literal represents a
32 bit `int` value. For example, `0xFF` specifies eight bits out of the 32 bits
in the `int` value and all the other bits are `0`. So `0xFF` specifies the
`int` value `0x000000FF`, which is the decimal number `255`.

With practice, you can quickly go from hexadecimal notation to binary notation
and back. It is not necessary, and not all that useful, to be able to go back
and forth between decimal and hexadecimal notation.

Remember that *all* data in a computer system is represented as a sequence of
bytes. So every file in your computer's storage device is stored as a sequence
of bytes. If the data in a file is text data, then you can open the file in a
text editor to see what data is in the file. When a file holds data that is
*not* text data, we say that the file is a **binary file**. If you open a
binary file in a text editor, it will appear to be gibberish. When we want
to represent the data in a binary file, we usually represent the data in
hexadecimal notation and we call that representation a **hex dump**.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Binary_file">Binary File</a></li>
<li><a href="https://en.wikipedia.org/wiki/Hex_dump">Hex Dump</a></li>
<li><a href="https://en.wikipedia.org/wiki/Hexadecimal">Hexadecimal</a></li>
</ul>

* <https://betterexplained.com/articles/a-little-diddy-about-binary-file-formats/>

Here are some examples. In the "filters.jar" file there is a filter program
called "HexDump.java". It reads a sequence of bytes from its input stream
and writes a "hex dump" for those bytes to its output stream. You can use
"HexDump" to display the hexadecimal notation of the data in any file
(binary or text file).

A command-line like this will display in the console window a hex dump
of the given file.

```text
    > java -cp filters.jar HexDump < Readme.txt
    > java -cp filters.jar HexDump < filters.jar
```

You can use output redirection to save a hex dump as a text file.

You can use a command-line like the following to search a file for
a particular byte value.

```text
    > java -cp filters.jar HexDump 16 < Readme.txt |^
      java -cp filters.jar LineNumbers |^
      java -cp filters.jar Find 0D
```

The following command-line will tell you the exact location of every '>'
character in the file.

```text
    > java -cp filters.jar HexDump 1 < Readme.txt |^
      java -cp filters.jar LineNumbers |^
      java -cp filters.jar Find 3E
```

If you use the "filters-with-classpath.cmd" script file, then you do not
need to type all the classpath parameters.

```text
    filters> java HexDump 16 < Readme.txt | java LineNumbers | java Find 0D
    filters> java HexDump 1  < Readme.txt | java LineNumbers | java Find 3E
```

There are many programs that can display a hexadecimal representation of
a file's data. These kinds of programs are usually called "hex editors".
They can do a lot more than "HexDump.java".

There is an online hex editor called ImHex. You can drag-and-drop a file
onto its web page and it will open the file in its hex view. This is
really convenient and easy to use.

<ul>
<li><a href="https://web.imhex.werwolv.net/">ImHex online</a></li>
<li><a href="https://docs.werwolv.net/imhex">ImHex Documentation</a></li>
</ul>

If you use VS Code, you can add a hex editor extension written by Microsoft.

<ul>
<li><a href="https://github.com/microsoft/vscode-hexeditor#readme">VS Code Hex Editor - microsoft - GitHub</a></li>
<li><a href="https://marketplace.visualstudio.com/items?itemName=ms-vscode.hexeditor">VS Code Hex Editor - Visual Studio Marketplace</a></li>
</ul>

If you want a small, simple, stand-alone hex editor, then XVI32 is a good option.

<ul>
<li><a href="http://www.chmaas.handshake.de/delphi/freeware/xvi32/xvi32.htm">Freeware Hex Editor XVI32</a></li>
</ul>


In the folder "data_formats" there are sample programs "CreateData.java"
and "ReadDataAsInt.java". The "CreateData.java" program writes 16 bytes of
data to its standard output stream. The key idea is that this data is just
16 bytes. There is no meaning or interpretation attached to those bytes.
Those 16 bytes can be read by other programs and interpreted as any kind
of data they want.

The program "ReadDataAsInt.java" reads 16 bytes of data from its standard
input stream and *assumes* those bytes represent four `int` values. We can
pipe the output of "CreateData" into the input of "ReadDataAsInt".
```text
    > java CreateData | java ReaddDataAsInt
```
This shows us the four `int` values that those 16 bytes represent.

On the other hand, we can pipe the output of "CreateData" into the input
of "ReadDataAsDouble.java". That program reads 16 bytes of data from its
standard input stream and *assumes* those bytes represent two `double`
values.
```text
    > java CreateData | java ReaddDataAsInt
```
This shows us the two `double` values that those 16 bytes represent.

Those 16 bytes of data can be interpreted as a stream of any Java primitive
data type. The program "ReadDataAsFloat.java" reads the data as a stream of
four `float` values. The program "ReadDataAsShort.java" reads the 16 bytes as
a stream of eight `short` values. The program "ReadDataAsMixedTypes.java"
reads the 16 bytes as several different data types (one long, one int,
and two shorts).

Every file on your computer's storage device is stored as a stream of bytes.
Just like the stream of 16 bytes written by "CreateData.java", the stream of
bytes in a file have *no* meaning or interpretation attached to them. The
stream of bytes in a file can be read by a program as any kind of data type
the program wants. If a program is a text editor, it will interpret the file's
stream of bytes as text, even if the producer of those bytes intended them to
be a stream of `int` values, and the text editor's view of the data will look
like gibberish. But it is not "wrong" for the text editor to interpret the
stream of bytes as text (you might be looking for text segments in a binary
executable ".exe" file).

Use the online hex editor ImHex to look inside of several files from your
computer's storage device (you can drag-and-drop a file onto its web page
and it will open the file in its hex view). Open several different kinds of
files, ".txt", ".docx", ".png", ".class", ".exe", ".pdf". You should notice
that they all look pretty much alike, just a stream of hex values (a stream
of bytes).

<ul>
<li><a href="https://web.imhex.werwolv.net/">ImHex online</a></li>
</ul>

The Windows operating system uses filename extensions as a way to assign
an interpretation to the stream of bytes in a file. If a file has the
extension ".txt", then Windows *assumes* that the stream of bytes in the
file represent text data. But filename extensions are not reliable. Anyone
can change a file's name. A file's name may not even have an extension. If
a file ends up with the wrong filename extension, or no filename extension,
then it can be difficult to find out what the stream of bytes in the file
represent.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Filename_extension">Filename extension</a></li>
</ul>

Finally, remember these two facts concerning the Java `byte` data type.
(Forgetting them leads to mistaken code and misunderstood error messages.)

1. A `byte` is an 8 bit 2's complement integer with a range from `-128` to `127`.
2. Every hexadecimal literal represents a 32 bit `int` value.



## ASCII

Bytes are closely associated with ASCII code. ASCII code is used to represent
the characters on your keyboard. There are about 100 characters on your
keyboard. In the ASCII code, each character is represented by a 7 bit word
(NOT an 8 bit word). There are 2^7 = 128 possible 7 bit words, so the ASCII
code has room for all of your keyboard characters plus a collection of
special "control characters". Each number between 0 and 127 represents one
ASCII character. The numbers from 0 to 31 represent the special control codes.
These are also called the "non-printable characters". The actual keyboard
characters start at number 32, with the space character. The ASCII codes from
32 to 126 are all "printable" ASCII codes (real keyboard characters). Code
number 127 is called `DEL` and it is not a printable character.

Look at the following ASCII table for a bit.

* <https://www.ascii-code.com/>

ASCII codes are always stored in byte values, which is why bytes and ASCII
code are so closely related. But ASCII codes are only 7 of the 8 bits in
a byte. The ASCII code is always stored as the 7 least significant bits in
a byte and the most significant bit is `0`.

Any byte value whose most significant bit is `0` can be interpreted as an
ASCII code. That does not mean it should interpreted as an ASCII code.
Whoever stored the byte value may have meant it to be an ASCII code, or they
may have meant the byte to mean something else. Just by looking at the byte,
there is no way to tell.

If a byte's most significant bit is `1`, then that byte *cannot* be interpreted
as an ASCII code. But now things become complicated. The byte may be what is
called an "Extended ASCII code".

The 100 or so characters on your keyboard are fine for writing English, but
are not good enough for almost any other language. Since there are 128 values
in a byte not used by the ASCII code, there is a potential to create codes for
128 more characters, characters used in some other languages. "Extended ASCII"
is a choice of characters for each of the code numbers from 128 to 255. The
problem with Extended ASCII is that there is no standard way to make these
choices. There are literally hundreds of versions of Extended ASCII.
Extended ASCII is a big problem. In modern computer systems, it has been
mostly replaced by UTF-8. We will say more about this in a later document.

Windows uses a version of Extended ASCII called "Windows-1252".

* <https://en.wikipedia.org/wiki/Windows-1252>

If you are given a byte value, and the byte's most significant bit is `0`,
then you may interpret the byte as an ASCII character and you know which
character it represents (but, you may be wrong to interpret the byte as
an ASCII character). If the byte's most significant bit is a `1`, then
you may interpret the byte as an Extended ASCII character, but you have
no idea what character it represents, unless you have an agreement on
which version of Extended ASCII to use. If you are using a Windows
computer, you would probably interpret the byte using the Windows-1252
version of Extended ASCII.

When you type a key on your keyboard, the keyboard communicates with the
rest of the computer system to let it know what key you typed. Like all
communication in a computer system, the keyboard communicates by sending
bytes, one byte for every keystroke (more or less). ASCII codes are the
bytes that the keyboard communicates.

We want to think of the keyboard as a "byte generator", a tool for sending
byte values into a process. We want to think of the keyboard as the source
of the byte stream connected to the standard input stream of a process.
Thinking of the keyboard this way will help us understand how streams and
data formats work.

If we consider the keyboard as a source of byte values, then an interesting
problem is to find a way to use the keyboard to generate every one of the
256 byte values.

For example, we can easily generate the byte values of the printable ASCII
characters with code values between 32 and 126 by just typing the appropriate
keyboard character. If we want the byte value 80, we can look in an ASCII
table and see that we just need to tap the `P` key. You can verify in JShell
that the 'P' character has value 80.

```text
    jshell> byte b = 'P'
```

What about the non-printable ASCII control codes from 0 to 31, or the Extended
ASCII codes from 128 to 255?

Most of the ASCII control codes can be generated using the `Ctrl` key on your
keyboard (that's how this key got its name!). You hold down the `Ctrl` key
and tap some other key. That generates an ASCII control code.

Here is a list of the non-printable ASCII control codes that we can enter
at the console window using the keyboard's `Ctrl` key.

```text
             Dec    Hex
    Ctrl-A     1   0x01
    Ctrl-B     2   0x02
    Ctrl-D     4   0x04
    Ctrl-E     5   0x05
    Ctrl-F     6   0x06
    Ctrl-G     7   0x07
    Ctrl-I     9   0x09
    Ctrl-K    11   0x0B
    Ctrl-L    12   0x0C
    Ctrl-N    14   0x0E
    Ctrl-O    15   0x0F
    Ctrl-P    16   0x10
    Ctrl-Q    17   0x11
    Ctrl-R    18   0x12
    Ctrl-T    20   0x14
    Ctrl-U    21   0x15
    Ctrl-W    23   0x17
    Ctrl-X    24   0x18
    Ctrl-Y    25   0x19
    Ctrl-Z    26   0x1A
    Ctrl-[    27   0x1B
    Ctrl-]    29   0x1D
    Ctrl-^    30   0x1E
    Ctrl-_    31   0c1F
```

A few ASCII control codes, like Ctrl-C, have a special meaning to Windows.
We can't use those control keys at the console window to enter an ASCII value
because the operating system captures those control codes and does something
special with them. Here is a list of the special control codes.

```text
            Dec    Hex
   Ctrl-@     0   0x00    NULL
   Ctrl-C     3   0x03    kill
   Ctrl-H     8   0x08    Backspace
   Ctrl-J    10   0x0A    Line Feed
   Ctrl-M    13   0x0D    Carriage Return
   Ctrl-S    19   0x13    XOFF (stop scrolling output)
   Ctrl-V    22   0x16    paste from the clipboard
   Ctrl-/    28   0x1C    ?
```

There is a way to enter some of these ASCII control characters from the
keyboard, but we need to use a different mechanism than the `Ctrl` key.
Besides the `Shift` and `Ctrl` keys there is one more "modifier key" on
your keyboard, the `Alt` key. Windows has a mechanism for using the `Alt`
key in combination with the "Numeric Keypad" to enter a wide variety of
special characters. Microsoft calls these the **Alt codes**. There is an
AlT code for every ASCII character, including the above control characters.
But the Alt codes for ASCII control codes do not work on all computers. On
any given computer, some of the Alt codes for the ASCII control codes will
work and others wont.

Here is how you can try, on your computer, to enter the ASCII NULL character
into a console window (this will not work in most GUI programs). First, open
a command-prompt (console) window. To enter the NULL character from the
keyboard, you must use the `0` key from the numeric keypad (NOT the `0` key
in the row of number keys) and you must have "Num Lock" turned on. Press the
`Alt` key down, and while holding it down, tap the `0` key on the numeric
keypad. Then release the `Alt` key. The NULL character will (may) appear in
the console window when you release the `Alt` key. It will appear as `^@`.
If that doesn't work, try holding down the `Alt` key and tapping the `0` key
twice. If that still doesn't work, try tapping the `0` key three times. If
even that doesn't work, your computer probably cannot generate the NULL
character.

You can also try the Ctri-C code. With "Num Lock" turned on, hold down the
`Alt` key and tap the numeric key `3` and then release the `Alt` key. You may
get a `^C` character in the console window, or the operating system may have
received the Kill signal. If you didn't get `^C`, try holding down the `Alt`
key and tapping the `0` key followed by the `3` key, and then release the `Alt`
key. If you still don't get `^C`, try `Alt`+'0'+'0'+'3' (hold down the `Alt`
key and tap the three other numeric keys, then release the `Alt` key`).

You can try any ASCII code. Depending on your computer, you may need to tap
one, two, or three numeric keys while holding down the `Alt` key.

On my computer, I need to tap two numeric keys to generate `^C` but I need
to tap three numeric keys to generate `^Y`. And I cannot generate `^H`. Every
Alt code I try gets intercepted by the operating system and becomes "backspace".

On your computer, you can also try using Alt codes to enter the Extended ASCII
character codes. In the simplest case, you hold down the `Alt` key, tap the
three decimal digits for the numeric value of the ASCII code, and then release
the `Alt` key.

In the next section we will use ASCII codes as a way to use the keyboard to
enter arbitrary byte values into console programs. This will be a way for us
to observe how different data types interpret byte values.

In general, don't think of these tricks as "Alt codes for ASCII codes". Think
of them as "Alt codes for byte values".


We can test the above tricks for generating ASCII codes using two programs
from "filters.jar", the "HexDump.java" and "DecimalDump.java" programs.
Enter this command-line in a folder that contains the file "filters.jar".

```text
    > java -cp filters.jar  DecimalDump 3
```

When the program is expecting input, tap any of the printable keys and
then tap `Enter`. You should get a response line with three numbers on it,
the ASCII code for your key, followed by the two numbers `13` and `10`. These
are the ASCII control codes for "carriage return" and "line feed". These two
codes are the result of your tapping the `Enter` key. Open an ASCII table in
your web browser and verify a few ASCII code values. Try generating some of
the ASCII control codes that can be entered using the `Ctrl` key. To generate
an Extended ASCII code, use the "Num Lock" key, the `Alt` key, and the
numeric keypad. Try entering the decimal code 200.

Here is a transcript I created on my computer. I typed 'q', Ctrl-G, Ctrl-Y,
the Alt code 200, and the Alt code 195. The characters that I got for Alt
codes 200 and 195 may be different that the characters you see on your
computer.

```text
    > java -cp filters.jar  DecimalDump 3
    q
    113   13   10
    ^G
      7   13   10
    ^Y
     25   13   10
    +
    200   13   10
    +
    195   13   10
```

* <https://www.ascii-code.com/>
* <https://ss64.com/ascii.html>
* <https://en.cppreference.com/w/c/language/ascii.html>
* <https://bestasciitable.com/>



## Integers

In the `Integer` wrapper class, there is a built-in number that tells us
how many bytes there are in an `int` value.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Integer.html#field-summary">java.lang.Integer.BYTES</a></li>
</ul>

Most of the wrapper classes for the primitive types have a `BYTES` field
built into them. If you open a JShell session, then you can quickly see
what their values are.

```text
    jshell> Long.BYTES
    jshell> Integer.BYTES
    jshell> Short.BYTES
    jshell> Byte.BYTES
    jshell> Double.BYTES
    jshell> Float.BYTES
    jshell> Character.BYTES
```

There are four bytes in an `int` value. That is 32 bits. So there are
<ul>
<li><a href="https://www.google.com/search?q=2%5E32">2^32 = 4,294,967,296</a></li>
</ul>
distinct `int` values. These values are equally divided between positive and
negative values (an `int` is a 32 bit 2's complement integer). The range of
`int` values is from
<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Integer.html#field-summary">Integer.MIN_VALUE = -2,147,483,648</a></li>
</ul>
to
<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Integer.html#field-summary">Integer.MAX_VALUE = 2,147,483,647</a></li>
</ul>
You can get these values from JShell.
```text
    jshell> Integer.MIN_VALUE
    jshell> Integer.MAX_VALUE
```

It is important to realize that *every* `int` value is made up of four bytes.
Many people mix up the size (magnitude) of a number with how many bytes are
used to store it. The decimal integer `10` is written in binary as `1010` so
it has four binary digits. But it is stored in computer memory as an `int`
value with 32 bits (four bytes). The extra 28 bits are all `0`, so the `int`
is stored in memory as `00000000000000000000000000001010`. Computers do not
optimize the storage of numbers and use fewer bytes for small numbers and
more bytes for large numbers. All `int` values are stored using four bytes.

The decimal number `10` is written in hexadecimal as `A`, a single hexadecimal
digit. The `int` value for hexadecimal `A` (and decimal `10`) is stored as 32
bits, which can be written in hexadecimal as `0000000A`, with eight hexadecimal
digits. The four bytes that make up this `int` can be written in hexadecimal
as `0x00`, `0x00`, `0x00`, and `0x0A`.

Here is a slightly more complex example. The hexadecimal number `0x0810040A`
has 32 bits, so it is an `int` value. The four bytes in this `int` can be
written in hexadecimal as `0x08`, `0x10`, `0x04`, `0x0A`. There are only five
bits in this number that are `1`, the other 27 bits are `0`. The five bits
that are `1` are in the 2's place, the 8's place, the 1024's place, the
2^20's place, and the 2^27's place. So the hexadecimal number `0x0810040A`
has the decimal value,

<ul>
<li><a "https://www.google.com/search?q=2+%2B+8+%2B+1024+%2B+2%5E20+%2B+2%5E27">
2 + 8 + 1024 + 2^20 + 2^27 = 135,267,338.</a>
</ul>

Remember that in a decimal number like `1,005`, we say that the `1` is the
most significant digit and the `5` is the least significant digit. Similarly,
a binary number has a most significant bit and a least significant bit. In an
`int` value like `0x0810040A` we call `0x08` the **most significant byte**
(MSB) and we call `0x0A` the **least significant byte** (LSB). One thing to
note is that if someone writes a decimal number as `00123`, then we are not
likely to say that the most significant digit is `0`. But in an `int` value
like `0x00654321`, we *do* say that the most significant byte is `0x00`.

The `Integer` class does not have a method to convert an `int` value
into its four bytes. To do that, we use the `java.nio.ByteBuffer`
class.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/ByteBuffer.html">java.nio.ByteBuffer</a></li>
</ul>

The `ByteBuffer` class can be thought of as a factory class for constructing
arrays that hold the bytes from primitive values. The `ByteByuffer` class does
not have any constructors. It has static factory methods that build and return
`ByteArray` objects.

The static factory method `allocate(int)` builds and returns a `ByteBuffer`
of the specified size.

The static factory method `wrap(byte[])` builds and returns a `ByteBuffer`
from the given `byte` array.

There is also an `array()` method that extracts a `byte` array from
a `ByteBuffer` object.

Here is a line of code that creates a `ByteBuffer` object holding the four
bytes from an `int` value and then converts the `ByteBuffer` into a `byte`
array.

```java
    byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                             .putInt(123456)
                             .array();
```
This line of code lets us see the four `byte` values that make up the `int`
value `123456`.

Notice that the `ByteBuffer` class, like the `ProcessBuilder` class,
has a fluent API.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/ByteBuffer.html#invocation-chaining-heading">java.nio.ByteBuffer - Invocation Chaining</a></li>
</ul>

We can also go in the other direction. Given a `byte` array holding four
bytes, we can find out what `int` value those bytes represent.

The following code defines an array of four bytes, wraps a `ByteBuffer`
object around them, and then uses the `ByteBuffer` method `getInt()`
to get the `int` value that the four bytes represent.

```java
    byte[] bytes = {0, 1, -30, 64};
    int n = ByteBuffer.wrap(bytes)
                      .getInt();
```

You can do these `ByteBuffer` operation in JShell. At a JShell prompt, enter
the following line of code to get the `byte` array representation of an `int`
value.

```java
    jshell> var bytes = java.nio.ByteBuffer.allocate(Integer.BYTES).putInt(123456).array()
```

At the JShell prompt, enter the following line of code to go in the other
direction and find the `int` value represented by four `byte` values in
an array.

```java
    jshell> var n = java.nio.ByteBuffer.wrap(new byte[]{0, 1, -30, 64}).getInt()
```

Notice that when JShell prints out a `byte` array, the bytes are written in
decimal notation (not hexadecimal). On the other hand, when we write the code
for a `byte` array, we can write the individual bytes using either decimal or
hexadecimal notation. We can even mix up the notations. Notice that in the
following code, the two middle bytes are written in decimal, and the MSB and
LSB are written in hex.

```java
    jshell> var n = java.nio.ByteBuffer.wrap(new byte[]{0x10, 32, 16, 0x20}).getInt()
```

But we need to be careful. We are using Java `byte` values to represent what
we earlier referred to as "hardware bytes", and they are not equivalent. For
example, the following line of code in JShell looks like it holds four `byte`
values, but it does not compile.

```java
    jshell> var n = java.nio.ByteBuffer.wrap(new byte[]{0x80, 0xAB, 0xFF, 0xC0}).getInt()
```

On the other hand, the following line of code doesn't look like it contains
`byte` values, but it does.

```java
    jshell> var n = java.nio.ByteBuffer.wrap(new byte[]{0xFFFFFFF0, 117, -0x78, -125}).getInt()
```

We can fix the first line of code by casting all the "byte" values to `byte`.

```java
    jshell> var n = java.nio.ByteBuffer.wrap(new byte[]{(byte)0x80, (byte)0xAB, (byte)0xFF, (byte)0xC0}).getInt()
```

The problem is that Java does not have "byte literals". Java only has
"int literals" and "long literals". When we use the value `0xFF`, that is not
a `byte`, it is an `int` and it is really the value `0x000000FF`, because it
has to have 32 bits (not eight bits). The value `0xFF` is the integer 255
(try it in JShell).
```java
    jshell> 0xFF
```
The largest positive integer that can be stored in a `byte` is 127 (remember,
half of the `byte` values are negative integers and half are positive). The
value `0xFF` is out of the range of the `byte` data type.

When we use the notation `(byte)0xFF` we are telling Java to throw away the
24 most significant bits from `0x000000FF` which leaves just the least
significant eight bits `11111111` which is the 2's complement value for -1.
```java
    jshell> (byte)0xFF
```


**Exercise:** Why are `0xFFFFFFFF` and `0xFFFFFFF0` `byte` values
but `0xFFFFFF00` is not?


We can take our code that converts an `int` value into bytes, and our code
that converts bytes back into an `int` value, and create a "client/server"
pair of programs where the server program writes an `int` value, as four
bytes, to its standard output stream, and the client program reads an `int`
value, as four bytes, from its standard input stream. We can then use these
two programs to do a number of experiments, such as putting them on either
end of a pipe.

Here is the code for "IntServer.java". It reads an integer value from its
standard input stream as a `String` of digits, uses a `ByteBuffer` object
to get the four bytes that make up that integer, and then writes those
four bytes to its standard output stream.

```java
import java.nio.ByteBuffer;
public class IntServer
{
   public static void main(String[] args)
   {
      final int n = new java.util.Scanner(System.in).nextInt();
      byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                               .putInt(n)
                               .array();
      System.out.write(bytes[0]);
      System.out.write(bytes[1]);
      System.out.write(bytes[2]);
      System.out.write(bytes[3]);
      System.out.flush(); // Try commenting this out.
   }
}
```

Here is the code for "IntClient.java". It reads four bytes from its standard
input stream, uses a `ByteBuffer` object to convert those four bytes into
an integer value, and then sends that integer value as a `String` of digits
to its standard output stream.

```java
import java.nio.ByteBuffer;
import java.io.IOException;
public class IntClient
{
   public static void main(String[] args) throws IOException
   {
      final int b0 = System.in.read();
      final int b1 = System.in.read();
      final int b2 = System.in.read();
      final int b3 = System.in.read();
      final byte[] bytes = {(byte)b0,
                            (byte)b1,
                            (byte)b2,
                            (byte)b3};
      final int n = ByteBuffer.wrap(bytes)
                              .getInt();
      System.out.println(n);
   }
}
```

We can connect these programs using a pipe.

```text
    > java IntServer | java IntClient
```

Type an integer at the console and tap `Enter`. The server reads the integer,
sends four bytes over the pipe, and closes the pipe. The client reads four
bytes from the pipe, puts them together as an int, and prints the result.

Why do we go through so much trouble to send an integer value from one
process to another? Why can't we just send the `String` of digits over
the pipe? Remember that the `InputStream` and the `OutputStream` classes
can only read and write `byte` values. We do not yet know how to send a
`String` value over a stream. So we convert the `int` value to its bytes,
send the bytes over the stream, convert the bytes back into an `int` value,
and then print the digits of the integer.

There is an advantage to doing the transmission this way. Consider the
integer value `1234567890`. This is a 10 digit number. As an `int`, it needs
four bytes to transmit the value. But as a string of digits, we will see
that it could need anywhere from 10 to 40 bytes of data, depending on the
"character encoding" used in the transmission.

On the other hand, the integer value `1` also needs four bytes in order to
be transmitted as an `int`. As a string of digits, it could be transmitted
as a single byte.

We can break the above pipeline up into two steps. First, run the following
command-line.

```text
    > java IntServer > temp
```

Type an integer at the console and tap `Enter`. The server reads the string
of digits that you entered, converts it to an `int` value, writes the four
`byte` values of the `int` to the file "temp", and then closes the output
stream. Use the Windows Explorer view of your folder to see that the file
"temp" holds exactly four bytes.

Next, run the following command-line.

```text
    > java IntClient < temp
```

The client reads four `byte` values from the file "temp", puts them together
as an `int`, and then prints the `int` value, as a string of digits, to its
output stream. That string of digits should be the string you entered into
the server program.

The "temp" file created by redirecting the output from "Server.java" is a
binary file. It holds the 4 bytes of the integer value that you typed into
the server program. It *does not* hold the `String` representation of that
integer value. If you open the "temp" file with a text editor, you will not
see the integer that you typed into the server program.

In the "filters.jar" file there is a filter program called "HexDump.java".
It reads a sequence of bytes from its input stream and writes the hexadecimal
notation for those bytes to its output stream. You can use "HexDump" to
display the hexadecimal representation of the four bytes in the "temp" file.

```text
    > java -cp filters.jar HexDump < temp
```

For example, if your input integer was `-1`, then the four bytes will be `FF`,
`FF`, `FF`, and `FF` (in hexadecimal). If your input integer was `0`, then the
four bytes will be `00`, `00`, `00`, and `00`. Notice that *every* integer
value, no matter how large or small, will be represented by exactly four bytes.

In "filters.jar" there is also a filter program called "BinaryDump.java".
It writes the binary notation for the bytes it reads from its input stream.
You can use "BinaryDump" to display all the bits in the four bytes in the
"temp" file.

```text
    > java -cp filters.jar BinaryDump < temp
```

You can also use any hex editor to open the "temp" file to see the four bytes
that represent your integer value.

We can pipe the server program directly into either of the dump programs and
see right away the bytes used to represent an `int` value,

```text
    > java IntServer | java -cp filters.jar  HexDump
    > java IntServer | java -cp filters.jar  BinaryDump
```


**Exercise:** Run this command-line,
```text
    > java IntServer
```
and when the program expects input, type "123" and tap the `Enter` key.
Carefully explain the output. Then run the command-line again and type
the following number when the program expects its input, "1667331187".
Carefully explain the output. Can you get the program to output "hope"?
What about the output "new hope"? (Hint: `long`.)


**Exercise:** Run this command-line,
```text
    > java IntClient
```
and when the program expects input, tap the `Enter` key two times.
Carefully explain the output.


**Exercise:** Run this command-line,
```text
    >java IntServer | java -cp filters.jar DecimalDump
```
and when the program expects input, type "876543210" and tap the `Enter`
key. You should get this output.
```text
 52   62  252  234
```
These are the decimal values of the four bytes that make up the `int` value
`876543210`. Now run this command-line,
```text
    > java IntClient
```
and when the program expects input, use "Num Lock", the `Alt` key, and the
numeric keypad to enter those four byte values from the keyboard. After
you enter those four byte values, tap the `Enter` key. You should get the
response "876543210". (Hint: You may need to enter "52" as "052". Same
for "62".) Do this exercise again with the number "1234567890".


**Exercise:** In your own words, explain what "IntServer.java" expects as
input. What does it produce as output? What does "IntCliet.java" expect
as input? What does it produce as output?


We can convert our client/server programs so that they work with more than
one `int` value. Below is a client/server pair where each program takes a
command-line argument specifying how many `int` values that program should
use. With these programs we can do some more experiments.

```java
import java.nio.ByteBuffer;
import java.util.Scanner;

public class IntServerN {
   public static void main(String[] args) {
      int n = 1; // Default value for n.
      if (args.length >= 1) {
         try {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e) {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      final Scanner in = new Scanner(System.in);
      for (int j = 0; j < n; ++j) {
         final int i = in.nextInt();
         byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                                  .putInt(i)
                                  .array();
         System.out.write(bytes[0]);
         System.out.write(bytes[1]);
         System.out.write(bytes[2]);
         System.out.write(bytes[3]);
         System.out.flush(); // Try commenting this out.
         if ( System.out.checkError() )
            throw new RuntimeException(
               "System.out has encountered an IOException");
      }
   }
}
```

```java
import java.nio.ByteBuffer;
import java.io.IOException;

public class IntClientN {
   public static void main(String[] args) throws IOException {
      int n = 1; // Default value for n.
      if (args.length >= 1) {
         try {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e) {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      for (int j = 0; j < n; ++j) {
         final int b0 = System.in.read();
         final int b1 = System.in.read();
         final int b2 = System.in.read();
         final int b3 = System.in.read();
         final byte[] bytes = {(byte)b0,
                               (byte)b1,
                               (byte)b2,
                               (byte)b3};
         final int i = ByteBuffer.wrap(bytes)
                                 .getInt();
         System.out.println(i);
      }
   }
}
```

For example, you can compare the hexadecimal (or binary, or decimal)
representation of the four bytes in several `int` values.

```text
    > java IntServerN 5 | java -cp filters.jar HexDump 4
    > java IntServerN 5 | java -cp filters.jar BinaryDump 4
    > java IntServerN 5 | java -cp filters.jar DecimalDump 4
```

Enter the following command-line.

```text
    > java IntServerN 5 | java IntClientN 5
```

Enter five integer values. You should get them echoed back.

Now explain the results from the following command-line.

```text
    > java IntServerN 3 | java IntClientN 6
```

Next, explain the results from the following command-line.

```text
    > java IntServerN 6 | java IntClientN 3
```

When you enter the six numbers into "IntServerN", do it three ways. First,
enter each number on its own line (each number should be followed with the
`Enter` key). Second, enter the six numbers all on one line (only tap the
`Enter` key once, after typing all six numbers). Third, enter four numbers
all on one line, and then enter a single number on its own line. Why do you
think the program behaves so differently? Hint: You are seeing the results
of a race condition caused by the two programs, "IntServerN.java" and
"IntClientN.java" running at the same time (concurrently).


**Exercise:** Suppose you need to store 1,000 random `int` values in a file.
Should you store them as strings or as bytes? Why? Write two demo programs,
one that stores 1,000 random integers as string values and the other that
stores then as byte values. Which program produces smaller files? What if
the 1,000 random integers are all in the range from 0 to 100?


**Exercise:** Run the following command-line,
```text
    > java IntServerN 4
```
and when the program expects input, type an integer number, then tap the
`Enter` key, then type `Ctrl-z` and tap the `Enter` key again. The program
crashes. Rerun the command-line and when the program expects input, type
this string, "80 81 dog 82 83", then tap the `Enter` key. The program crashes.
Try to fix these problems. How should the program behave instead of crashing?
(Hint: The command-line argument N shouldn't mean "exactly N", it should mean
"at most N". And the N should count only valid integer tokens.) Notice that
this exercise is not really about data formats, its about the proper use of
the `Scanner` class.


**Exercise:** Write a program called "ReadStreamAsInt.java" that reads its
standard input stream as an unlimited sequence of four-byte `int` values.
The program should print each `int` value it reads as a `String` of digits
to its standard output stream. The program should exit when it detects the
end-of-file (eof) condition on its standard input stream. If the program
detects eof in the middle or reading four bytes, then the program should
print the error message "Premature eof: " followed by the hexadecimal
representation of the one, two, or three bytes it last read, and then
exit with the exit code "-1". Test your program with a few pipelines
like these.
```text
    > java IntServerN 5 | java ReadStreamAsInt
    > java IntServerN 10 | java ReadStreamAsInt
    > java ShortServerN 5 | java ReadStreamAsInt
```
The last pipeline should produce a premature end-of-file error message from
your program. How can you test your program with 10,000 `int` values?



## Doubles

In the `Double` wrapper class, there is a built-in number that tells us how
many bytes there are in a `double` value.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Double.html#field-summary">Double.BYTES</a></li>
</ul>

If you open a JShell session, then you can quickly see what its value is.

```text
    jshell> Double.BYTES
```

There are 8 bytes in a `double` value. That is 64 bits. So there are
potentially `2^64` distinct `double` values. This is an immense number.
The range of `double` values is also immense. You can use JShell to see
the largest `double` value.
```text
    jshell> Double.MAX_VALUE
```
This is a number with over 300 digits (but it is written using "scientific
notation").

We will not say anything about the binary structure of `double` values other
than the fact that they are 8 bytes with 64 bits.

Like the `Integer` class, the `Double` class does not have a method for
converting a `double` value into its eight `byte` values. To do that, we
use the `ByteBuffer` class.

Here is a line of code that creates a `ByteBuffer` object holding the eight
bytes from a `double` value and then converts the `ByteBuffer` into a `byte`
array.

```java
    byte[] bytes = ByteBuffer.allocate(Double.BYTES)
                             .putDouble(3.14159265358979)
                             .array();
```

We can also go in the other direction. Given a `byte` array holding eight
bytes, we can find out what `double` value those bytes represent.

The following code defines an array of eight bytes, wraps a `ByteBuffer`
object around them, and then uses the `getDouble()` method to get the
`double` value that the eight bytes represent.

```java
    byte[] bytes = {64, 9, 33, -5, 84, 68, 45, 17};
    double n = ByteBuffer.wrap(bytes)
                         .getDouble();
```

You can try this in JShell by entering the following line of code at the
JShell prompt.

```java
    jshell> var bytes = java.nio.ByteBuffer.allocate(Double.BYTES).putDouble(3.14159265358979).array()
```

Here is the other direction.

```java
    jshell> var d = java.nio.ByteBuffer.wrap(new byte[]{64, 9, 33, -5, 84, 68, 45, 17}).getDouble()
```

Here is another demonstration of the fact that bytes, by themselves, carry
no information about what they represent or how they should be interpreted.
Create a `ByteBuffer` object wrapped around eight bytes.

```java
    jshell> var byteBuf = java.nio.ByteBuffer.wrap(new byte[]{64, 9, 33, -5, 84, 68, 45, 17})
```

Now ask the `ByteBuffer` object to interpret its bytes two ways, as two
different types of values.

```java
   jshell> double d = byteBuf.getDouble()
   jshell>    int n = byteBuf.getInt()
```

In fact, we can interpret that same `ByteBuffer` three more ways.

```java
    jshell> short i = byteBuf.getShourt()
    jshell> float f = byteBuf.getFloat()
    jshell> long  l = byteBuf.getLong()
```

That's five different values interpreted from the same eight bytes of data.
None of the five values is the "correct", or the "real", value of those bytes.
All five values are equally correct. Unless we have a prior agreement on how
to interpret the data, we should not prefer one interpretation over any other.
The bytes themselves cannot tell us what they mean.

We can make our example a bit more dramatic. The following code derives four
distinct `int` values from eight bytes.

```java
   jshell> var byteBuf = java.nio.ByteBuffer.wrap(new byte[]{64, 9, 33, -5, 84, 68, 45, 17})
   jshell> var n0 = byteBuf.getInt(0)
   jshell> var n1 = byteBuf.getInt(1)
   jshell> var n2 = byteBuf.getInt(2)
   jshell> var n3 = byteBuf.getInt(3)
```

We get the additional `int` values by using offsets into the array of eight
bytes. The values `n0` and `n1` share three bytes. The values `n0` and n2`
share two bytes, etc.

We can make this example even more dramatic. We can get eight distinct `int`
values from eight bytes.

```java
   jshell> var byteBuf = java.nio.ByteBuffer.wrap(new byte[]{64, 9, 33, -5, 84, 68, 45, 17})
   jshell> var n0 = byteBuf.getInt(0)
   jshell> var n1 = byteBuf.getInt(1)
   jshell> var n2 = byteBuf.getInt(2)
   jshell> var n3 = byteBuf.getInt(3)
   jshell> var n4 = byteBuf.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(0)
   jshell> var n5 = byteBuf.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(1)
   jshell> var n6 = byteBuf.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(2)
   jshell> var n7 = byteBuf.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(3)
```

We get the additional `int` values by reversing the order of the bytes (see
the section below on "byte order"). It would seem that eight distinct `int`
values would need 32 bytes of data, bet we got eight `int` values out of
just eight bytes of data.


We can convert our integer client/server programs from the last section so
that they work with `double` values. Below is a client/server pair where
each program takes a command-line argument specifying how many `double`
values that program should use. With these two programs, combined with the
two integer programs from the last section, we can do a lot of interesting
experiments.

```java
import java.nio.ByteBuffer;
import java.util.Scanner;

public class DoubleServerN {
   public static void main(String[] args)  {
      int n = 1; // Default value for n.
      if (args.length >= 1) {
         try {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e) {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      final Scanner in = new Scanner(System.in);
      for (int j = 0; j < n; ++j) {
         final double d = in.nextDouble();
         byte[] bytes = ByteBuffer.allocate(Double.BYTES)
                                  .putDouble(d)
                                  .array();
         System.out.write(bytes[0]);
         System.out.write(bytes[1]);
         System.out.write(bytes[2]);
         System.out.write(bytes[3]);
         System.out.write(bytes[4]);
         System.out.write(bytes[5]);
         System.out.write(bytes[6]);
         System.out.write(bytes[7]);
         System.out.flush(); // Try commenting this out.
         if ( System.out.checkError() )
            throw new RuntimeException(
               "System.out has encountered an IOException");
      }
   }
}
```

```java
import java.nio.ByteBuffer;
import java.io.IOException;

public class DoubleClientN {
   public static void main(String[] args) throws IOException {
      int n = 1; // Default value for n.
      if (args.length >= 1) {
         try {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e) {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      for (int j = 0; j < n; ++j) {
         final int b0 = System.in.read();
         final int b1 = System.in.read();
         final int b2 = System.in.read();
         final int b3 = System.in.read();
         final int b4 = System.in.read();
         final int b5 = System.in.read();
         final int b6 = System.in.read();
         final int b7 = System.in.read();
         final byte[] bytes = {(byte)b0,
                               (byte)b1,
                               (byte)b2,
                               (byte)b3,
                               (byte)b4,
                               (byte)b5,
                               (byte)b6,
                               (byte)b7};
         final double d = ByteBuffer.wrap(bytes)
                                    .getDouble();
         System.out.println(d);
      }
   }
}
```

Now we can compare the hexadecimal (or binary, or decimal) representation
of the eight bytes in several `double` values.

```text
    > java DoubleServerN 5 | java -cp filters.jar HexDump 8
    > java DoubleServerN 5 | java -cp filters.jar BinaryDump 8
    > java DoubleServerN 5 | java -cp filters.jar DecimalDump 8
```

Enter the following command-line and then enter 4 integer numbers.

```text
    > java IntServerN 4 | java DoubleClientN 2
```

"IntServerN" writes 16 bytes into the pipe. "DoubleClientN" reads those
16 bytes as two doubles. There is nothing that tells "DoubleClientN" that
it is reading bytes that are really from an `int` value. Once bytes enter
the pipe, they have no context or meaning attached to them. The program
"DoubleClientN" has no way to know how the bytes it reads were produced
or what they "really" mean. It just interprets those bytes as bytes that
make up a `double` value.

The following command-line is similar.

```text
    > java DoubleServerN 3 | java IntClientN 6
```

The 24 bytes that "DoubleServerN" writes to the pipe are read from
the pipe by "IntClientN" as six `int` values. "IntClientN" has no way
to know that the bytes it is reading as parts of an `int` were written
to the pipe as the bytes of a `double`.



## Character

In the `Character` wrapper class, there is a built-in number that tells us
how many bytes there are in an 'char` value.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#field-summary">Character.BYTES</a></li>
</ul>

If you open a JShell session, then you can quickly see what its value is.

```text
    jshell> Character.BYTES
```

In Java, the `char` data type is not an ASCII character. A `char` is a two
byte (16 bit), Unicode UTF-16BE character. We will say a lot more about
Unicode in the next document. Here, we just want to emphasize that Java,
unlike C and C++, uses two bytes to represent every character.

Since there are two bytes (16 bits) in a `char` value, there are potentially
`2^16 = 65,536` distinct `char` values. The `char` type is an unsigned integer
type. That means its range of values is from `0` to `65535`.

```text
    jshell> char c = 65535
    jshell> char c = 65536
```

We cannot assign a negative value to a `char` variable.

```text
    jshell> char c = -1
```

Java has five integer types, `byte`, `char`, `short`, `int`, and `long`.
Only the `char` type is unsigned.

Since `char` is an integer type, we can do arithmetic on `char` values,
but after doing an arithmetic operation, the result needs to be cast
back to a `char`.

```text
    jshell> char c = 'c'
    jshell> c = (char)(c - 2)
    jshell> c = (char)(c + 19)
```

Like the `Integer` class, the `Character` class does not have a method for
converting a `char` value into its two `byte` values. To do that, we use
the `ByteBuffer` class.

Here is an example of a line of code that creates a `ByteBuffer` object
holding the two bytes from a `char` value and then converts the `ByteBuffer`
into a byte array.

```java
    byte[] bytes = ByteBuffer.allocate(Character.BYTES)
                             .putChar('€')
                             .array();
```

We can also go in the other direction. Given a `byte` array holding two
bytes, we can find out what `char` value those bytes represent.

The following code defines an array of two bytes, wraps a `ByteBuffer`
object around them, and then uses the `getChar()` method to get the
`char` value that the two bytes represent.

```java
    byte[] bytes = {0x20, (byte)0xAC};
    char c = ByteBuffer.wrap(bytes)
                         .getChar();
```

You can try this in JShell by entering the following line of code at
the JShell prompt.

```java
    jshell> var bytes = java.nio.ByteBuffer.allocate(Character.BYTES).putChar('€').array()
```

Here is the other direction.

```java
    jshell> var c = java.nio.ByteBuffer.wrap(new byte[]{0x20, (byte)0xAC}).getChar()
```

Since a `char` is stored as two bytes, the order of the bytes is important.
Java stores `char` values in memory using big endian order (as Java does
for all its multi-byte data types).

The exclamation mark, '!' is the first printable `char` character. Its
integer value is 33, so its higher order `byte` (its "big" end) is `0`.
The following line of code shows that the `0` byte comes first in the
`byte` array.

```java
    jshell> var bytes = java.nio.ByteBuffer.allocate(Character.BYTES).putChar('!').array()
```

The `char` values below 32 are "non-printable" (sometimes referred to as
"control characters"). Let's ask JShell to print a non-printable character.

```java
    jshell> (char)31
    jshell> (char)30
```

JShell responds with the values '\037' and '\036'. How does 31 become 37? The
explanation is that Java prints non-printable `char` values using their octal
representation (not decimal, not hexadecimal, but octal!). And `3 * 8 + 7 == 31`.

Like all integer types in Java, `char` literals can be written using any of
the hexadecimal, decimal, octal, or binary number systems. If the character
is printable, then JShell will respond with the character. If the character
is non-printable, then JShell will respond with the character's octal value.

```java
    jshell> char c1 = 51          // decimal
    jshell> char c2 = 051         // octal
    jshell> char c3 = 0x51        // hexadecimal
    jshell> char c4 = 0b01010001  // binary

    jshell> char c5 = 11          // decimal
    jshell> char c6 = 013         // octal
    jshell> char c7 = 0x0B        // hexadecimal
    jshell> char c8 = 0b00001011  // binary
```

* <https://www.ascii-code.com/articles/ASCII-Control-Characters>



## String

In Java, a `String` is not an array of `char`, as in C or C++. In Java, a
`String` is an "opaque type", meaning that we do not know how Java actually
stores a `String` value. Java may use an "array of char" to store a `String`
but at times it may not (it may use an "array of bytes"). Strings are so
important to Java programs that the JVM has many tricks and optimizations
for storing and handling `String` values.

The `String` class has a method `getBytes()` for getting a `byte` array from
a `String` value (not a `char` array). The `ByteBuffer` class does not work
with `String` values.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#getBytes()">java.lang.String.getBytes()</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#getBytes(java.nio.charset.Charset)">java.lang.String.getBytes(Charset)</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#getBytes(java.lang.String)">java.lang.String.getBytes(String charsetName)</a></li>
</ul>

The `byte` values in the array returned by `getBytes()` are not determined
just by the `String` itself. They are also determined by a "character set".

```java
    jshell> "hello".getBytes()
    jshell> "hello".getBytes("UTF-16")
    jshell> "hello".getBytes("UTF-16BE")
    jshell> "hello".getBytes("UTF-16LE")
    jshell> "hello".getBytes("UTF-32")
    jshell> "hello".getBytes("UTF-32LE")
```

The bytes returned by the `getBytes()` method are *not* the bytes that are
stored in memory by the JVM to represent the `String` value. These bytes
are an "encoding" of the characters from the string. As we will see later,
these are the bytes that we will need to send over a stream to transmit
the `String` value to another process. But in that case, we have to be
careful about how we handle the character set. In the next document we
will explain character sets and character encodings.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/charset/Charset.html">java.nio.Charset</a></li>
</ul>

We can go in the other direction and construct a `String` value from a `byte`
array.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#%3Cinit%3E(byte%5B%5D)">java.lang.String(byte[])</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#%3Cinit%3E(byte%5B%5D,java.nio.charset.Charset)">java.lang.String(byte[], Charset)</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#%3Cinit%3E(byte%5B%5D,java.lang.String)">java.lang.String(byte[], String charsetName)</a></li>
</ul>

Much like the `getBytes()` method, the `String` value constructed by one of these constructors
is not determined just by the `byte` array. The `String` value is also determined by a "character set".

```java
    jshell> new String(new byte[]{100, 111, 103}) // Use the default Charset.
    jshell> new String(new byte[]{-2, -1, 0, 100, 0, 111, 0, 103}, "UTF-16")
    jshell> new String(new byte[]{0, 100, 0, 111, 0, 103},         "UTF-16BE")
    jshell> new String(new byte[]{100, 0, 111, 0, 103, 0},         "UTF-16LE")
    jshell> new String(new byte[]{0, 0, 0, 100, 0, 0, 0, 111, 0, 0, 0, 103}, "UTF-32")
    jshell> new String(new byte[]{100, 0, 0, 0, 111, 0, 0, 0, 103, 0, 0, 0}, "UTF-32LE")
```

Each of the above constructor calls returns the string "dog".

These constructors can be used to build a `String` from a stream of bytes
sent by one process to another process. But the receiving process must
know what `Charset` the sending process used when it wrote the bytes.

Notice how `String` values are more complicated than Java's primitive values.
If one Java process streams a primitive value to another Java process, as
long as both processes agree on what data type is being streamed (for example,
`int` or `double`), then the receiving process has no ambiguity in decoding
the bytes. But when two Java processes stream the bytes of a `String` value,
not only do they need to agree on the data type, `String`, they also need to
agree on a `Charset`.



## Byte order (endianness)

We know that an `int` value contains four bytes of data. In this section we
want to discuss a small, but important, detail that comes up when we write
an `int` value to a stream or store an `int` value in a `byte` array.

Let's use the notation `b3`, `b2`, `b1`, and `b0` to represent the four
bytes of an `int` value, where `b3` is the MSB and `b0` is the LSB. We
can picture this `int` value as a row of four bytes.

```text
    +--------+--------+--------+--------+
    |   b3   |   b2   |   b1   |   b0   |
    +--------+--------+--------+--------+
       MSB                        LSB
```

What if we want to change our visualization of these four bytes from a "row
of bytes" to a "stack of bytes". We can do this two ways, with the MSB on
top, or the LSB on top.


```text
    +--------+                  +--------+
    |   b3   |  MSB             |   b0   |  LSB
    +--------+                  +--------+
    |   b2   |                  |   b1   |
    +--------+                  +--------+
    |   b1   |                  |   b2   |
    +--------+                  +--------+
    |   b0   | LSB              |   b3   | MSB
    +--------+                  +--------+
```

We have names for each of these two ways of "stacking" bytes. When the MSB
of the `int` is on top, we call that the **big endian** (BE) byte ordering
of the `int`. When the LSB is on top, we call that the **little-endian**
(LE) byte ordering of the `int`.


In a computer, the "row of bytes" represents an `int` value when it is being
held in a CPU register. The "stack of bytes" represents an `int` value when
it is stored in computer memory or when the `int` is converted to an array
of `byte` (for example, by using the `ByteBuffer` class). In the case of an
array of `byte`, the "top" is the array entry with index `0`. In the case of
computer memory, the "top" is the memory location with the lowest address of
the four bytes.

Remember that in a decimal number like `10,005`, `1` is the most significant
digit, so the `1` can be called the "big end" of the number. The `5` is the
least significant digit, so `5` can be called the "little end" of the number.
That's how we get the names "big-endian" and "little-endian". The MSB of an
`int` is its "big end" and the LSB of an `int` is its "little end". The
big-endian byte ordering has the MSB first (on top). The little-endian
byte ordering has the LSB first (on top).

Here is another picture of this idea.

* <https://en.wikipedia.org/wiki/Endianness#/media/File:32bit-Endianess.svg>

Here is the `ByteBuffer` code that computes the four `byte` values for the
decimal number `2,000,000,007`.

```java
    jshell> var bytes = java.nio.ByteBuffer.
                           allocate(Integer.BYTES).
                           putInt(2_000_000_007).
                           array()
```
The return value is this array.
```java
    byte[4]{ 119, 53, -108, 7 }
```
The `7` is the "little end" of the decimal number and therefore it is also
the "little end" of the `byte` array. So the `byte` values are in the array
in big-endian order.

We can verify this byte order by using the `order(ByteOrder)` method in the
`ByteBuffer` class. This method takes a constant of type `java.nio.ByteOrder`,
which must be either the constant `ByteOrder.BIG_ENDIAN` or the constant
`ByteOrder.LITTLE_ENDIAN`. The `order()` method orders its `ByteBuffer`
object to be in the specified byte order.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/ByteBuffer.html#order(java.nio.ByteOrder)">java.nio.ByteBuffer.order(java.nio.ByteOrder)</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/ByteOrder.html">java.nio.ByteOrder</a></li>
</ul>

Here is how we instruct `ByteBuffer` to use a specific byte order
(its default is to always use big-endian byte order).

```java
    jshell> var bytesBE = java.nio.ByteBuffer.
                             allocate(Integer.BYTES).
                             order(java.nio.ByteOrder.BIG_ENDIAN).
                             putInt(2_000_000_007).
                             array()
    jshell> var bytesLE = java.nio.ByteBuffer.
                             allocate(Integer.BYTES).
                             order(java.nio.ByteOrder.LITTLE_ENDIAN).
                             putInt(2_000_000_007).
                             array()
```
The results are
```java
    bytesBE ==> byte[4] { 119, 53, -108, 7 }
    bytesLE ==> byte[4] { 7, -108, 53, 119 }
```

The Java Virtual Machine uses big-endian byte order for all of its multi-byte
data types. So `double`, `long`, `float`, `int`, `short`, and `char` are all
stored in the JVM's memory using big-endian byte ordering.

Here is an example using a `char` value.

```java
    jshell> var bytesBE = java.nio.ByteBuffer.
                             allocate(Character.BYTES).
                             order(java.nio.ByteOrder.BIG_ENDIAN).
                             putChar('z').
                             array()
    jshell> var bytesLE = java.nio.ByteBuffer.
                             allocate(Character.BYTES).
                             order(java.nio.ByteOrder.LITTLE_ENDIAN).
                             putChar('z').
                             array()
```
The results are,
```java
    bytesBE ==> byte[2] { 0, 122 }
    bytesLE ==> byte[2] { 122, 0 }
```
The character 'z' is stored as the two byte value `0x007A` (in hexadecimal).
Its "big end" is the byte `0x00`. Its "little end",`0x7A` (or `122` in
decimal), is the ASCII code for 'z'.

This is a good place for us to look at the code that could be used by the
`ByteBuffer` class to compute the four bytes in an `int` value or to compute
the `int` value from an array of four `byte` values.

Here is a code segment that takes an array of four bytes in big-endian order
and computes the `int` value that they represent.

```java
    byte[] bytesBE = {8, 4, 2, 1};
    int value = bytesBE[0] << 24 |
                bytesBE[1] << 16 |
                bytesBE[2] <<  8 |
                bytesBE[3];
```

Here is how we compute the `int` value from an array of four bytes in
little-endian order.

```java
    byte[] bytesLE = {1, 2, 4, 8};
    int value = bytesLE[3] << 24 |
                bytesLE[2] << 16 |
                bytesLE[1] <<  8 |
                bytesLE[0];
```

Here is how we compute the big-endian array of bytes from an `int` value.

```java
    int value = 134_480_385;
    byte[] bytesBE = new byte[4];
    bytesBE[0] = (byte)(value >> 24);
    bytesBE[1] = (byte)(value >> 16);
    bytesBE[2] = (byte)(value >>  8);
    bytesBE[3] = (byte)(value);
```

Here is how we compute the little-endian array of bytes from an `int` value.

```java
    int value = 134_480_385;
    byte[] bytesLE = new byte[4];
    bytesLE[3] = (byte)(value >> 24);
    bytesLE[2] = (byte)(value >> 16);
    bytesLE[1] = (byte)(value >>  8);
    bytesLE[0] = (byte)(value);
```

These conversions are not a lot of code. But it is a good idea to have this
code encapsulated in the `ByteBuffer` class. If we write the two lines of
code shown below instead of the blocks of code shown above, then it is
easier to understand, and much less likely to get goofed up. The fluent
API used by `ByteBuffer` has a lot to do with this code's clarity.

```java
    byte[] bytesBE = ByteBuffer.
                        allocate(Integer.BYTES).
                        putInt(134_480_385).
                        array();
       int   value = ByteBuffer.
                        wrap(new byte[]{8, 4, 2, 1}).
                        getInt();
```


**Exercise:** Write the blocks of code that convert a `double` value to and
from an array of eight `byte` values in both big and little endian order.
(Hint: You cannot apply the bitwise shift operators to a `double`. Look in
the `Double` class for methods that convert a `double` value to a `long`
and back.)


<ul>
<li><a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a></li>
</ul>



<!--
<ul>
<li><a href=""></a></li>
</ul>
-->
