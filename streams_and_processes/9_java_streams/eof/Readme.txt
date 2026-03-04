
      End-of-file (eof) and End of Stream

What does it mean to say that a stream of bytes
has reach the "end-of-file"? How can we tell if
a given byte stream does not have more data and
has reached its "end-of-file"?

This is not an easy question to answer. The answer
depends on things like what is the source of the
byte stream, what type of data do we think is in
the byte stream, and what Java methods are we
using to read the byte stream.

The most important thing to know about "end-of-file"
is that it is NOT a special symbol stored at the end
of a file. No file ever stores a symbol denoting the
end of the file. Instead, all file systems will have
meta-data for each file and part of a file's meta-data
will be how many bytes are stored in the file. When
a program has read that many bytes from a file, the
operating system will inform the program that it has
reached the "end of the file". How the operating system
informs a program that it has reached the end of a file
depends on the operating system, the programming language
used to write the program, and even the function used in
that language (Java has at least four different ways of
letting a Java program know that it has reached the end
of a file!).

While files never store an "end of file" symbol, there is a
special "end of file" symbol that is only used for keyboard
input to a process. If you are using a keyboard to enter data
into the standard input stream of a process, then you let
the operating system know that you do not have any more data
by typing the key combination Control-z on Windows, or the
key combination Control-d on Linux. When you use that key
combination, the operating system lets the process know, in an
appropriate way, that the process has reached the end-of-file
for its standard input stream. (NOTE: The Control-z or
Control-d key combinations DO NOT mean the same thing as
the Control-c key combination; Control-c means "kill the
process" while the other two mean "end-of-file".)


The programs EOFv1.java, EOFv2.java, EOFv3.java and
EOFv4.java demonstrate four different ways in which
Java lets a program know that it has reached the end
of a byte stream. It is not clear why Java has so
many ways to signal end-of-file.

You can run each program on the command-line and
have it read the data file in the current directory,
   > java EOFv1  <  data
or you can run each program with keyboard input,
   > java EOFv1
and type data until you enter Control-z (or Control-d).


https://en.wikipedia.org/wiki/End-of-file
