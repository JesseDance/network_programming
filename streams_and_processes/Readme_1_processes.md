
Processes
=========

Remember what a process is. A **process** is a running instance of a program
(where a **program** is an executable file stored in the computer's file
system). We make a distinction between a "program" and a "process" because
a program, like Notepad, can have several instances of itself running at any
given time. Each such instance is a different process (but each instance can
be said to be the same program). Notice that a process is to a program much
like an object is to a class.

All the example code mentioned in this document is in the sub folder called
"creating_processes" from the following zip file.

* <http://cs.pnw.edu/~rlkraft/cs33600/for-class/streams_and_processes.zip>


## Process isolation

In Operating Systems you learn that each process runs in its own virtual
memory space. Virtual memory spaces were invented so that each process is
purposely walled off from every other process. You do not want a bug in one
process (say an instance of Word) to cause the crash of another process (say
an instance of Firefox). You also do not want one process (say an instance
of Firefox) to be able to look at, and read, the memory of another process
(say Word) and report back over the Internet what you are typing in your
Word documents. So for stability and security reasons, processes are
strictly isolated from each other (each in its own virtual memory space).

if processes are strictly isolated from each other, they cannot communicate,
or cooperate, with each other. That is too severe a restriction (think of how
useful copy and paste is, among many other ways in which programs communicate
with each other). So operating systems provide tightly controlled mechanisms
for processes to communicate (and therefore cooperate) with each other. These
mechanisms are referred to as **Inter-process Communication**.

* <https://en.wikipedia.org/wiki/Process_isolation>

If you would like to review the idea of a process, here are explanations
from two online operating systems textbooks.

* <https://pages.cs.wisc.edu/~remzi/OSTEP/cpu-intro.pdf>
* <https://pages.cs.wisc.edu/~remzi/OSTEP/cpu-mechanisms.pdf>
* <https://www.greenteapress.com/thinkos/thinkos.pdf#page=21>


## Inter-process Communication

**Inter-process Communication** (IPC) is when one process passes a piece
of data (a message) to another process. ALL forms of IPC involve the
operating system kernel acting as an intermediary in the passing of the
message (in order to guarantee the stability and security of the whole
system). In fact, every form of IPC involves the kernel copying a segment
of data (a buffer) from the virtual memory space of the sending process
into the virtual memory space of the receiving process.

* <https://en.wikipedia.org/wiki/Inter-process_communication>

There are seven simple mechanisms for IPC (and many complicated ones).
The simple IPC mechanisms are,

1. command-line arguments
2. environment variables
3. program exit code (return value)
4. files
5. I/O redirection
6. pipes
7. sockets

**Command-line arguments** can only be used by a parent process to pass
(in a one-way direction) a message to a child process (a child process
is a process that is started by the parent process). Also, this message
must be fairly small and it can only be sent at the time the child
process is created. So command-line arguments are a fairly restrictive
form of IPC (but they get used a lot).

**Environment variables** are similar to command-line arguments. They are
fairly small messages, they can only be passed in one direction (from
parent to descendant processes) and the messages must be sent at the
time a child process is created. The most significant difference
between environment variables and command-line arguments is that an
environment variable is also inherited by every descendant of the
original child process. So an environment variable can be used to send
a message to every descendant of the parent process. There are times
when this feature can be useful.

A program's **exit code** is a single integer value that can be used by a
child process to send a (very small) message back to its parent process.
This form of communication can only take place when the child terminates.
Exit codes are used mostly as error codes that let the parent know
something about the success or failure of a child process.

**Files** can be used by any two processes to communicate with each other
(more specifically, any two processes that have access to a shared
file system). In this sense, files are a very general form of IPC
(the messages can be as large as any file, the messages can be passed
in both directions, and the communicating processes need not even be
running on the same computer or at the same time), but it is a slow
form of IPC.

**I/O redirection** can be used by a parent to communicate with a child
while the child is running (not just when the child starts up). When
the parent creates the child, the parent can have the operating system
redirect one of the parent's output streams to the child's standard
input stream, and then redirect the child's standard output stream to
one of the parent's input streams. The amount of data that can be
communicated this way is unlimited and the data can be communicated
in both directions, from parent to child and from child to parent.
This is a common and useful form of IPC.

**Pipes** are a way for two sibling processes to communicate. Two processes
are siblings if they were created by the same parent processes. A pipe
must be created by the parent process and it must be created at the time
the two child processes are created. But otherwise, pipes are a very general
form of communication. The most common example of a parent process creating
a pipe between two child processes is when we use a command-line shell to
run two programs and we connect the two programs on the command-line with
the pipe ('|') symbol. The shell process is the parent process and the two
programs on the command-line become the child processes. The shell process
has the operating system create a pipe object, then redirect the standard
output of the first child process (the child that can send messages) to the
input of the pipe, then redirect the standard input of the second child
process (the child that will receive messages) to the output from the pipe.
We say that pipes are a form of "stream" communication, in the sense that
once the pipe is created, the two child processes can pass an unlimited
stream of bytes through the (one-directional) pipe. Pipes are fast (almost
as fast as writing and reading from physical memory). The biggest
restriction on pipes is that the two processes must be running on the same
computer.

**Sockets** are a way of providing stream communication between any two
processes running on the same or different computers (but connected
together by a network). Sockets are straightforward to use, they are
versatile, fairly fast, and they are now ubiquitous, every language
running on every operating system provides sockets.

The most important advanced form of IPC is **shared memory**. Shared memory
is when the operating system arranges for two processes (running on the same
computer) to share a physical page frame in their virtual memory page tables.
This means that the two processes can both read and write to the physical page
frame. This is a fast, bidirectional, symmetrical, form of IPC but it causes
many of the same problems that shared memory causes with threads (race
conditions). Shared memory is the basis for many other forms of IPC.


## Creating Processes

An important fact to remember is that *every* process running in an operating
system was created by the operating system kernel at the request of some other
process. When one process requests that the kernel create another process, the
requesting process is called the **parent process** and the newly created
process is called a **child process**.

Every operating system has a function (a "system call") that a process can
call to request that a child process be created. In the Linux operating
system, this system call is the `fork()` function. In the Windows operating
system this system call is the `CreateProcess()` function.

* <https://man7.org/linux/man-pages/man2/fork.2.html>
* <https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createprocessa>

When a parent process creates a child process, the parent has the ability
to communicate with that child process by using, as we mentioned above,
command-line parameters, environment variables, or I/O redirection. It is
common for the parent process to create the child process as a helper process
to do work for the parent process. Sometimes the parent and child work in
parallel (to speed up the parent's work) and sometimes the parent delegates
work to the child in a manner much like one method calling another method.

The operating system kernel keeps track of the parent/child relationship
for all running processes. It is instructive for you to observe this
relationship on your computer. To see the parent/child relationship on
Windows, you need to download and run the "ProcessExplorer.exe" program.

* <https://learn.microsoft.com/en-us/sysinternals/downloads/process-explorer>

This program does not need to be installed. You download a zip file, unzip it,
and then just run the executable "ProcessExplorer.exe" program that will be in
the unzipped folder. To see the parent/child relationships, be sure to select
the menu item "View -> Show Process Tree". Since a child process can become a
parent to another process, the parent/child relationship is in fact a family
tree.

You can also use the program "System Informer", which is an open source
alternative to "ProcessExplorer". You can download either an installer
for the program or a zip file (portable) distribution of the program.

* <https://systeminformer.sourceforge.io/>
* <https://github.com/winsiderss/systeminformer/>
* <https://systeminformer.sourceforge.io/downloads>



## Java `ProcessBuilder` class

If a programming language wants to let programmers write code to create
a process, then the programming language needs to create an interface to
the `fork()` function on Linux and the `CreateProcess()` function on
Windows. For example, the Java language has two interfaces to these
functions. Java's `java.lang.Runtime` class has the `exec()` method for
creating processes on either Linux or Windows. But the `Runtime` class is
not very versatile, so Java created a better way for Java programs to
create processes. Java's `java.lang.ProcessBuilder` class is a modern,
sophisticated way for Java programs to create processes on either Linux or
Windows.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Runtime.html">java.lang.Runtime</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ProcessBuilder.html">java.lang.ProcessBuilder</a></li>
</ul>

In the "creating_processes" folder there are Java programs that create processes.
Compile and run those programs. They show how a Java program can start up other
programs. Notice that some of these child processes are given command-line
parameters when they are started. For example, on Windows the "notepad.exe"
editor is started and it is told to open a file. Also, the Windows
"explorer.exe" program is told to open the folder "C:\Windows\System32".
This demonstrates a simple example of a parent process starting up a child
processes and communicating to the child processes a bit of (inter-process)
information.

To see the parent/child relationships created by these examples on Windows,
run the "ProcessExplorer.exe" program and be sure to select the menu item
"View -> Show Process Tree". You really should download "ProcessExplorer"
(or "System Informer", or both) and use it on your computer!

* <https://learn.microsoft.com/en-us/sysinternals/downloads/process-explorer>
* <https://systeminformer.sourceforge.io/downloads>


Here are some references for using the Java Process API.

* <https://docs.oracle.com/en/java/javase/21/core/process-api1.html>
* <https://link.springer.com/chapter/10.1007/978-1-4842-7135-3_11>
* <https://link.springer.com/content/pdf/10.1007/978-1-4842-7135-3_11>
* <https://www.baeldung.com/java-process-api>


## Creating Processes in JShell

An effective way to understand the Java code that creates processes is to
execute the code using the Java Shell program. The Java Shell (jshell.exe)
lets us run one line of Java code at a time. This gives us a way to experiment
with individual lines of Java code, to see the effect each line of code has.

* <https://dev.java/learn/jshell-tool/>
* <https://docs.oracle.com/en/java/javase/25/jshell/introduction-jshell.html>
* <https://www.oracle.com/a/ocom/docs/corporate/java-magazine-jul-aug-2017.pdf#page=29>
* <https://link.springer.com/content/pdf/10.1007/978-1-4842-7307-4_23>

Open a command-prompt window and on the command-line type the following command.

```text
   > jshell
```

You should see a response that looks something like this.

```text
|  Welcome to JShell -- Version 11.0.7
|  For an introduction type: /help intro

jshell>
```

At the `jshell` prompt, type the following three lines of Java code.

```text
    jshell> var pb = new ProcessBuilder("C:\\Windows\\system32\\charmap.exe")
    jshell> var p = pb.start()
    jshell> p
```

You should see an instance of the "Character Map" program running on your
desktop.

The first line of code uses a `ProcessBuilder` constructor to create a
`ProcessBuilder` object. The `String` parameter to the constructor should
be the name of a program on your computer. The second line of code uses the
`start()` method in the `ProcesBuilder` object to do two things. First, the
`start()` method asks the operating system (either Linux or Windows) to
create a running process from the program named in the `ProcessBuilder`
constructor (using either the `fork()` function on Linux, or the
`CreateProcess()` function on Windows). Second, if the process was started
successfully, then the `start()` method creates a Java `Process` object that
represents the operating system's running process.

The `toString()` method of the `Process` object `p` tells you the "process ID
number" (PID) of the running instance the `charmap.exe` program.

Exit (close) the "Character Map" program and then type the following line
of code in your JShell session.

```text
    jshell> p
```

The `toString()` method of `p` tells you that the "charmap.exe" program
is no longer running.

Once we have created a `ProcessBuilder` object, we can use it several
times to create multiple instances (processes) of the same program.

```text
    jshell> var p2 = pb.start()
    jshell> var p3 = pb.start()
    jshell> var p4 = pb.start()
```

Use the Windows "Task Manager" program to verify that there are three instances
of the "charmap.exe" program.

We can use a `Process` object to terminate the process that it represents. As
you execute the following lines, you should see the instances of `charmap.exe`
terminate (close).

```text
    jshell> p2.destroy()
    jshell> p3.destroy()
    jshell> p4.destroy()
```

You can use a `Process` object to wait for the termination of the process
that the object represents. Type the following two line of code into your
`jshell` prompt.

```text
    jshell> var p5 = pb.start()
    jshell> pb5.waitFor()
```

After you enter the second line, `jshell` should "freeze", because the
`jshell.exe` process is waiting for the `charmap.exe` process to terminate.
Use your mouse to terminate (close) the `charmap.exe` process. That should
free up the `jshell` prompt.

Type the following two lines of code into the JShell prompt.
(Important questions: What is the purpose of `\\`? Why not use `\`?)

```text
    jshell> var pb = new ProcessBuilder("C:\\Windows\\system32\\bob.exe")
    jshell> var p = pb.start()
```

There is no program named "bob.exe" in the folder "C:\Windows\system32",
so we get a "file not found" exception. But notice that the exception did
not happen when we created the `ProcessBuilder` object. The exception happened
when we tried to start the process. The `ProcessBuilder` constructor does not
check the validity of the filename it is given. The filename is not checked
until we try to start the process.

Type the following two lines of code into the JShell prompt.

```text
    jshell> var pb = new ProcessBuilder("C:\\Windows\\explorer.exe", "C:\\Users")
    jshell> var p = pb.start()
    jshell> pb.command()
```

The `String` parameters to the `ProcessBuilder` constructor are essentially
a Windows command-line. The `pb.command()` method returns a `String` array
holding the parts of the command-line, the program name and then its
command-line arguments. The above `ProcessBuilder` object is equivalent to
opening a command-prompt window and typing the following command-line
(try it).

```text
    > explorer  C:\Users
```


Open another command-prompt window and on the command-line start another
JShell session.

```text
   > jshell
```

When the `jshell` prompt appears, copy-and-paste the following block of code
into the prompt. JShell allows you to copy several lines of code at a time.
It will execute each line of code and then give you another prompt.

```java
var pb = new ProcessBuilder("cmd.exe", "/C", "dir")
pb.directory(new java.io.File("C:\\Users"))
var home = System.getenv("USERPROFILE")
var desktop = home + "\\Desktop\\"
pb.redirectOutput(new java.io.File(desktop + "temp.txt"))
var p = pb.start()
```

After this code executes, you should have a new file, called "temp.txt",
on your computer's desktop. The contents of the file is a directory
listing of your computer's "C:\\Users" folder.

Type the following additional line of code into your `jshell` prompt.

```java
p
```

The `toString()` method of `p` tells you that the "cmd.exe" program is
no longer running.

To remind ourselves what code we have typed into the `jshell` prompt, use
the JShell `list` command.

```text
    jshell> /list
```

If you want to see a list of all the JShell commands, use the following
command, where `<TAB>` means tap the `Tab` key on the keyboard.

```text
    jshell> /<TAB>
```

The `ProcessBuilder` class is an example of the **Builder design pattern**.
This is a software design pattern that is used in a lot in modern versions
of Java. Like the Factory pattern, the Builder pattern replaces the use of
constructors. If you look at the Javadoc for the `Process` class, you will
see that the class does not have a public constructor. The only way to create
a `Process` object is the call the `start()` method in a `ProcessBuilder`
object. The `ProcessBuilder` class controls how `Process` objects are
constructed and configured.

* <https://en.wikipedia.org/wiki/Builder_pattern>
* <https://www.digitalocean.com/community/tutorials/builder-design-pattern-in-java>
* <https://refactoring.guru/design-patterns/builder>

The `ProcessBuilder` class is also an example of a **fluent interface**.
In a fluent interface, the methods in a class return `this` so that we
can "chain" method calls. This allows us to write compact code like the
following example.

```java
Process p = new ProcessBuilder("cmd.exe", "/C", "dir")
                .redirectOutput(new File("output.txt"))
                .redirectError(new File("errors.txt"))
                .directory(new File("C:\\Users"))
                .start();
```

Each method call returns `this` which is a reference to the `ProcessBuilder`
object that we are constructing. The final `start()` method builds and returns
the `Process` object that we want. (In this example, the `ProcessBuilder`
object that we constructed is immediately garbage collected since we did not
create a reference to it.)

The old fashion alternative to a Builder pattern with a fluent interface is
a class with a lot of (overloaded) constructors that take many parameters
along with a lot of `set` methods that mutate the object. This old fashion,
traditional way to write code produces code that is harder to read than
modern code using a builder class with a fluent interface. Almost all
modern Java classes use builders with a fluent interface.

* <https://en.wikipedia.org/wiki/Fluent_interface>
* <https://www.martinfowler.com/bliki/FluentInterface.html>
* <https://www.baeldung.com/java-fluent-interface-vs-builder-pattern>
* <https://blog.jooq.org/the-java-fluent-api-designer-crash-course/>
