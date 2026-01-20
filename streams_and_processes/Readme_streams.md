
Streams, I/O Redirection, Pipes, and Filters
============================================

Processes use streams for all of their I/O operations. A **stream**
is a sequence of bytes. The bytes can represent any kind of data, for
example, text, images, video, audio. In introductory programming courses,
streams are associated with files. A program reads or writes a stream of
data from a file on a storage device. But we will see that streams are
much more versatile. We will see that programs can read or write streams
of data from other programs.

* <https://en.wikipedia.org/wiki/Stream_(computing)>
* <https://docs.oracle.com/javase/tutorial/essential/io/streams.html>

Here are references to several online book chapters that review using streams for mostly file I/O.

<ul>
<li><a href="https://runestone.academy/ns/books/published/javajavajava/chapter-files.html">Chapter 11: Files and Streams</a> (<a href="http://www.cs.trincoll.edu/~ram/jjj/jjj-os-20170625.pdf#page=515">PDF</a>) from <a href="https://runestone.academy/ns/books/published/javajavajava/">Java Java Java</a></li>
<li><a href="https://ptgmedia.pearsoncmg.com/images/9780135166314/samplepages/9780135166314_Sample.pdf#page=23">Chapter 2, Input and Output</a> from <a href="https://www.informit.com/store/core-java-volume-ii-advanced-features-9780135166314">Core Java, Volume II--Advanced Features, 11th Edition</a></li>
<li><a href="https://math.hws.edu/javanotes-swing/c11/s1.html">Section 11.1, I/O Streams</a> (<a href="https://math.hws.edu/eck/cs124/downloads/javanotes9-swing-linked.pdf#page=582">PDF</a>) (<a href="https://math.hws.edu/javanotes-swing/source/chapter11/">Source code</a>)</li>
<li><a href="https://link.springer.com/content/pdf/10.1007/978-1-4842-3348-1_7">Chapter 7, Input/Output</a> from <a href="https://link.springer.com/book/10.1007/978-1-4842-3348-1">Java Language Features</a></li>
<li><a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP1406/Notes/COMP1406_Ch11_FileIO.pdf">Chapter 11, Saving and Loading Information</a> (<a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP1406/Notes/Code/Chapter11/">code</a>)from <a href="https://people.scs.carleton.ca/%7Elanthier/teaching/COMP1406/notes.html">Carleton University</a></li>
<li><a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP2401/Notes/COMP2401_Ch6_StreamsAndFileIO.pdf">Chapter 6, Streams and File/Device I/O</a> (<a href="https://people.scs.carleton.ca/~lanthier/teaching/COMP2401/Notes/Code/ch6/">code</a>) from <a href="https://people.scs.carleton.ca/%7Elanthier/teaching/COMP2401/notes.html">Carleton University</a></li>
</ul>


## Standard Streams

When a process is created by the operating system, the process is always
supplied with three open streams. These three streams are called the
"standard streams". They are

* standard input  (stdin)
* standard output (stdout)
* standard error  (stderr)

We can visualize a process as an object with three "connections" where
data (bytes) can either flow into the process or flow out from the process.

```text
                      process
                +-----------------+
                |                 |
           >---->>stdin    stdout>>------>
                |                 |
                |          stderr>>------>
                |                 |
                +-----------------+
```

A console application will usually have its stdin stream connected to the
computer's keyboard and its stdout and stderr streams connected to the
console window.

```text
                      process
                +-----------------+
                |                 |
    keyboard --->>stdin    stdout>>------+---> console window
                |                 |      |
                |          stderr>>------+
                |                 |
                +-----------------+
```

It is important to realize that the above picture is independent of the
programming language used to write the program which is running in the
process. Every process looks like this. It is up to each programming
language to allow programs, written in that language, to make use of
this setup provided by the operating system.

* <https://en.wikipedia.org/wiki/Standard_streams>

Every **operating system** has its own way of giving a process access to
the internal data structures the operating system uses to keep track of
what each standard stream is "connected" to.

The Linux operating system gives every process three **file descriptors*,

```text
    #define  STDIN_FILENO 0,  STDOUT_FILENO 1,  STDERR_FILENO 2
```

Linux provides the `read()` and `write()` system calls to let a process
read from and write to these file descriptors.

The Windows operating system gives every process three **handles**. We
retrieve the handles using the `GetStdHandle()` function with one of
these input parameters.

```text
     STD_INPUT_HANDLE, STD_OUTPUT_HANDLE, STD_ERROR_HANDLE
```

Windows provides the `ReadFile()` and `WriteFile()` system calls to let
a process read from and write to these handles.


Every **programming language** must have a way of representing the three
standard streams and every language must provide a way to read from the
standard input stream and a way to write to the standard output and
standard error streams.

For example, here is how the three standard streams are represented by some
common programming languages.

    Java uses stream objects.
      java.io.InputStream  System.in
      java.io.PrintStream  System.out
      java.io.PrintStream  System.err
    These are static fields in the java.lang.System class.

    .Net uses stream objects.
      System.IO.TextReader  Console.In
      System.IO.TextWriter  Console.Out
      System.IO.TextWriter  Console.Error
    These are static fields in the System.Console class.

    C++ uses stream objects.
      ios std::cin;
      ios std::cout;
      ios std::cerr;

    Standard C uses file objects.
      FILE* stdin;
      FILE* stdout;
      FILE* stderr;

The C language provides functions like `getchar()`, `scanf()`, and `fscanf()`
to read from `stdin` and it provides `printf()` and `fprintf()` to write to
`stdout` and `stderr`. On a Windows computer, C's `printf()` function will be
implemented using Window's `WriteFile()` system call with the `STD_OUTPUT_HANDLE`
handle. On a Linux computer, C's `printf()` function will be implemented using
Linux's `write()` system call with the `STDOUT_FILENO` file descriptor.

* <https://man7.org/linux/man-pages/man3/stdio.3.html>
* <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#field-summary>
* <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileDescriptor.html>
* <https://en.cppreference.com/w/cpp/header/iostream.html>
* <https://learn.microsoft.com/en-us/dotnet/api/system.console>


## I/O Redirection

*Every* process is created by the operating system at the request of some
other process, the parent process. When the parent process asks the
operating system to create a child process, the parent must tell the
operating system how to "connect" the child's three standard streams.
The parent telling the operating system how to connect the child's
three standard streams is usually referred to as **I/O redirection**.

At a shell command prompt, if we type a command like this,

```text
    > foo > result.txt
```

the shell program (cmd.exe on Windows, or bash on Linux) is the parent
process. The above command tells the shell process to ask the operating
system to create a child process from the foo program. But in addition
to asking the operating system to create the child process, the shell
process also instructs the operating system to redirect the child
process's standard output to the file result.txt. So when foo runs, it
looks like this.

```text
                   foo process
                +-----------------+
                |                 |
    keyboard --->>stdin    stdout>>----> result.txt
                |                 |
                |          stderr>>----> console window
                |                 |
                +-----------------+
```

Stdin and stderr have their default connections, and stdout is redirected
to the file result.txt.

If we type a command like this,

```text
    > foo > result.txt < data.txt
```

the shell process will ask the operating system to create a child process
from the foo program and also ask the operating system to redirect the
child process's standard output to the file result.txt and redirect the
child process's standard input to the file data.txt. So when foo runs, it
looks like this.

```text
                   foo process
                +-----------------+
                |                 |
    data.txt --->>stdin    stdout>>----> result.txt
                |                 |
                |          stderr>>----> console window
                |                 |
                +-----------------+
```

* <https://en.wikipedia.org/wiki/Redirection_(computing)>
* <https://www.linfo.org/redirection.html>
* <https://man7.org/linux/man-pages/man1/bash.1.html#REDIRECTION>
* <https://ss64.com/nt/syntax-redirection.html>


## Shared streams

When two processes share a stream, it is usually the case that one of the
two processes is idle while the other process uses the shared stream (the
idle process will often be waiting for the other process to terminate). If
two processes are simultaneously using a shared stream, the results can be
confusing and unpredictable.

If two processes simultaneously use an output stream, then their outputs will
be, more or less, randomly intermingled in the stream's final destination.
This can lead to unusable results.

If two processes simultaneously use an input stream, for example, as in the
following picture, then it is not the case that every input byte flows into
each process. Each input byte can only be consumed by one of the two
processes. Which process gets a particular byte of input depends on the
ordering of when each process calls its `read()` function on the input
stream. This is almost never a desirable situation. Processes almost never
simultaneously use a shared input stream. Shared input streams are very
common, but the two processes almost always have a way to synchronize their
use of the stream so that they are never reading from it simultaneously.

```text
                       parent
                  +--------------+
                  |              |
           +----->>stdin  stdout>>------->
           |      |              |
           |      |       stderr>>--->
           |      |              |
           |      |              |
           |      +--------------+
     ------+
           |
           |              child
           |         +--------------+
           |         |              |
           +-------->>stdin  stdout>>------>
                     |              |
                     |       stderr>>--->
                     |              |
                     |              |
                     +--------------+
```


## Pipes

If we type a command like this,

```text
    > foo < data.txt | bar > result.txt
```

the shell process will ask the operating system to create two child
processes, one from the `foo` program and the other from the `bar` program.
In addition, the shell process will ask the operating system to create a
**pipe** object and have the `stdout` of the `foo` process redirected to the
input of the pipe, and have the `stdin` of the `bar` process redirected to
the output of the pipe. Finally, the shell process will ask the operating
system to redirect the `bar` process's standard output to the file `result.txt`
and redirect the `foo` process's standard input to the file `data.txt`. So
while this command is executing, it looks like this.

```text
                 foo process                  bar process
              +---------------+            +---------------+
              |               |    pipe    |               |
    data.txt-->>stdin  stdout>>--========-->>stdin  stdout>>------> result.txt
              |               |            |               |
              |        stderr>>---+        |        stderr>>----+-> console window
              |               |   |        |               |    |
              +---------------+   |        +---------------+    |
                                  |                             |
                                  +-----------------------------+
```

In the above command, the two programs, `foo` and `bar`, are running
simultaneously (in parallel) with each other. The pipe object acts as a
"buffer" between the two processes. Whenever the `foo` process writes
something to its output, that something gets put in the pipe "buffer". Then
when the `bar` process wants to read some input data, it reads whatever is
currently in the pipe "buffer". If the `foo` process writes data faster than
the `bar` process reads data, then data accumulates in the pipe. When `foo`
terminates, it may be that data still remains in the pipe, in which case `bar`
will continue to run until it has emptied the pipe. On the other hand, if the
`bar` process reads data out of the pipe much faster than `foo` writes data
into the pipe, then the `bar` process will often find the pipe empty when
`bar` wants to read some data. In that case, `bar` "blocks" and waits until
some data shows up in the pipe. When the `foo` process writes its last bit
of data to the pipe and then `foo` terminates, the operating system will let
the `bar` process know that it has reached the "end-of-file" after the `bar`
process reads the last bit of data from the pipe.

* <https://en.wikipedia.org/wiki/Pipeline_(Unix)>

Here is another way to think about the above pipeline command. The shell
process could run the two programs, `foo` and `bar`, sequentially, one
after the other. In other words, the shell process could interpret this
command,

```text
    > foo < data.txt | bar > result.txt
```

as the following three commands.

```text
    > foo < data.txt > temp
    > bar < temp > result.txt
    > del temp
```

These three commands would have a picture that looks like this.

```text
                   foo process
                +-----------------+
                |                 |
    data.txt --->>stdin    stdout>>----> temp
                |                 |
                |          stderr>>----> console window
                |                 |
                +-----------------+

                   bar process
                +-----------------+
                |                 |
        temp --->>stdin    stdout>>----> result.txt
                |                 |
                |          stderr>>----> console window
                |                 |
                +-----------------+
```

First the `foo` process is executed with its output stored in a temporary
file called `temp`. Then the `bar` process is run with its input coming
from the `temp` file. Then the `temp` file gets deleted.

Notice that this sequential interpretation of the pipeline command might
be considerably slower than the parallel interpretation. And since the
sequential interpretation needs to store all the intermediate data in
a temp file, the sequential interpretation may require far more storage
space than the parallel interpretation.


Here is a more detailed picture of a Java process, its three standard
streams, and their buffers. The "user space" buffers belong to Java
classes and are used by Java methods. For example, the `Scanner` class,
and all its methods, have a user space input buffer. The `PrintWriter`
class, and its `print()`, `println()`, `printf()` methods, have a user
space output buffer. (Note: C and C++ processes do not have a user
space buffer for `stderr`.)

```text
                                        Java process
                             +---------------------------------------+
                             |                                       |
                kernel space |  user space                user space |        kernel space
                +------+     |  +------+                  +------+   |        +------+
    keyboard -->|      |----->>-|      |->stdin   stdout->|      |-->>---+--->|      |---> console window
                +------+     |  +------+                  +------+   |   |    +------+
                 buffer      |   buffer                    buffer    |   |     buffer
                             |                                       |   |
                             |                            user space |   |
                             |                            +------+   |   |
                             |                    stderr->|      |-->>---+
                             |                            +------+   |
                             |                             buffer    |
                             +---------------------------------------+
```


Here is a sketch of two processes connected with a pipe and some of the
associated buffers.

```text
                  foo process                                    bar process
               +---------------+      user space              +---------------+
               |               |      +------+                |               |
    data.txt -->>stdin   stdout>>-----|      |--+        +--->>stdin   stdout>>----> result.txt
               |               |      +------+  |        |    |               |
               |               |       buffer   |        |    |               |
               |               |                |        |    |               |
               |        stderr>>--+    +--------+        |    |        stderr>>---+-> console window
               |               |  |    |                 |    |               |   |
               +---------------+  |    |  kernel space   |    +---------------+   |
                                  |    |  +------+       |                        |
                                  |    +--| pipe |--+    |                        |
                                  |       +------+  |    |                        |
                                  |        buffer   |    |                        |
                                  |                 |    |                        |
                                  |       +---------+    |                        |
                                  |       |              |                        |
                                  |       |  user space  |                        |
                                  |       |  +------+    |                        |
                                  |       +--|      |----+                        |
                                  |          +------+                             |
                                  |           buffer                              |
                                  |                                               |
                                  +-----------------------------------------------+
```


## Filters and Pipelines

A **filter** is a program that reads data from its `stdin`, does some kind of
operation on the data, and then writes that converted data to its `stdout`.

In the `filter_programs` folder there are Java programs that can act as filter
programs. They are all very short programs that do simple manipulations of the
input characters. Look at the source code. Compile and then run them using
command-lines like the following.

```text
    > java Reverse < Readme.txt > result.txt
    > java Double < Readme.txt | java Reverse
    > java Double | java ToUpperCase | java Reverse
    > java ShiftN 2 | java ToUpperCase | java Reverse
    > java Twiddle < Readme.txt | java ToUpperCase | java Double | java RemoveVowels > result2.txt
```

Then run a couple of the programs by themselves, without any I/O redirection
or pipes, to see how they manipulate input data (from the keyboard) to produce
output data (in the console window).

```text
    > java ToUpperCase
    > java Double
    > java Reverse
    > java MakeOneLine
```

Notice that you need to tap the `Enter` key to send input from the keyboard
to the program. Sometimes you see immediate output. Sometimes there is no
output until the input is terminated (end-of-file). You denote the end of
your input to the program by typing `Control-z` on Windows or `Control-d`
on Linux. **Do not* use `Control-C`. That terminates the program (instead
of terminating just the program's input) and causes the program's output
to be lost.

* <https://en.wikipedia.org/wiki/Pipeline_(Unix)>
* <https://en.wikipedia.org/wiki/Pipeline_(software)>
* <https://en.wikipedia.org/wiki/Filter_(software)>



## Command-line Syntax

We have seen that command-lines can be made up of, among other things,
program names, command-line arguments, file names, I/O redirection
operators, and pipes. In this section we will look at the syntax of
building complex command-lines that combine all of these elements along
with a few new elements.




CMD syntax.

* <https://ss64.com/nt/syntax-redirection.html>
* <https://ss64.com/nt/syntax-conditional.html>
* <https://ss64.com/nt/syntax.html>
* <https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmd>
* <https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/command-line-syntax-key>


Bash syntax.

* <https://man7.org/linux/man-pages/man1/bash.1.html#REDIRECTION>
* <https://catonmat.net/bash-redirections-cheat-sheet>
* <https://man7.org/linux/man-pages/man1/bash.1.html#SHELL_GRAMMAR>
