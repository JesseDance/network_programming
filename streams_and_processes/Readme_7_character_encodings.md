
Character encodings
===================

The topic of characters and fonts is very complex. It started out easy,
when computers used ASCII characters displayed in a terminal window. Now
we have graphical displays that can draw any character from any alphabet
in the world. But we don't sit at keyboards with thousands of keys. That
just begins to hint at the complexity of letting documents use any
character from any alphabet.

This document is a short introduction to characters, character sets,
character encodings, glyphs, fonts, and typefaces.

All the example code mentioned this document is in the sub folder called
"character_sets" from the following zip file.

* <http://cs.pnw.edu/~rlkraft/cs33600/for-class/streams_and_processes.zip>



## Code pages

We mentioned in the last document that ASCII uses 7 bits to encode 128
characters. This covers the characters on a standard keyboard. Since a
byte has one more bit in it, Extended ASCII uses that bit to encode 128
more characters (which are not on a standard keyboard). Unlike ASCII,
which is an international standard, Extended ASCII was never standardized.
There are hundreds of ways to choose 128 characters and create an Extended
ASCII character set.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Extended_ASCII">Extended ASCII</a></li>
</ul>

A choice of 128 characters, along with a choice of which number between 128
and 255 will represent each of those characters, is called a **code page**.
Every code page uses ASCII for the character numbers from 0 to 127.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Code_page">Code page</a></li>
</ul>

Code pages are not important to most GUI programs, though some text editors
use them. Code pages are important when you open a console window. A console
window will display text data using a particular code page. The same text
data may appear different in different console windows if the consoles are
using different code pages. As we have seen many times, byte values are
always ambiguous. There must always be an agreement about how to interpret
bytes. A code page is an agreement about how to display character bytes.

Do the following experiments in a console window opened to the "character_sets"
folder.

Windows has a command-line program, "chcp", that tells you what code page
the console is currently using and it also lets you change the current code
page.

```text
    character_sets> chcp
    character_sets> chcp /?
```

In the "character_sets" folder there is a data file called "CharacterData_Ex_ASCII.txt"
that contains byte values in the Extended ASCII range, from 128 to 254. This
file *does not* contain "characters", it contains "byte values". The contents
of this file will look different if you look at it using different code pages
because each code page will interpret the byte values differently. Different
code pages will interpret the same byte value as different characters.

In the "character_sets" folder there is a script file called
"CharacterData_Ex_ASCII_codepages.cmd" that displays the data file
"CharacterData_Ex_ASCII.txt" using a variety of code pages. If you run
the script file (double-click on it) you can see how much the code page's
interpretation of the same byte values can change the appearance of the
data. The data (the byte values) do not change. Their interpretation,
and their appearance, changes.

The code in the script file looks like the following. You should try opening
a command-prompt window in the "character_sets" folder and typing these
command-lines directly. 

```text
    character_sets> chcp 437
    character_sets> type CharacterData_Ex_ASCII.txt
    character_sets> chcp 1252
    character_sets> type CharacterData_Ex_ASCII.txt
    character_sets> chcp 864
    character_sets> type CharacterData_Ex_ASCII.txt
    character_sets> chcp 932
    character_sets> type CharacterData_Ex_ASCII.txt
    character_sets> chcp 1256
    character_sets> type CharacterData_Ex_ASCII.txt
```

The default code page for the cmd terminal is usually code page 437.
Sometimes it is code page 1252. It should be code page 65001, the UTF-8
"code page" (which is not really a code page).

The data file "CharacterData_Ex_ASCII.txt" was created by compiling and
running the Java program "CreateCharacterData_Ex_ASCII.java". It is a
very simple program.

```java
public class CreateCharacterData_Ex_ASCII {
   public static void main(String[] args) {
      for (int i = 0x80; i < 0xFF; ++i) {
         System.out.write(i); // Write one byte.
      }
      System.out.flush();
   }
}
```

You should compare this program to the program "CreateData.java" from
the folder "data_formats". They are almost the same program

```java
public class CreateData {
   public static void main(String[] args) {
      for (int i = 0x78; i <= 0x78 + 15; ++i) {
         System.out.write(i); // Write one byte.
      }
      System.out.flush();
   }
}
```

Both programs write one byte of data at a time in a loop. Neither
program gives any meaning to those bytes of data. In the case of the
`CreateCharacterData_Ex_ASCII` class, we interpreted the data as text
using several code pages. In the case of the `CreateData` class, we
interpreted the data as different primitive data types. In both cases,
the data itself carried *no* meaning or interpretation. Binary data
siting in a file is always just a stream of bytes and we always need
additional information to tell us how to interpret the bytes.

<ul>
<li><a href="https://en.wikipedia.org/wiki/Windows_code_page">Windows code page</a></li>
<li><a href="https://en.wikipedia.org/wiki/Code_page_437">Code page 437</a></li>
<li><a href="https://en.wikipedia.org/wiki/Windows-1252">Code page 1252</a></li>
<li><a href="https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/chcp">chcp - Change code page</a></li>
<li><a href="https://ss64.com/nt/chcp.html">chcp</a></li>
</ul>

We have just seen how the choice of a code page can change the appearance
of the contents of a file when the file is displayed in a console window.
What about displaying a file in a text editor? The same issues will still
apply. A text editor must have some agreement on how to interpret the byte
values in whatever file it opens and displays.

We know that the file "CharacterData_Ex_ASCII.txt" contains byte values in
the extended ASCII range from 128 to 254. We know that these byte values do
not have a fixed interpretation or appearance. We should be able to open this
file in a text editor and see it displayed using any code page we choose,
just as we chose different code pages in the console window. But not all
text editors make selecting a code page as easy as using the console window's
`chcp` command.

The Windows Notepad text editor can only use code page 1252 or UTF-8.
We cannot force it to open a file using the older Windows Cp437 code
page.

The open source Notepad++ text editor allows you to open a text file and
use their "Encoding" menu to view the file's contents using almost any
code page.

The VS Code editor can also view an open file using any code page. Open
the "Command Palette" (Ctrl+Shift+P) and enter into its text box "Change
File Encoding" and tap the `Enter` key. Select the item "Reopen with
Encoding". A drop down list should appear of all the available code pages
and encodings.

Remember, opening a file using different encodings (code pages) does not
change the contents of the file. Only its appearance in the editor window
changes. The byte values in the file should not change.

So far we have seen that the choice of a code page (an encoding) can change
the appearance and interpretation of a text file's contents. When the console
window needs to open a file, it needs a way interpret the bytes in the file.
When a text editor opens a file, it needs a way to interpret the contents of
the file. These ideas are not restricted to just the console window and text
editors. *All* programs, when they open a text file, need a way to interpret
the bytes in the file. This applies to compilers, like `javac`.

A Java source file, a file with the extension ".java" is usually thought
of as a "text file". But there is no such thing as a "text file". *Every*
file is just a sequence of bytes stored in a file system. As the `javac`
compiler reads bytes from a source file, it needs to follow some agreement
on how to interpret those bytes as a sequence of text characters. Like a
text editor or a console window, the Java compiler uses a code page as its
agreement on how to interpret a sequence of bytes as a sequence of characters.

The Java compiler has a built in default code page that it uses if no other
code page is specified. When we use the `javac` command, we can give the
compiler a `-encoding` command-line argument that tells the compiler what
code page it should use.

Yhe Java compiler's default code page depends on both the version of Java
and the operating system. Starting with Java 18, the Java compiler uses
UTF-8 as its default agreement for translating byte sequences into character
sequences. Before Java 18, the Java compiler on Windows used code page Cp1252
as its default.

Let's look at some examples. In the "character_sets" folder there are two Java
source files called "BoxDrawingChars_Cp437.java" and "BoxDrawingChars_UTF_8.java".
Each file is encoded using a different character set. The file
"BoxDrawingChars_Cp437.java" uses Extended ASCII and the Cp437 code page.
The file "BoxDrawingChars_UTF_8.java" uses Unicode and the UTF-8 encoding.
Other than the charcter encoding, the files are equivalent. In fact, they
compile to the exact same `.class` file. If you open these two files in a
text editor, make sure that you instruct the text editor to use the correct
character encoding for each file.

Since the Java compiler has only one default charcter encoding, and since
these files use two differeent character encodings, at least one of them
will not be using the compiler's default encoding (and maybe both of them).
If you compile a file and the compiler is using the wrong encoding, one of
two things will happen. The file fails to compile, and the compiler gives
you an encoding error, or the file compiles, but not to the program that
you wanted.

First, let us compile eachh program using the correct encoding.

```text
    character_sets> javac -encoding Cp437  BoxDrawingChars_Cp437.java
    character_sets> javac -encoding utf-8  BoxDrawingChars_UTF_8.java
```

When we run the programs, they produce the same output (the two programs
compile to the exact same `.class` file).

```text
    character_sets> java  BoxDrawingChars_Cp437
    character_sets> java  BoxDrawingChars_UTF_8
```

If we compile a program while using the wrong encoding, we can get encoding
errors from the compiler.

```text
    character_sets> javac -encoding utf-8  BoxDrawingChars_Cp437.java
    character_sets> javac -encoding Cp1252 BoxDrawingChars_UTF_8.java
```
 
 If we compile a program while using the wrong encoding, we can get an
 incorrect version of our program.

```text
    character_sets> javac -encoding Cp437  BoxDrawingChars_UTF_8.java
    character_sets> java BoxDrawingChars_UTF_8
```

```text
    character_sets> javac -encoding Cp1252  BoxDrawingChars_Cp437.java
    character_sets> java BoxDrawingChars_Cp437
```

We have just seen that when we compile a Java source file we need to make
sure that the compiler is using the correct character encoding (code page).
When we run a Java program it turns out that we also need to tell the Java
Virtual Machine (JVM) what character encoding (code page) it should use
when doing text based input or output.

Like the Java compiler, the JVM has built in default code pages for doing
text based input and output. And like the compiler, the default depends on
both the version of Java and the operating system.


```text
javac -encoding utf-8  BoxDrawingChars_UTF_8.java
chcp 1252
java  BoxDrawingChars_UTF_8
java -Dfile.encoding=utf-8   BoxDrawingChars_UTF_8
java -Dstdout.encoding=utf-8 BoxDrawingChars_UTF_8
java -Dstdout.encoding=Cp437 BoxDrawingChars_UTF_8
chcp 437
java -Dstdout.encoding=utf-8 BoxDrawingChars_UTF_8
java  BoxDrawingChars_UTF_8
chcp 65001
java -Dstdout.encoding=Cp437 BoxDrawingChars_UTF_8
```

We tell the Java compiler to use a specific code page by using the compiler's `-encoding` command-line option.

We tell the JVM to use a specific default code page by using the JVM's `-Dfile.encoding=` command-line option.



## Character set

A **character set** is a choice of characters. A character set is usually
some alphabet (like the Latin, Greek, or Cyrillic alphabets) combined
with useful symbols (like punctuation or arithmetic symbols).

<ul>
<li><a href="https://unicode.org/glossary/#character_set">Character set</a> (definition)</li>
<li><a href="https://developer.mozilla.org/en-US/docs/Glossary/Character_set">Character set</a> (definition)</li>
</ul>

Each code page is a character set. Many code pages were created by countries
to contain their native alphabet along with their most important symbols.

<ul>
<li><a href="https://learn.microsoft.com/en-us/windows/win32/intl/code-page-identifiers">List of code pages</a></li>
</ul>

The Java language has a notion of a "charset". Most people read charset as
"character set", but a Java charset is not exactly a character set. It is
more like a "character encoding".

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/charset/Charset.html">java.nio.charset.Charset</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/charset/package-summary.html">java.nio.charset</a> - Package Summary</li>
</ul>



## Coded character set

If we take the characters in a character set and put them in a specific
order, so we have a character that comes first, and a character that comes
second, and a character that comes last, then that ordering makes the
character set into a **coded character set**.

This terminology can be confusing, because a "coded character set" is not
a "character encoding". A "character encoding" means that we have assigned
a binary value to represent each character in a character set. A "coded
character set" means that we have put the characters in an ordering.

When we have a coded character set, we have assigned a number to each
character (but *not* a binary representation). The number assigned to a
character is called its **code point** (in that coded character set).

If we go back to extended ASCII, every code page is simultaneously a
character set and a coded character set. If we look at the code page's
table, the table shows us the character set. The table puts the characters
in an ordering, from the table's upper left-hand corner to the table's lower
right-hand corner. The code point for each character is determined by its
position in the table, starting with `0` at the upper left-hand corner.

<ul>
<li><a href="https://unicode.org/glossary/#coded_character_set">Coded character set</a> (definition)</li>
<li><a href="https://developer.mozilla.org/en-US/docs/Glossary/Code_point">Code point</a> (definition)</li>
<li><a href="https://en.wikipedia.org/wiki/Code_point">Code point</a></li>
</ul>


## Character encoding

Once we have chosen the characters that will be in a character set and
assigned a code point to each character, we then need to choose a binary
encoding for each of those characters (code points). Exactly what binary
value do we want each code point to be represented by?

Choosing a binary representation for each character in a character set
is called a **character encoding**.

If we go back to extended ASCII, every code page is simultaneously a
character set, a coded character set, and a character encoding. The code
page's table shows us the character set and puts them in an ordering. The
code point for each character is its position in the table, starting with
`0` at the upper left-hand corner. Each character's binary encoding is
the 8-bit binary number for its code point.

In a large character set, if a character is assigned a code point, say 28,745,
then we could give that character the binary encoding that is the binary
encoding of its code point number, 28,745. But we will see that such a
straight forward binary encoding of code points is usually not the best
way to assign encodings.

Code pages are such simple character sets that we tend to blur the
distinction between coded character set and character encoding. When we
work with a complex character set like Unicode, these distinctions become
important. As we will see, Unicode is an ordered character set with many
different character encodings.

<ul>
<li><a href="https://developer.mozilla.org/en-US/docs/Glossary/Character_encoding">Character encoding</a> (definition)</li>
<li><a href="https://en.wikipedia.org/wiki/Character_encoding">Character encoding</a></li>
<li><a href="https://en.wikipedia.org/wiki/Mojibake">Mojibake</a></li>
</ul>



## Code unit

When we set out to define a binary encoding for all the code points in an
encoded character set, the first decision we must make is what will be the
size of the binary words that our encoding uses, that is, what will be our
**code unit**. For example we could use 8-bit code units (bytes), or 16-bit
code units, or 24-bit code units, etc. If we choose 16-bit units, then every
code point will be encoded as a sequence of 16-bit words. If we choose 8-bit
code units, then every code point will be encoded as a sequence of bytes.

For example, ASCII is a 7-bit code but the code unit is an 8-bit byte (the
most significant bit is always `0`). Every code point is encoded in a single
code unit.

Extended ASCII is a 8-bit code and we use an 8-bit code unit and every code
point is encoded in a single code unit.

Unicode is an encoded character set with several encodings that use different
size code units. Some encodings use 8-bit code units, some 16-bit code units,
and some use 32-bit code units.

If our code unit is made up of multiple bytes, then we must specify a byte
order for the bytes in a code unit.

It is important to realize that code point and code unit are not the same
thing. If an encoding uses 8-bit code units, that does not mean that the
coded character set must be at most 256 characters. An encoding can use
8-bit code units, and use two (or more) code units per encoded character.
So there can be far more that 256 character in the coded character set.

Let's do a simple example. Consider the following coded character set and
an encoding of the characters using one or two 3-bit code units. Even though
each code unit is only three bits, there are 20 characters in the character
set.

```text
+  000
-  001
(  010
)  011
0  100 000
1  100 001
2  100 010
3  100 011
4  101 000
5  101 001
6  101 010
7  101 011
8  110 000
9  110 001
u  110 010
v  110 011
w  111 000
x  111 010
y  111 010
z  111 011
```

This is called a **variable length encoding** because some characters are
encoded using one code unit and some characters are encoded using two code
units.

Since this character set has 20 characters, we could use a single 5-bit code
unit to encode every character. But the variable length encoding has the
potential to use fewer bits to encode strings than a 5-bit, fixed length,
encoding. If the characters '+', '-', '(', and ')' are more common in our
strings than the other characters, then the fact that those characters need
only 3 bits each might make the overall encoding of a string shorter than
if we used 5 bits for every character.

Here is a grammar for a small language that uses this character set.

```
   expr ::= expr [ '+' | '-' ] expr
          | '(' expr ')'
          | '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
          | 'u', 'v', 'w', 'x', 'y', 'z'
```

This is a language of arithmetic expressions using single digit numbers and
single letter variable names. Strings in this language look like the following.

```text
1+2
x-4+z
(1+2)-(x-8)+(3+4-z)
```

In the first string, both the 3-bit code unit encoding and a 5-bit code
unit encoding need 15 bits. In the second string, the 3-bit code unit
encoding needs 24 bits, but a 5-bit code unit encoding needs 25 bits. In
the third string, the 3-bit code unit encoding needs (3 * 12) + (6 * 7) = 78
bits, but a 5-bit encoding needs 5 * 19 = 95 bits. For a typical string in
this language, the 3-bit, variable length code unit encoding will need
fewer bits than a 5-bit fixed length code unit encoding.


**Exercise:** Which strings in the above language need fewer bits in a
5-bit code unit encoding?


This encoding has another property that is important when using a variable
length encoding. It is a **self-synchronizing code**. That means we can
point to any code unit in a stream of code units and determine what we are
looking at without having to go back to the beginning of the stream and
decode the entire stream.

For example, suppose we are given a code unit that has `1` as its most
significant bit. That code unit must be the first code unit in a two code
unit character. When we are given the next code unit, we can decode the
character the two code units encode.

On the other hand, if we are given a code unit with `0` as its most significant
bit, then it is either a one code unit character or the second code unit is a
two code unit character. If the given code unit is the first code unit in the
stream, then it must be a single code unit character. If its not the first code
unit and if we can see the previous code unit, and its MSB is `0`, then our
code unit is a single code unit character. If we can see that the previous code
unit's MSB is `1`, then we can decode the two code unit character. If we cannot
see the previous code unit (maybe it has already been discarded), then we must
discard the given code unit as un-decodable, but we can start decoding correctly
on the next code unit.

The above encoding has a property that is *not* desirable in an encoding. If we
want to search a stream of code units for the character `(`, then we can look for
the code unit `010`. But searching for that code unit will also find the trailing
code unit for the characters '2', '6', 'u', etc. The code unit `010` "overlaps"
several code points. This makes searching for characters inefficient. Well
designed character encodings should be "non-overlapping".


Not all variable-length codes are self-synchronizing. Here is a simple example.
Consider this coded character set with an encoding that uses one or two 3-bit
code units.

```text
a  000
b  001
c  010
d  011
e  100
f  101
g  110
h  111 000
i  111 001
j  111 010
k  111 011
l  111 100
m  111 101
n  111 110
o  111 111
```

This encoding is not self-synchronizing. In the following two messages,
the code unit `000`, with a bar over it, cannot be decoded without looking
all the way to the beginning of the message. In the first message, that code
unit is the character 'a'. In the second message, that code unit is part of
the character 'h'.

```text
                    ___
000 111 111 111 111 000 111 111 ==> "aooao" (5 chars)
111 111 111 111 111 000 111 111 ==> "ooho"  (4 chars)
```

When we get to a more complex character set like Unicode, then understanding
the differences between "code point", "code unit", and "character encoding"
becomes crucial to understanding the structure of the character encoding.


<ul>
<li><a href="https://www.unicode.org/versions/Unicode17.0.0/core-spec/chapter-2/#G13708">Unicode Encoding Forms</a></li>
<li><a href="https://en.wikipedia.org/wiki/Variable-length_encoding">Variable-length encoding</a></li>
<li><a href="https://en.wikipedia.org/wiki/Self-synchronizing_code">Self-synchronizing code</a></li>
</ul>



## Unicode

Unicode is a character set. It is supposed to be the set of all characters
and symbols used by any, and every, language on Earth. Unicode presently
has 159,801 characters in its character set.

Unicode is a coded character set. The 159,801 characters in Unicode are
in a specific order. The order a character has in this ordering is called
the character's code point.

Unicode is also a character encoding, but with several different encodings.
The two most common encodings of Unicode are UTF-8 and UTF-16. There is also
UTF-32 which is used in certain special situations. Java, JavaScript, and C#
all define their `char` data type in terms of UTF-16. On the other hand, the
Internet uses UTF-8, and most newer programming languages are based on the
UTF-8 character encoding.

Let's look at Unicode code points first, and then we will look at Unicode
encodings.

Unicode organizes its code points into 17 "code planes" with 2^16 = 65,536
code points per plane. That means that Unicode has 17 * 2^16 = 1,114,112 code
points. That is a lot more than the 159,801 characters we said are in the
Unicode character set. Unicode is designed to be able to handle all the
characters that history has created so far and also all the characters that
will be created in the future. So Unicode has a really generous number of
code points.

<ul>
<li><a href="https://codepoints.net/planes">Unicode Planes</a></li>
<li><a href="https://www.compart.com/en/unicode/plane">Unicode Planes</a></li>
<li><a href="https://en.wikipedia.org/wiki/Plane_(Unicode)">Unicode Planes</a></li>
<li><a href="https://www.unicode.org/versions/Unicode17.0.0/core-spec/chapter-2/#G16482">Details of Allocation</a></li>
</ul>

The number
```text
    17 * 2^16 = (1 + 2^4) * 2^16
              = 2^16 + (2^4 * 2^16)
              = 2^16 + 2^20
              = 1,114,112
```
needs 21 bits to be written in binary, so it is sometimes said that Unicode
has a 21-bit address space. But that is not accurate because a 21-bit address
space would have 2^21 = 2,097,152 code points, much more than what Unicode
defines.

The address space for Unicode code points is from `0x000000` to `0x10FFFF` in
hexadecimal. Notice that `0x10FFFF` is the number `2^20 + 2^16 - 1` (notice
that the `1` is in the binary 2^20's place).

<!--
Unicode has seventeen 16-bit planes, the BMP and then 16 other planes.
The 16 other planes represent a 20-bit address space (4 + 16). The two
code units in a UTF-16 surrogate pair provide 10 bits each, for a 20-bit
address. That 20-bit address is added to 0x10000 to form a 21-bit code point.
The highest 20-bit address is 0xFFFFF, which gives the highest code point,

     0x0FFFFF
   + 0x010000
   ----------
     0x10FFFF

but this is NOT a 21-bit address space.
-->

Unicode organizes its code points into planes of size 65,536 because Unicode
started out as a 16-bit character encoding, a kind of super ASCII. For the
first few years of Unicode's existence, there were less than 65,536 characters
in the character set and it was thought that 65,536 characters enough for the
whole world. It turned out that 65,536 was no where near enough characters,
so Unicode grew to its present form.

Since Unicode is a coded character set, all the characters (code points) are in
an ordering from the first character to the last one. The first 127 characters
(code points) in the Unicode ordering are the 127 ASCII characters (Unicode
calls this the "Basic Latin block" instead of calling them ASCII). The next
128 characters (code points) are called the "Latin-1 block". These are the
most common characters used in Europe. The first 255 characters (code points)
in Unicode are essentially Windows code page 28591 (or code page 1252).



Unicode has a notation for Unicode code points. The notation uses the
hexadecimal value of a code point. For example, the letter 'a' in ASCII
has the code `97` in decimal, which is `0x61` in hexadecimal.  Since the
first 127 code points in Unicode are the same as ASCII, the Unicode code
point for the letter 'a' is written `U+0061`. In general, the notation for
a code point is `U+` followed by the hexadecimal digits of its code point
number. The notation does not care about leading zeros, so `U+0061` is the
same as `U+61` (but the Unicode standard does state that code points should
be written with a least four hexadecimal digits). Some Unicode code points
need as many as six hexadecimal digits.


* <https://home.unicode.org/>
* <https://www.unicode.org/versions/Unicode17.0.0/core-spec/>
* <https://en.wikipedia.org/wiki/Unicode>
* <https://www.compart.com/en/unicode/plane>
* <https://codepoints.net/planes>
* <https://www.compart.com/en/unicode/block/U+0000>
* <https://www.compart.com/en/unicode/block/U+0080>



## UTF-8

UTF-8 is a binary encoding of all the code points in Unicode. UTF-8 uses
an 8-bit code unit. That means that every Unicode code point is encoded
as either one, two, three, or four bytes. UTF-8 is a "variable length
encoding" because the encoding of a code point can have a length that
is between 1 and 4 bytes. If a string has five characters, the UTF-8
encoding of that string can be between 5 and 20 bytes long, depending on
the exact characters in the string.



## UTF-16

UTF-16 is a binary encoding of all the code points in Unicode. UTF-16 uses
a 16-bit code unit. That means that every Unicode code point is encoded as
either one or two 16-bit (two byte) words. Because the UTF-16 code unit is
two bytes, byte ordering is important. That leads to two additional encodings,
UTF-16BE and UTF-16LE. In UTF-16BE, the big-endian byte order is always used.
In UTF-16LE, the little-endian byte order is always used. In UTF-16, the byte
order used by a sequence of bytes is declared by a **byte order mark** (BOM)
at the beginning o the sequence.

Java uses UTF-16BE. The `char` data type represents a UTF-16BE code unit
(*not* a Unicode character!). If a Unicode code point is represented in
UTF-16BE by a single code unit, then that `char` value does represent a
Unicode character. But some Unicode code points require two code units in
UTF-16BE. In that case, we need two `char` values to represent that character.

<!--
Unicode has seventeen 16-bit planes, the BMP and then 16 other planes.
The 16 other planes represent a 20-bit address space (4 + 16). The two
code units in a UTF-16 surrogate pair provide 10 bits each, for a 20-bit
address. That 20-bit address is added to 0x10000 to form a 21-bit code point.
The highest 20-bit address is 0xFFFFF, which gives the highest code point,

     0x0FFFFF
   + 0x010000
   ----------
     0x10FFFF

but this is NOT a 21-bit address space.
-->



## UTF-32

UTF-32 uses a 32-bit code unit (similar to a Java `int`). UTF-32 uses
a single code unit for every code point in Unicode.

UTF-32 is a straightforward character encoding that uses the binary number
representation of each code point as the encoding. Since the address space
of Unicode code points is 21 bits, this address space easily fits into a
32-bit code unit. But it also wastes a lot of bits. Every encoding wastes
at least 11 bits of the code unit. Most of the time, it wastes 24 bits
because the majority of characters are from ASCII.

UTF-32 is usually not used for storing or transmitting Unicode characters
because it wastes so many bits. But UTF-32 is useful in certain situations.
It can be used to make encoding conversions easier to implement. Text editors
use it to represent in memory the text you are editing. Since (almost) every
character is essentially one `int`, UTF-32 makes it easier to jump around in
the text.

<ul>
<li><a href="https://en.wikipedia.org/wiki/UTF-32#Use">UTF-32, Use</a></li>
<li><a href="https://www.unicode.org/versions/Unicode17.0.0/core-spec/chapter-2/#G11149">UTF-32, Preferred usage</a></li>
</ul>



## Fonts

When we work with computer representations of text (or what are generally
called "writing systems") we need to make many detailed distinctions in
order to be clear about what we are saying. We need to define a number of
terms that describe text and its appearance, words like, character, glyph,
font, and typeface. We will not give precise definitions from these terms,
just definitions that are good enough to talk reasonably about text.

A **character** is a letter from an alphabet, or a common symbol like
a numeral or a punctuation mark.

A **glyph** is a drawing of a character. Here is a drawing of the letter 'a'.
But that is not the only way to draw the letter 'a'. Here are nine different
glyphs for the letter 'a'.

<span style="font-family: 'Lucida Calligraphy';       font-style: italic; font-size: 32px;">a</span>
<span style="font-family: 'Fira Sans',         serif;                     font-size: 32px;">a</span>
<span style="font-family: 'Fira Sans',    sans-serif; font-style: italic; font-size: 32px;">a</span>
<span style="font-family: 'Fira Sans',         serif; font-style: italic; font-size: 32px;">a</span>
<span style="font-family: 'Fira Sans',       cursive;                     font-size: 32px;">a</span>
<span style="font-family: 'Courier New';              font-style: italic; font-size: 32px;">a</span>
<span style="font-family: 'Courier New';              font-weight: bold;  font-size: 32px;">a</span>
<span style="font-family: 'Papyrus',         fantasy;                     font-size: 32px;">a</span>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Tangerine">
<span style="font-family: 'Tangerine',         serif;                     font-size: 50px;">a</span>

You could use any one of those glyphs to spell the word "cat". The choice
of a glyph does not change the meaning of the letter or the word. Most
importantly, all nine of these glyphs have the same character encoding. They
are all the ASCII (or maybe UTF-8) character with hexadecimal code `0x61`
(decimal code `97`).

Here are nine glyphs for the letter 'A'. Every one of these glyphs is
represented by the ASCII (or UTF-8) character code hexadecimal `0x41`
(decimal code `65`).

<span style="font-family: 'Lucida Calligraphy';       font-style: italic; font-size: 32px;">A</span>
<span style="font-family: 'Fira Sans',         serif;                     font-size: 32px;">A</span>
<span style="font-family: 'Fira Sans',    sans-serif; font-style: italic; font-size: 32px;">A</span>
<span style="font-family: 'Fira Sans',         serif; font-style: italic; font-size: 32px;">A</span>
<span style="font-family: 'Fira Sans',       cursive;                     font-size: 32px;">A</span>
<span style="font-family: 'Courier New';              font-style: italic; font-size: 32px;">A</span>
<span style="font-family: 'Courier New';              font-weight: bold;  font-size: 32px;">A</span>
<span style="font-family: 'Papyrus',         fantasy;                     font-size: 32px;">A</span>
<span style="font-family: 'Tangerine',         serif;                     font-size: 50px;">A</span>

If every one of those glyphs has the same ASCII code, then how does the
display system know that it should draw them differently. The letters 'a'
and 'A' have different ASCII codes, so we expect the display system to draw
something different for each one. But if we use the same code, `0x41`, nine
times, how do we get nine different drawings? To (partially) answer this
question, we need some more terminology.

When you choose a specific glyph for each letter in an alphabet, that
collection of glyphs is called a **font**. Usually, all the glyphs in
a font have the same size and are in a similar style.

A collection of fonts in different sizes, where each font is, more or
less, a scaled version of the other fonts, is called a **typeface**.
In HTML, typefaces are called "font families", which is a good
descriptive name. A typeface is a family of closely related fonts.

In many current uses of these terms, the distinction between a font and
a typeface is blurred. When people talk about a font, they often mean
a typeface.

In the above display of the nine glyphs for the letter 'A", just before
each character 'A' there is special code embedded into this document that
tells the display system to switch to a different typeface and choose a font
from that typeface. Once the display system is told to switch to a different
font, it will use that font to display every character it decodes. But in the
above display, the font is changed nine times, just before each occurrence of
the character 'A'.

If you want to see the hidden code that changes the font for each 'A', use
your mouse to point at one of the glyphs, then "right-mouse-click" on the
glyph and choose the menu item "Inspect". You can also examine the source
code for this document, the file "Readme_7_character_encodings.md" in the
zip file "streams_and_processes.zip".

Not all display systems can switch fonts character by character. For example,
most text editors cannot do that (but word processing programs can). In a
text editor, you chose a global font and then all the text in your document
is displayed using that font. Similarly for a console window. The font is
a global setting and all the text in the console window is shown in the same
font. In some console programs, you need to restart the program in order to
be able to change the display font.


**Exercise:** How does "changing the font" compare with "changing the code
page"? Both change the appearance of what you see on the screen without
changing the underlying data. Are they similar ideas? Are they the same
thing? (Hint: This is a subtle question.)


**Exercise:** Find out how to change the font in your text editor. Find
out how to change the code page in your text editor. Do the same for your
console (terminal) program.


**Exercise:** Look up "ligatures". Install into your text editor a
"programmer's font" that uses ligatures (such as Fira Code). How do
ligatures fit in to the ideas of character encodings, code points,
and glyphs? Does Unicode have code points for ligatures?


<ul>
<li><a href="https://en.wikipedia.org/wiki/Character_(computing)">Character</a></li>
<li><a href="https://en.wikipedia.org/wiki/Glyph">Glyph</a></li>
<li><a href="https://en.wikipedia.org/wiki/Font">Font</a></li>
<li><a href="https://en.wikipedia.org/wiki/Computer_font">Computer font</a></li>
<li><a href="https://en.wikipedia.org/wiki/Typeface">Typeface</a></li>
<li><a href="https://en.wikipedia.org/wiki/Font_family_(HTML)">Font family (HTML)</a></li>
<li><a href="https://en.wikipedia.org/wiki/Writing_system">Writing system</a></li>
</ul>



<!--
<ul>
<li><a href=""></a></li>
<li><a href=""></a></li>
<li><a href=""></a></li>
</ul>
-->
