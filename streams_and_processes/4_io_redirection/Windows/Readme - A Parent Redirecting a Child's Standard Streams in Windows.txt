
   A Parent Redirecting a Child's Standard Streams in Windows

The examples in this folder show how a parent Windows process
can redirect the standard streams (stdin, stdout, stderr) of a
child process.

In Windows it is fairly straightforward for a parent to create a
child process and redirect the child's stdin and stdout to files.
It takes quite a few Windows function calls, some of them not at
all obvious (like making the handles to the files "inheritable"),
but the individual steps are easy to understand.

Unlike in Linux, the redirection of the child's streams is done
before the child process is created (by using the STARTUPINFO
data structure passed to the operating system's CreatProcess()
function). So as soon as the child process starts running, it
is using its redirected streams.

To redirect the child's stdin and stdout streams the parent process
needs to do (at least) these six steps.

   1.) Ask the OS to create an input file handle.
   2.) Ask the OS to create an output file handle.
   3.) Redirect the child's stdin to the input file.
   4.) Redirect the child's stdout to the output file.
   5.) Ask the OS to create the child process.
   6.) Close the parent's handles to the input and output files.

Here are some pictures that explain the steps in redirecting the
child's stdin and stdout streams. Start with a parent process that
has the keyboard as its stdin and the console window as its stdout
and stderr.
                  parent
             +----------------+
             |                |
 keyboard -->> stdin   stdout >>--------> console window
             |                |
             |         stderr >>-----> console window
             |                |
             +----------------+

The parent needs to open the two files that will be used by the
child. So the parent needs to create two new streams, an input
stream and an output stream. In Windows the two new streams are
represented by "handles" and we can call these new handles hIn
and hOut.
                  parent
             +----------------+
             |                |
 keyboard -->> stdin   stdout >>--------> console window
             |                |
             |         stderr >>-----> console window
             |                |
 double.c -->> hIn       hOut >>-----> result.txt
             |                |
             +----------------+

Once these files have been opened, the parent can create the child
and have the child inherit these two new streams as its stdin and
stdout and inherit the parent's stderr (this is done using the
STARTUPINFO data structure). This will mean that, at least for a
short while, the parent and child will be sharing the streams to
the two files.

                  parent
              +----------------+
              |                |
 keyboard --->> stdin   stdout >>-------------------------> console window
              |                |
              |         stderr >>--------------------+--> console window                |
              |                |                     |
              |                |                     |
double.c -+-->> hIn       hOut >>----+-> result.txt  |
          |   |                |     |               |
          |   +----------------+     |               |
          |                          |               |
          |           child          |               |
          |     +-----------------+  |               |
          |     |                 |  |               |
          +---->> stdin    stdout >>-+               |
                |                 |                  |
                |          stderr >>-----------------+
                |                 |
                +-----------------+

But the parent does not want to use the two file streams. Those two
streams are for the child process. So the parent process should close
its two handles to the files. That gives us the configuration that we
want.
                  parent
             +----------------+
             |                |
 keyboard -->> stdin   stdout >>----------------------> console window
             |                |
             |         stderr >>-------------------+--> console window
             |                |                    |
             +----------------+                    |
                                                   |
                     child                         |
               +-----------------+                 |
               |                 |                 |
  double.c --->> stdin    stdout >>---> result.txt |
               |                 |                 |
               |          stderr >>----------------+
               |                 |
               +-----------------+



Be sure to run CreateChildRedirectStdinStdout.exe with its
standard input and output streams redirected to files.

   C:\> CreateChildRedirectStdinStdout.exe < Readme.txt > something.txt

Notice that the redirection of the parent's streams should not
have any affect on the redirection of the child's streams.


             CreateChildRedirectStdinStdout.exe
                +----------------+
                |                |
  Readme.txt -->> stdin   stdout >>-------> something.txt
                |                |
                |         stderr >>--->
                |                |
                +----------------+

                      double.exe
                   +-----------------+
                   |                 |
    double.c ----->> stdin    stdout >>------> result.txt
                   |                 |
                   |          stderr >>--->
                   |                 |
                   +-----------------+



Exercise: A somewhat convoluted way for a parent to redirect its child's
standard streams is for the parent to first redirect its own standard
streams (as in folder 5), then have the child inherit the parent's
standard streams (as in folder 6), and then have the parent restore its
standard streams to where they originally were.

Write a Windows program that redirects its stdin to a file (say double.c),
redirects its stdout to another file (say result.txt), creates a child
(say double.exe) that inherits the redirected streams from the parent, and
then restores the parent's stdin and stdout to what they were when the
program started up. Your program will need to keep copies of the stdin and
stdout handles that the program began with and use the copies to restore the
stdin and stdout handles back to what they were. Have your program wait for
the child to end and then have your program read and write from its stdin
and stdout to make sure that they have been properly restored.

Run your program with its stdin and stdout redirected like this,

C:\> myProgram < Readme.txt > soemthing.txt

to make doubly sure that your program is redirecting the child to the files
double.c and result.txt and that your program then restores is own stdin and
stdout to their original streams.

