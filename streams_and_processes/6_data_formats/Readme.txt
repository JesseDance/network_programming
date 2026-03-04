
      Byte Streams

A byte stream is an ordered sequence of bytes. The
bytes in a stream have no inherent meaning. There is
no such thing as a "video stream", or an "audio stream",
or a "text stream". Every byte stream is just that, a
stream of (raw) binary bytes. Any program that reads a
stream of bytes is free to interpret those byte any way
it wants. If a program thinks it is reading a "video
stream", as long as the data in the stream makes sense,
then the stream really is a "video stream" from the point
of view of that program.

The programs in this folder demonstrate how a stream of bytes
can be interpreted by a program in any way it wishes.

The program CreateData.java write 16 bytes to its standard
output. We can redirect those bytes to a file. That data
file is then a stream (of 16 bytes) that can be read by
other programs.
   > java  CreateData  >  data

Each of the example programs in this folder reads a stream
of bytes with a different interpretation. You can use the
data file as input to any one of them,

   > java ReadDataAs____  <  data

or you can directly pipe the output from CreateData.java
directly into the input of one of the readers,

   > java CreateData | java ReadDataAs____

You can also run any of the reader programs using keyboard
input,

   > java ReadDataAs____

and then enter 16 data bytes using the keyboard. You would
think that the keyboard is sending "text data" to the program,
but it is not. The keyboard is just sending binary bytes to the
program, one byte per keyboard character. You need to look at an
ASCII table to see what binary byte each key actually sends as
data to the program.

If you know the binary representation of a double value, like
-42.42 for example, then you can type the correct sequence of
keyboard characters to send that double value to the program
ReadDataAsDouble.java (-42.42 has hex value 0xC04535C28F5C28F6).
