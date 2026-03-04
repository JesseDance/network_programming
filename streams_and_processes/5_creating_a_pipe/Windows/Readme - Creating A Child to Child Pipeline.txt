
   Creating A Child to Child Pipeline

In this folder we have a parent process create a two stage pipeline
between two child processes. This is exactly what a shell program
(like bash on Linux or cmd.exe on Windows) does when you use a pipe
symbol in a command line like this.

   > program_1 | program_2

Here are the steps that a shell program goes through to "execute"
this command line.

  1.) The shell program should create a pipe object.
  2.) The shell program should create two child processes,
      one each from the files program_1.exe and program_2.exe,
  3.) The stdin of program_1 should be inherited from the shell,
  4.) The stdout of program_2 should be inherited from the shell,
  5.) The stderr of both program_1 and program_2 should be inherited form the shell.
  6.) The stdout of program_1 should be redirected to the input of the pipe.
  7.) The stdin of program_2 should be redirected to the output of the pipe.

The correct order of these steps depends on the operating system,
but each step is needed by each operating system.


Here is a diagram of how the parent and its two child processes should
have their streams connected together.

                        shell
                  +----------------+
                  |                |
  >----+--------->> stdin   stdout >>---------------------------+---->
       |          |                |                            |
       |          |         stderr >>---------------------------+
       |          |                |                            |
       |          +----------------+                            |
       |                                                        |
       |       program_1                       program_2        |
       |   +----------------+              +----------------+   |
       |   |                |     pipe     |                |   |
       +-->> stdin   stdout >>--0======0-->> stdin   stdout >>--+
           |                |              |                |   |
           |         stderr >>---+         |         stderr >>--+
           |                |    |         |                |   |
           +----------------+    |         +----------------+   |
                                 |                              |
                                 +------------------------------+

Notice that the parent and child_1 processes share an input stream and the
parent and child_2 processes share an output stream. It will usually be the
case that the parent process will put itself to sleep until both of the child
processes are done so the parent will not interfere with the children's I/O
on the shared streams. It is important to realize that after both children
close their copies of the shared streams and then terminate, the parent can
go back to using its copies of the two shared streams. When the children close
their copies of the shared streams, that has no effect on the parent's copies
of those streams.

When child_1 sees the end-of-file condition on its stdin stream, child_1
closes its stdout stream. The operating system then causes the end-of-file
condition to become true for child_2's stdin stream (after child_2 has read
all the data from the pipe's buffer). The child_2 process then knows that it
can terminate.

If child_1 never closes its stdout stream, then child_2 (and the operating
system) will not know that there is no more data coming through the pipe and
child_2 will block (forever) waiting for child_1 to write something into the
pipe's buffer. And since the child_2 will be blocked but still running, the
parent process will stay blocked (forever) waiting for child_2 to terminate.
So the parent and child_2 will wait forever on each other. A situation like
this is called a "deadlock".
