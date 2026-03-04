
   A Parent Redirecting a Child's Standard Streams in Linux

The examples in this folder show how a parent Linux process
can redirect the standard streams (stdin, stdout, stderr) of
a child process.

Unlike in Windows, the redirection of the child's streams is done
after the child process is created. The redirection happens in the
child process after the fork() but before the child process execs
the "real" child program. After the fork, the child redirects its
own standard streams exactly as in folder 5. The child process can
use either of the three techniques for redirecting the standard
streams (close-open, or open-close-dup-close, or open-dup2-close).


Here are some pictures that explain the steps in redirecting the
child's stdin and stdout streams using the close-open technique.

Start with a parent process that has the keyboard as its stdin and
the console window as its stdout and stderr.

                   parent
              +------------+
              |            |
  keyboard--->> 0        1 >>---------> console window
              |            |
              |          2 >>------> console window
              |            |
              +------------+

The parent forks the child process which starts out inheriting
its parent's standard streams.

                     parent
                 +------------+
                 |            |
            +--->> 0        1 >>---------+-----> console window
            |    |            |          |
            |    |          2 >>----+----|---> console window
            |    |            |     |    |
            |    +------------+     |    |
 keyboard --+                       |    |
            |          child        |    |
            |       +------------+  |    |
            |       |            |  |    |
            +------>> 0        1 >>------+
                    |            |  |
                    |          2 >>-+
                    |            |
                    +------------+

The child calls close(0), so we get this.

                     parent
                 +------------+
                 |            |
   keyboard ---->> 0        1 >>---------+-----> console window
                 |            |          |
                 |          2 >>----+----|---> console window
                 |            |     |    |
                 +------------+     |    |
                                    |    |
                       child        |    |
                    +------------+  |    |
                    |            |  |    |
                    |          1 >>------+
                    |            |  |
                    |          2 >>-+
                    |            |
                    +------------+

The child calls open("double.c") which results in this (since
0 is now the child's lowest available file descriptor).

                     parent
                 +------------+
                 |            |
   keyboard ---->> 0        1 >>---------+-----> console window
                 |            |          |
                 |          2 >>----+----|---> console window
                 |            |     |    |
                 +------------+     |    |
                                    |    |
                       child        |    |
                    +------------+  |    |
                    |            |  |    |
       double.c --->> 0        1 >>------+
                    |            |  |
                    |          2 >>-+
                    |            |
                    +------------+

The child now calls close(1) so we get this.

                     parent
                 +------------+
                 |            |
   keyboard ---->> 0        1 >>--------------> console window
                 |            |
                 |          2 >>----+-------> console window
                 |            |     |
                 +------------+     |
                                    |
                       child        |
                    +------------+  |
                    |            |  |
       double.c --->> 0          |  |
                    |            |  |
                    |          2 >>-+
                    |            |
                    +------------+

The child calls open("result.txt") which results in this (since
1 is now the child's lowest available file descriptor).

                     parent
                 +------------+
                 |            |
   keyboard ---->> 0        1 >>--------------> console window
                 |            |
                 |          2 >>----+-------> console window
                 |            |     |
                 +------------+     |
                                    |
                       child        |
                    +------------+  |
                    |            |  |
       double.c --->> 0        1 >>-----> result.txt
                    |            |  |
                    |          2 >>-+
                    |            |
                    +------------+

And this is the configuration that we want. The child can now
call exec() to run the "real" child program.



Be sure to run create_child_redirect_stdin_stdout with its
standard input and output streams redirected to files.

   > create_child_redirect_stdin_stdout.exe < Readme.txt > something.txt

Notice that the redirection of the parent's streams should not
have any affect on the redirection of the child's streams.


             create_child_redirect_stdin_stdout
                +----------------+
                |                |
  Readme.txt -->> stdin   stdout >>-------> something.txt
                |                |
                |         stderr >>--->
                |                |
                +----------------+

                      double.exe
                   +----------------+
                   |                |
    double.c ----->> stdin   stdout >>------> result.txt
                   |                |
                   |         stderr >>--->
                   |                |
                   +----------------+



Exercise: A somewhat convoluted way for a parent to redirect its child's
standard streams is for the parent to first redirect its own standard
streams (as in folder 5), then have the child inherit the parent's
standard streams (as in folder 6), and then have the parent restore its
standard streams to where they originally were.

Write a Linux program that redirects its stdin to a file (say double.c),
redirects its stdout to another file (say result.txt), forks and execs a
child (say double.exe) that inherits the redirected streams from the parent,
and then restores the parent's stdin and stdout to what they were when the
program started up. Your program will need to make copies of the stdin and
stdout file descriptors that the program began with and use the copies to
restore stdin and stdout back to what they were. Have your program wait for
the child to end and then have your program read and write from its stdin
and stdout to make sure that they have been properly restored.

Run your program with its stdin and stdout redirected like this,

> myProgram < Readme.txt > something.txt

to make doubly sure that your program is redirecting the child to the files
double.c and result.txt and that your program then restores is own stdin and
stdout to their original streams.
