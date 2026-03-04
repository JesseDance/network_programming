
Java Streams
============

Like all programming languages, Java has a way for programs to make use
of the data streams provided by the operating system. Java's streams are
defined in the `java.io` package.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html">Package java.io</a></li>
</ul>

Here are references to several online book chapters that review using Java
streams, mostly for file I/O.

<ul>
<li><a href="https://runestone.academy/ns/books/published/javajavajava/chapter-files.html">Chapter 11: Files and Streams</a> (<a href="http://www.cs.trincoll.edu/~ram/jjj/jjj-os-20170625.pdf#page=515">PDF</a>) from <a href="https://runestone.academy/ns/books/published/javajavajava/">Java Java Java</a></li>
<li><a href="https://ptgmedia.pearsoncmg.com/images/9780135166314/samplepages/9780135166314_Sample.pdf#page=23">Chapter 2, Input and Output</a> from <a href="https://www.informit.com/store/core-java-volume-ii-advanced-features-9780135166314">Core Java, Volume II--Advanced Features, 11th Edition</a></li>
<li><a href="https://math.hws.edu/javanotes-swing/c11/s1.html">Section 11.1, I/O Streams</a> (<a href="https://math.hws.edu/eck/cs124/downloads/javanotes9-swing-linked.pdf#page=582">PDF</a>) (<a href="https://math.hws.edu/javanotes-swing/source/chapter11/">Source code</a>)</li>
<li><a href="https://link.springer.com/content/pdf/10.1007/978-1-4842-3348-1_7">Chapter 7, Input/Output</a> from <a href="https://link.springer.com/book/10.1007/978-1-4842-3348-1">Java Language Features</a></li>
<li><a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP1406/Notes/COMP1406_Ch11_FileIO.pdf">Chapter 11, Saving and Loading Information</a> (<a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP1406/Notes/Code/Chapter11/">code</a>)from <a href="https://people.scs.carleton.ca/%7Elanthier/teaching/COMP1406/notes.html">Carleton University</a></li>
<li><a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP2401/Notes/COMP2401_Ch6_StreamsAndFileIO.pdf">Chapter 6, Streams and File/Device I/O</a> (<a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP2401/Notes/Code/ch6/">code</a>) from <a href="https://people.scs.carleton.ca/%7Elanthier/teaching/COMP2401/notes.html">Carleton University</a></li>
<li><a href="https://docs.oracle.com/javase/tutorial/essential/io/streams.html">I/O Streams</a> from the <a href="https://docs.oracle.com/javase/tutorial/essential/index.html">Java Tutorials</a></li>
</ul>

All the example code mentioned this document is in the sub folder called
"java_streams" from the following zip file.

* <http://cs.pnw.edu/~rlkraft/cs33600/for-class/streams_and_processes.zip>



## I/O streams vs. Functional streams

The Java language now has two types of objects that are called
"streams". The two types are very different from each other with entirely
different uses. First, there's the traditional I/O streams that we discuss
in this document. Second, starting in Java 8, Java defined a `Stream` class
that implements the Stream abstract data type (ADT) from functional
programming languages. The new `Stream` class is not for doing I/O. The new
`Stream` class provides a modern way to process data structures from the Java
Collections Framework.

Here are links to the documentation for the package of "functional streams"
and the package of "I/O streams".

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/package-summary.html">Package java.util.stream</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html">Package java.io</a></li>
</ul>

Here are the basic "stream" classes. You can see that the
`java.util.stream.Stream` class is nothing like the `java.io.InputStream`
or `java.io.OutputStream` classes.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html">java.util.stream.Stream</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/InputStream.html">java.io.InputStream</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/OutputStream.html">java.io.OutputStream</a></li>
</ul>

The Stream abstract data type is becoming an important part of modern
programming languages. It plays a big part in modern Java.

* <https://en.wikipedia.org/wiki/Stream_(abstract_data_type)>
* <https://dev.java/learn/api/streams/>
* <https://www.baeldung.com/java-8-streams>
* <https://link.springer.com/content/pdf/10.1007/978-1-4842-7080-6_8>
* <https://link.springer.com/content/pdf/10.1007/978-1-4842-7135-3_6>


## Basic I/O streams

We have seen that all the data that enters a Java process must go through
an `InputStream` object and all the data that leaves a Java process must
go through an `OutputStream` object. Both `InputStream` and `OutputStream`
objects can only work with bytes of data. No other data type can be read
or written by these objects.

We have seen how we can read or write primitive data types over streams
by making repeated calls to the `read()` or `write()` method and by using
a `ByteBuffer` object to convert primitive data type values into
`byte` arrays, and back.

In theory, we can do all the I/O a Java program needs using only
`InputerStream` and `OutputStream` objects. But our code would be
tedious to write and difficult to maintain. The designers of the Java
language found an elegant way to solve this problem.

We mentioned earlier that it is not a good design to make `InputStream` and
`OutputStream` more complicated by giving them a bunch of methods for reading
and writing other data types. How many data types should be coded directly into
these two classes? What happens when a new data type is developed? How would it
be integrated into these two stream classes?

Java solves this problem by designing a layered system of stream classes.
Every stream class is given a single responsibility, or task. We solve
complex I/O problems by "wrapping" stream objects around other stream
objects, creating a kind of chain of stream objects, with each stream object
doing one specific I/O task for us. The whole chain of stream object solves
our I/O problem. This layered system is very flexible and adaptive. It is
straight forward to create a stream class that implements some new I/O
responsibility and then combine the new kind of stream object with other
stream objects.

Let us look at a simple example. Recall that in the "data_formats" folder
there's a pair of programs called "IntClient.java" and "IntServer.java".
The "IntServer.java" program writes, to its standard output, the four `byte`
values that make up an `int` value. The "IntClient.java" program reads four
`byte` values form its standard input and interprets those bytes as an `int`
value. Each program in this pair uses a `ByteBuffer` object to translate
between `byte` and `int` values.

Here is the code from "IntClient.java" that reads four bytes and uses
a `ByteBuffer` to interpret those bytes as an `int` value.

```java
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
```

Java has a stream class called `DataInputStream` that can do the byte
translation that `ByteBuffer` does.

Here is the code that uses a `DataInputStream` to read four bytes and
interpret those bytes as an `int` value (this code is in the
"IntClient.java" program in the "java_streams" folder").

```java
      final DataInputStream in = new DataInputStream( System.in );
      final int n = in.readInt();
```

Notice how we longer call `read()` directly on `System.in`. Instead we "wrap"
a `DataInputStream` object around the `System.in` object. Then we call the
`readInt()` method on the `DataInputStream` object. That object, which has a
reference to the `System.in` object, calls the `read()` method in `System.in`
four times to get four bytes of data, puts the four bytes together as a single
`int` and then returns the `int` value to us.

This code uses two kinds of stream objects, each with its own responsibility.
The `System.in` object has the responsibility of actually getting byte data
from the source of bytes. The `DataInputStream` object has the responsibility
of grouping those bytes into our desired data type. The resulting code is easy
to read and maintain.

We can draw a picture that helps us understand this "layered" stream design.
Here is our usual picture of a Java process with its three standard streams.

```text
                   IntClient
          +-------------------------+
          |                         |
    >---->> System.in    System.out >>---->
          |                         |
          |              System.err >>---->
          |                         |
          +-------------------------+
```

The next picture visualizes the idea that a `DataInputStream` object is
"wrapped" around the `System.in` object.

```text
                           IntClient
          +---------------------------------------------+
          |     +-----------------+                     |
          |     | DataInputStream |                     |
          +-----+-------+         |                     |
    >---->> System.in   >>        >> in      System.out >>---->
          +-----+-------+         |                     |
          |     |                 |                     |
          |     +-----------------+          System.err >>---->
          |                                             |
          +---------------------------------------------+
```

The `System.in` object gets data from outside the process. The `DataInputStream`
object gets data from the `System.in` object it is "wrapped" around.

We do not in fact "wrap" one object around another object. What we really do
is give the "outer" object a reference to the "inner" object. When we make
this constructor call,
```java
      final DataInputStream in = new DataInputStream( System.in );
```
we are passing a reference to the `System.in` object to the `DataInputStream`
object that we are constructing. The `DataInputStream` object stores that
reverence value in some instance field of the object. When we call `readInt()`
on the `DataInputStream` object, it uses its reference to the `System.in`
object to call the `read()` method in `System.in`.

Every time we call `in.readInt()` on the `DataInputStream` object, `readInt()`
calls the `read()` method on its `System.in` object four times to read four
bytes. Then `readInt()` converts those four bytes into an `int` value and
returns that `int` to us.

This picture leads to another concept. The "stream" metaphor implies that data
bytes "flow" like a stream of water. We can even us a "pipe" to carry this flow.
But this metaphor can fool you. Data bytes do not "flow" into the `System.in`
stream from their source. Rather, data bytes are "pulled" into `System.in` from
their source. The data bytes come from some source (maybe the keyboard, maybe
a file). We must call the `read()` method in `System.in` in order for it to
pull a data byte from the source. If we never call `read()`, then no data
bytes will ever leave the source.

When we call `readInt()` on the `in` object, we are "pulling" an `int` value
out of the `DataInputStream` stream. It needs to "pull" four bytes out of its
`System.in` stream. `System.in` pulls those bytes out of whatever is its data
source.


Let's look at an example that works in the other direction and converts
an `int` value into four `byte` values.

Here is the code from the "IntServer.java" program in the "data_formats"
folder. It reads an integer from its standard input as a string of digits,
uses a `ByteBuffer` to convert the `int` value into four `byte` values,
and then writes the four bytes to its standard output.

```java
      final int n = new java.util.Scanner(System.in).nextInt();
      byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                               .putInt(n)
                               .array();
      System.out.write(bytes[0]);
      System.out.write(bytes[1]);
      System.out.write(bytes[2]);
      System.out.write(bytes[3]);
      System.out.flush();
```

Java has a `DataOutputStream` class that lets us write primitive values as
sequences of `int` values. Let us replace the `ByteBuffer` in the previous
code with a `DataOutputStream`.

```java
      final Scanner in = new Scanner(System.in);
      final DataOutputStream out = new DataOutputStream(System.out);

      final int i = in.nextInt();
      out.writeInt(i);
      out.flush();
```

Notice how we longer call `write()` directly on `System.out`. Instead we
"wrap" a `DataOutputStream` object around the `System.out` object. Then we
call the `writeInt()` method on the `DataOutputStream` object. That object
takes our `int` value, breaks it up into its four `byte` values, then, using
its reference to the `System.out` object, calls the `write()` method in
`System.out` four times to put the four bytes into their destination.

We are again using two kinds of stream objects, each with its own responsibility.
The `DataOutputStream` object has the responsibility of destructuring the
given data type into the appropriate number of bytes. The `System.out` object
has the responsibility of actually putting byte data into its destination.

Notice that we are also using two other stream objects, the `System.in`
object and a `Scanner` object. The `Scanner` object is "wrapped" around the
`System.in` object. As before, the responsibility of `System.in` is to pull
bytes out of the data source. The responsibility of `Scanner` is to build
primitive values out of their representations as a string of digits. When we
call `in.nextInt()` on the `Scanner` object, it pulls enough bytes out of the
`System.in` object to build up an integer string, and then parse it to an `int`
value. Notice that the `Scanner` object does not know how many bytes it might
need. The number "423123456" needs a lot more bytes than the number "42".

The next picture visualizes the idea that a `DataOutputStream` object is
"wrapped" around the `System.out` object, and a `Scanner` object is
"wrapped" around the `System.in` object.

```text
                                 IntServer
         +---------------------------------------------------------------+
         |     +----------------+               +------------------+     |
         |     |    Scanner     |               | DataOutputStream |     |
         +-----+-------+        |               |         +--------+-----+
   >---->> System.in   >>       >> in      out >>        >>   System.out >>---->
         +-----+-------+        |               |         +--------+-----+
         |     |                |               |                  |     |
         |     +----------------+               +------------------+     |
         |                                                               |
         |                                                    System.err >>---->
         |                                                               |
         +---------------------------------------------------------------+
```

When we call `out.writeInt()`, the `DataOutputStream` object takes our `int`
value and reduces it to four `byte` values. Then it uses its reference to the
`System.out` object to call the `write()` method four times. Each time it
calls `write()`, one `byte` value is put into the data destination.


Let's look carefully at how `System.in` is being used in the last two programs,
"IntClient.java" and "IntServer.jara".

The "IntClient.java" program wraps a `DataInputStream` around `System.in`.
```java
      final DataInputStream in = new DataInputStream( System.in );
```
The "IntServer.java" program wraps a `Scanner` around `System.in`.
```java
      final Scanner in = new Scanner( System.in );
```
In both cases we use the name `in` to refer to the outer stream object.

In "IntClient.java" we call the `in.readInt()` method from the
`DataInputStream` class.

In "IntServer.java" we call the `in.nextInt()` method from the
`Scanner` class.

These two methods, `readInt()` and `nextInt()`, have similar names, and
somewhat similar responsibilities, but they behave in very different ways.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readInt()">java.io.DataInputStream.readInt()</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html#nextInt()">java.util.Scanner.nextInt()</a></li>
</ul>

The `readInt()` method from `DataInputStream` reads exactly four bytes from
its inner (contained) input stream and bundles those four bytes into an `int`
value and then returns that `int`. Since *any* four `byte` values make up a
valid `int` value, the `readInt()` method cannot fail unless its inner
stream cannot produce four `int` values. In that case, `readInt()` throws
an `EOFException`.

The `nextInt()` method from the `Scanner` class reads bytes from its inner
stream until it has enough bytes to build a `String` token. Then it tries to
parse that token into an `int` value. If the parsing succeeds, the resulting
`int` value is returned. If the parsing fails, then the`nextInt()` method
throws an `InputMismatchException`. This can happen for several reasons. The
token may not represent an integer number or the token may represent an
integer number that is too big to fit in an `int` value. If the inner stream
runs out of bytes while `nextInt()` is building a token, then `nextInt()`
just uses what it has so far as the token and tries to parse it. If the inner
stream has no data bytes, so `nextInt()` cannot build any `String` token,
then `nextInt()` throws `NoSuchElementException`. Notice that `nextInt()`
never denotes end-of-file. That is part of the responsibility of the
`hasNextInt()` method, which should be called before calling `nextInt()`.


Let's look at one more example. In the "java_streams" folder there is the
"IntServerN.java" program. It reads up to "N" integers (as strings of digits)
from its standard input stream and writes their equivalent `byte` values to
its standard output stream. If the value of "N" is very large, then this
program will write a lot of data to standard output. We can make the program
run faster by giving it a large output buffer.


```java
      final var in = new Scanner(System.in);
      final var out = new DataOutputStream(
                         new BufferedOutputStream(
                            System.out, 4096));

      for (int i = 0; i < n; ++i)
      {
         final int i = in.nextInt();
         out.writeInt(i);
      }
      out.close();
```

Here is a picture illustrating this composition of three output streams.

```text
                                 IntServerN
        +------------------------------------------------------------------+
        |                                 +----------------------+         |
        |                                 | DataOutputStream     |         |
        |     +--------------+            |     +----------------+-----+   |
        |     |    Scanner   |            |     | BufferedOutputStream |   |
        +-----+-------+      |            |     |   +---+     +--------+---+
  >---->> System.in   >>     >> in   out >>    >>   |buf|    >> System.out >>---->
        +-----+-------+      |            |     |   |   |     +--------+---+
        |     |              |            |     +   +---+              |   |
        |     +--------------+            |     +----------------+-----+   |
        |                                 |                      |         |
        |                                 +----------------------+         |
        |                                                                  |
        |                                                       System.err >>---->
        |                                                                  |
        +------------------------------------------------------------------+
```

When we call `out.writeInt(i)` on the `DataOutputStream` object, the `writeInt()`
method takes the `int` value and breaks it up into four `byte` values. Then it
calls the `write()` method four times on the `BufferedOutputStream` object. Each
call to `write()` deposits a `byte` value in the internal buffer array, called
`buf` in the picture. When the `buf` array becomes full, the `write()` method in
`BufferedOutputStream` calls the `write(byte[])` method on `System.out`, which
sends the whole array of data to its destination. This final write is usually an
operating system call to write to a file. Operating system calls are slow, so it
helps to minimize how many of them we make. That is what the `buf` array in
`BufferedOutputStream` accomplishes. Without the `BufferedOutputStream` object
between the `DataInputStream` object and the `System.out` object, every time
`writeInt()` writes a single byte to `System.out`, that write causes an
operating system call to write to a file. The `buf` array, since it has size
4,096 bytes, reduces the number of operating system calls by many thousands.
Its not unusual for the buffered version of this program to be 10 times faster
than the unbuffered version.


**Exercise:** Explain what is wrong with this stream composition.
```java
      final var out = new BufferedOutputStream(
                         new DataOutputStream(
                            System.out), 4096);
```


Recall that in the "data_formats" folder there's the program "CreateData.java"
that writes 16 bytes of "raw" data to standard output, and a sequence of
programs called "ReadDataAs__.java" that each read 16 bytes form their
standard input stream and interpret the bytes as a stream of one particular
primitive data type. The "ReadDataAs__.java" programs in the "data_formats"
folder each use a `ByteBuffer` object to translate the "raw" bytes from
"CreateData.java" into the appropriate primitive data type.

In the folder "java_streams" there is a copy of "CreateData.java" and
another sequence of programs called "ReadDataAs__.java". These programs in
the "java_streams" folder each use a `DataInputStream` object to translate
the "raw" byte data from "CreateData.java" into the appropriate primitive
data type.



## Filter streams

Java has quite a few stream classes. The stream  classes are organized to all
derive from four abstract base classes, `InputStream`, `OutputStream`, `Reader`,
and `Writer`.

Streams derived from `InputStream` and `OutputStream` are meant to handle
`byte` data (often referred to as "binary streams). Streams derived from
`Reader` and `Writer` are meant to handle text data (strings).

Streams derived from `InputStream` and `Reader` are meant to handle data input.
Streams derived `OutputStream` and `Writer` are meant to handle data output.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-tree.html">Hierarchy For Package java.io</a></li>
<li><a href="https://runestone.academy/ns/books/published/javajavajava/streamsand-files.html#fig-streamhier">Java's stream hierarchy</a></li>
</ul>

Most stream classes have a single responsibility. An exception is
`RandomAccessFile`.

There are roughly three kinds of stream classes. Let's look at input streams
(output streams are similar).

1. There are input streams classes that cannot be instantiated, like
`InputStream`, `FilterInputStream`, `Reader`, or `FilterReader`.

2. There are input stream classes that must be instantiated by connecting
them directly to a data source, like `FileInputStream`, `FileReader`,
`ByteArrayInputStream`, `CharArrayReader`, or `StringReader`.

3. There are input streams that can only be instantiated by wrapping them
around some other input stream. You can see these in the documentation for
their constructors. Their constructors all require another input stream
object that the constructed stream can read data from. Examples are
`DataInputStream`, `BufferedInputStream`, `ObjectInputStream`,
`InputStreamReader`, `BufferedReader`.

The first set of input streams are not useful because we can never create
them. We study them because they document the methods that their subclasses
inherit.

The second set of input streams represent sources of data. But this list is
very incomplete. There are many sources of data for which there is no concrete
input stream class. For example, the keyboard is a source of input data, but it
is not represented by any of these concrete stream classes. Another example is
a network connection, which is a source of input data, but is not represented
by a concrete input stream class.

The third set of input streams is the most interesting set. These are the
streams that let us build up sophisticated input streams by composing multiple
kinds of streams that each provide some needed functionality. We saw examples
of this above using `DataInputStream` and `BufferedInputStream`. There is
another example below using `ZipInputStream`.


<!-- "is a" and "has a" relationships
<ul>
<li><a href="https://en.wikipedia.org/wiki/Is-a">is-a</a></li>
<li><a href="https://en.wikipedia.org/wiki/Has-a">has-a</a></li>
</ul>
 -->


Not all of Java's stream classes are in the `java.io` package. For example,
the `ZipInputStream` and `ZipOutputStream` classes are in the `java.util.zip`
package.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/zip/ZipOutputStream.html">ZipOutputStream</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/zip/ZipInputStream.html">ZipInputStream</a></li>
</ul>

We can build an example using the `ZipOutputStream` class that shows how
versatile the Java stream model is. In the "java_streams" folder there is
the "ZipServer.java" program. This program wraps a `ZipOutputStream` around
the `System.out` stream. The program then writes to the zip stream all the
bytes it reads from its standard input stream. The `ZipOutputStream` object
compresses all the bytes it receives and writes the compressed data to the
`System.out` stream.

The code is simple. It looks like this.

```java
      final ZipOutputStream zip = new ZipOutputStream(System.out);
      zip.putNextEntry(new ZipEntry("data"));
      int b = 0;
      while (-1 != (b = System.in.read()))
      {
         final byte[] bytes = { (byte)b };
         zip.write(bytes, 0, 1);
      }
      zip.close();
```

Here is a picture that illustrates how simple this program's structure is.
All the logic for compressing data is in the object that "wraps" around
`System.out`.

```text
                            ZipServer
           +---------------------------------------------+
           |                    +-----------------+      |
           |                    | ZipOutputStream |      |
           |                    |        +--------+------|
data >---->> System.in     zip >>       >>    System.out >>----> zipped data
           |                    |        +--------+------|
           |                    |                 |      |
           |                    +-----------------+      |
           |                                             |
           |                                  System.err >>---->
           |                                             |
           +---------------------------------------------+
```

This is a good example of how extensible the Java stream classes are. Imagine
if this zip feature needed to be added to a monolithic `OutputStream` class
that "conveniently" tries to do everything. Java's composable stream classes
make it easy to create new stream features. It also makes it straight forward
to add a new feature to an existing stream. This design does add a layer of
complexity to using Java streams. You need to understand the overall design
of Java's stream classes to use them effectively.


**Exercise:** Modify "ZipServer.java" to be a program called "IntZipServer.java"
that reads integers as strings of digits from standard input (up until eof),
converts each `int` value to four bytes, compresses all the byte data, and
then writes the compressed data to standard output.


**Exercise:** Write a zip client program that uses a `ZipInputStream` to read
zipped data through the standard input stream and then write the uncompressed
data to the standard output stream.


**Exercise:** Explain the difference between the following stream compositions.
Are they both useful? If so, is one more useful than the other?

```java
      final ZipOutputStream zip = new ZipOutputStream(
                                     new BufferedOutputStream(
                                        System.out));
```

```java
      final BufferedOutputStream bos = new BufferedOutputStream(
                                          new ZipOutputStream(
                                             System.out));
```


**Exercise:** Explain the relationship between the uses of the word "filter"
in `FilterInputStream` (or `FilterOutputStream`) and a "filter processes" that
reads from the standard input stream and writes to the standard output stream.



## End-of-file (eof)

End-of-file means, more or less, that an input stream is in a state where
no data can be read from it. This is not a good definition for eof, but
I do not know a general, always correct definition for eof.

Java has four broad ways of telling us that an input stream is in the eof
state. From a practical point of view, an input stream is in the eof state
whenever the JVM tells us it is.

End-of-file is a state that any input stream can be in, even if the stream
has nothing to do with reading from a file. An input stream that is not
connected to a file can still have the end-of-file condition being true.
It would be better if this state was called "end-of-stream". In some parts
of the Java documentation, they do use the term end-of-stream instead of
end-of-file.

End-of-file (end of stream) is much trickier than you might think. For example,
an input stream can be in the end-of-file state and then something can change
the stream's state and the stream is no longer in the eof state (see the
program "End_of_file_Demo.java" in the "java_streams" folder). It would be
wrong to say that eof means that you can no longer read data from a stream.
While that is true for many input streams, it is not true for all of them.

<ul>
<li><a href="https://en.wikipedia.org/wiki/End-of-file">End-of-file</a></li>
</ul>

Here are the four ways that methods in Java's I/O classes can denote that
an input stream is in the eof state.

1. The `read()` methods in `InputStream`, and all the classes derived from it, return `-1`.
2. The `hasNext()` method in the `Scanner` class returns `false`.
3. The `readLine()` method in the `BufferedReader` class returns `null`.
4. The various versions of "read" in the `DataInputStream` class throw a `EOFException`.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/InputStream.html#read()">InputStream.read()</a> returns -1</li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html#hasNext()">Scanner.hasNext()</a> returns false</li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/BufferedReader.html#readLine()">BufferedReader.readLine()</a> returns null</li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readInt()">DataInputStream.readInt()</a> throws EOFException</li>
</ul>

Here are the most important cases where we can get end-of-file on an input stream.

1. The input stream is connected to a file.
2. The input stream is connected to the keyboard.
3. The input stream is connected to a pipe.
4. The input stream is connected to a network connection.

We will look at the details of each of these cases.





<!--
Both `InputStream` and `Reader` are abstract classes.

`InputStream` has only one abstract method `read()`. All of the other methods
are concrete. In particular, the `read(byte[])` method is implemented in terms
of the abstract `read()` method.

`Reader` has two abstract methods, `read(char[] cbuf, int off, int len)` and `close()`.

Why is `close()` concrete in `InputStream` but abstract in `Reader`?
Is closing a stream of characters more complicated than closing a stream of bytes?
-->

<!--
>java IntServer
1234
-
>java IntServer | java -cp filters.jar HexDump
1234
00 00 04 D2
> chcp
Active code page: 437
-->

<!--
<ul>
<li><a href=""></a></li>
<li><a href=""></a></li>
<li><a href=""></a></li>
</ul>
-->
