
   A Child Inheriting (or Sharing) Streams With Its Parent

The examples in this folder (and the next) show how a parent
process can control the standard streams (stdin, stdout, stderr)
of a child process.

There are two ways that the parent can control what a child's
standard streams are connected to when the child is created.
The parent can either have the child "inherit" the parent's
standard streams or the parent can redirect the child's streams
to specific files.

The details of how a parent can redirect the child's standard
streams to specific files will be discussed in the next folder.
The details of a child "inheriting" its parent's standard streams
will be discussed in this folder.

Let's explain what we mean by a child inheriting its parent's
standard streams.

When a child inherits a standard stream from its parent, the
parent and child processes "share" that stream. For example,
if a child inherits its parent's standard output stream, then
the parent and child share that output stream. On the other
hand, if a child inherits its parent's standard input stream,
then both processes share that one input stream. We now need
to explain what it means for processes to "share" a stream.

Here is an illustration of a parent and a child process sharing
their stdin and stdout streams.

                     parent
                +-----------------+
                |                 |
           +--->> stdin    stdout >>------+
           |    |                 |       |
           |    |          stderr >>--    |
           |    |                 |       |
           |    |                 |       |
           |    +-----------------+       |
     ------+                              +----->
           |                              |
           |             child            |
           |       +-----------------+    |
           |       |                 |    |
           +------>> stdin    stdout >>---+
                   |                 |
                   |          stderr >>--
                   |                 |
                   |                 |
                   +-----------------+

Input and output streams are not shared in the same way. So
we first look at shared output streams, and then consider
shared input streams.

When two processes share an output stream, what each process
writes to its output stream gets mixed together in the final
destination of the shared stream. If the shared output stream is
connected to a console window, then the lines of output from each
of the two processes will be combined in the console window, usually
in an unpredictable way. The same will happen if the shared output
stream is connected to a file.

On the other hand, if two processes share an input stream, then
both processes read data from that one stream. But two processes
cannot "simultaneously" read bytes from a stream. Instead, the
operating system must "serializes" their use of the input stream.
What this means is that whichever process calls the "read()" function
first, that process gets whatever bytes are in the current input
buffer for the shared stream. But the process that called "read()"
second is then in line to be the next process to get the next batch
of bytes that arrive in the stream's buffer. If both processes are
continuously reading from the stream, then they will probably end up
alternately reading input lines from whatever is the source of the
input stream. Notice that any specific byte in the input stream can
only be "read()" by one of the two processes. In the above picture
for shared input streams, incoming bytes do not get copied into
each of the two branches leading to the two processes. Any given
input byte flows down only one of the two branches (not both).


In Linux or Windows, a child process always inherits its parent's
standard streams (unless the parent redirects them; see the next
folder). In Linux or Windows, if the parent's stdin is the keyboard,
then so is the child's stdin. If the parent's stdout is the console
screen, then so is the child's stdout (and if the parent's stdout
is the file result.txt, then so is the child's stdout). This is very
convenient. In Linux or Windows, every child starts with inheriting
its parent's standard streams and if the parent chooses to, it can
redirect the child's standard streams to files.

But in Java, when a child process is created, its three standard
streams do not connect to anything! This is unlike Linux and Windows.
The fact that Java does not work this way is a real problem. See the
Java folder.


Here again is the illustration of a parent and a child process sharing
stdin and stdout. In this picture stdin is connected to the (shared)
keyboard.

                     parent
                +-----------------+
                |                 |
           +--->> stdin    stdout >>------+
           |    |                 |       |
           |    |          stderr >>--    |
           |    |                 |       |
           |    |                 |       |
           |    +-----------------+       |
keyboard---+                              +----->
           |                              |
           |             child            |
           |       +-----------------+    |
           |       |                 |    |
           +------>> stdin    stdout >>---+
                   |                 |
                   |          stderr >>--
                   |                 |
                   |                 |
                   +-----------------+

In this situation, there is a special case that we should consider.
The user at the keyboard denotes that they have no more input to type
by using the key combination ^Z on Windows (or ^D on Linux). Since only
one of the two processes can be the recipient of that character, only
one of the two processes sees an EOF condition (and probably exits).
What about the other process? The user would need to know that it is
necessary to enter a second ^Z (or ^D) from the keyboard in order to
send an EOF condition to the second process. You can actually see this
happen on Windows by using the program ParentChildShareStdin_parent.c
and Window's Task Manager. (What would happen if the two processes had
their shared stdin redirected to an input file? Would they both see the
EOF condition or just one of them?)


It is worth noting that usually, when two processes share a stream,
one of them is not actually using the stream. More often than not,
if a parent creates a child and has the child inherit its standard
streams, the parent will not use those streams while the child is
running. The parent will wait until the child is done (the child
process terminates) before the parent goes back to using its
standard streams.

The best, and most common, example of a parent and child sharing
streams is when we type a command at the cmd.exe (or bash) shell
prompt. For example, suppose we type the command

C:\>find "a"

The shell (cmd.exe) is the parent process and it tells the operating
system to creates a child process from the file "find.exe" (that file
is at C:\Windows\System32\find.exe) and the shell has that child process
share stdin and stdout with the shell process. Once find.exe starts,
cmd.exe stops using its (shared) copies of stdin and stdout and lets
find.exe use its copies. As you type at the keyboard, find.exe looks
for the letter "a" in stdin and echoes to stdout any input line that
contains an "a" (find is really a version of grep). When you type ^Z
at the beginning of a line, find.exe sees an EOF condition on its copy
of stdin, so it exits. But cmd.exe never sees the EOF condition (the ^Z).
When cmd.exe notices that find.exe has terminated, cmd.exe goes back to
writing and reading from stdout and stdin. It issues a new prompt on stdout,
and waits to read a new command on stdin. All in all, a very elegant way
for a user to make one program run other programs in a shared window.
