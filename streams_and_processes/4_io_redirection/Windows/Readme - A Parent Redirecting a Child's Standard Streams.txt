
   A Parent Redirecting a Child's Standard Streams

The examples in this folder (and the previous folder) show how a parent
process can control the standard streams (stdin, stdout, stderr) of a
child process.

There are two ways that the parent can control what a child's standard
streams are connected to when the child is created. The parent can either
have the child "inherit" the parent's standard streams or the parent can
redirect the child's streams to specific files.

The details of a child "inheriting" its parent's standard streams were
discussed in the previous folder. This folder shows how the parent can
redirect the child's standard streams to specific files.

(Notice that we are not yet talking about pipes. That is, we do not yet
want to connect the stdout of the parent to the stdin of the child. That
would be a pipe (so would connecting the stdout of the child to the stdin
of the parent). We will discus that in the next folder.)

What we show in this folder is how a parent can create a child with the
child's standard streams redirected to files, as in this picture.

                     parent
                +-----------------+
                |                 |
    keyboard -->> stdin    stdout >>-------> console
                |                 |
                |          stderr >>--->
                |                 |
                +-----------------+

                        child
                   +-----------------+
                   |                 |
    data.txt ----->>stdin     stdout >>----> result.txt
                   |                 |
                   |          stderr >>--->
                   |                 |
                   +-----------------+

In Windows, the operating system provides a way for the parent to create
the child in exactly the above situation by using the STARTUPINFO data
structure passed to the CreateProcess() function.

In Linux, the child process is first created by fork() with inherited
streams, as in this picture.

                     parent
                +-----------------+
                |                 |
           +--->> stdin    stdout >>-------+
           |    |                 |        |
           |    |          stderr >>--->   |
           |    |                 |        |
           |    +-----------------+        |
keyboard --+                               +-----> console
           |             child             |
           |       +-----------------+     |
           |       |                 |     |
           +------>> stdin    stdout >>----+
                   |                 |
                   |          stderr >>--->
                   |                 |
                   +-----------------+

After the fork() is complete the child will redirect its own streams
to the files (as in folder 5) before the child calls exec().



One of the most important uses of having a parent redirect it's
child's standard streams is the implementation of I/O redirection
in a command-line shell. A command-line like

> program1 < file1.txt > file2.txt

tells the shell process (either cmd.exe or bash) to run program1 with its
standard input being file1.txt and its standard output being file2.exe.
The shell process is the parent process and the program1 process is its
child process. The shell implements the I/O redirection operators, < and >,
using the techniques described in this folder.

                     shell
                +-----------------+
                |                 |
    keyboard -->> stdin    stdout >>------------> console
                |                 |
                |          stderr >>------+--->
                |                 |       |
                +-----------------+       |
                                          |
                        program1          |
                   +-----------------+    |
                   |                 |    |
   file1.txt ----->>stdin     stdout >>------> file2.txt
                   |                 |    |
                   |          stderr >>---+
                   |                 |
                   +-----------------+
