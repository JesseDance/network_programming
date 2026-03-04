
   I/O Redirection using ProcessBuilder

When a process is created by the operating system, the
process is always supplied with three open streams.

These three streams are called the "standard streams".
They are
   standard input  (stdin)
   standard output (stdout)
   standard error  (stderr)

We can visualize a process as an object with three "connections"
where data (bytes) can either flow into the process or flow out
from the process.

                    process
              +-----------------+
              |                 |
         ---->> stdin    stdout >>------
              |                 |
              |          stderr >>------
              |                 |
              +-----------------+

A console application will usually have its stdin stream connected
to the computer's keyboard and its stdout and stderr streams connected
to the console window.

                     process
               +-----------------+
               |                 |
  keyboard >-->> stdin    stdout >>------+---> console window
               |                 |       |
               |          stderr >>------+
               |                 |
               +-----------------+

It is important to realize that the above picture is independent of
the programming language used to write the program which is running
in the process. Every process looks like this. It is up to each
programming language to allow programs, written in that language, to
make use of this setup provided by the operating system.


Every process is created by the operating system at the request of some
other process (the parent process). When the parent process asks the
operating system to create a child process, the parent tells the
operating system how to "connect" the child's three standard streams.

At a shell command prompt, if you type a command like this,

   > foo

the shell process (cmd.exe on Windows, or bash on Linux) is the parent
process. The above command tells the shell process to ask the operating
system to create a child process from the foo program and have the foo
process share all three of its standard streams with the shell process.

                                shell
                         +-----------------+
                         |                 |
    keyboard >----+----->> stdin    stdout >>-----+----> console window
                  |      |                 |      |
                  |      |          stderr >>-----+
                  |      |                 |      |
                  |      +-----------------+      |
                  |                               |
                  |              foo              |
                  |      +-----------------+      |
                  |      |                 |      |
                  +----->> stdin    stdout >>-----+
                         |                 |      |
                         |          stderr >>-----+
                         |                 |
                         +-----------------+


At a shell command prompt, if you type a command like this,

   > foo < data.txt

the shell process will ask the operating system to create a child
process from the foo program. But in addition, the shell process
will instruct the operating system to redirect the child process's
standard input stream to the file data.txt. The stdout and stderr
streams will have their default connections. When foo runs, its
stream connections will look like this.

                  foo process
               +-----------------+
               |                 |
 data.txt >--->> stdin    stdout >>------+---> console window
               |                 |       |
               |          stderr >>------+
               |                 |
               +-----------------+

If the file data.txt does not exist, this command-line will fail.


If you type a command like this,

   > foo > results.txt

the shell process will ask the operating system to redirect the
child process's standard output to the file results.txt. Stdin
and stderr will have their default connections.

                  foo process
               +-----------------+
               |                 |
 keyboard >--->> stdin    stdout >>----> results.txt
               |                 |
               |          stderr >>----> console window
               |                 |
               +-----------------+

If the file results.txt does not exist, then the operating system
will create an empty file with that name before the child process
runs. If the file results.txt already exists, then the operating
system will empty it of its contents before the child process runs
(so all the data previously in results.txt will be lost).


If you type a command like this,

   > foo > results.txt < data.txt

the shell process will ask the operating system to redirect the
child process's standard input to the file data.txt and redirect
the child process's standard output to the file results.txt.
Stderr will have its default connection.

                  foo process
               +-----------------+
               |                 |
 data.txt >--->> stdin    stdout >>----> results.txt
               |                 |
               |          stderr >>----> console window
               |                 |
               +-----------------+

The above command-line is equivalent to the following one.
The order of the redirections does not matter.

   > foo < data.txt > results.txt


If you type a command like this,

   > foo >> results.txt

the shell process will ask the operating system to redirect the child
process's standard output to the *end* of the file results.txt. This
is called the "append" redirection operator. The data presently in the
file results.txt is preserved. New data is written to the file after
the data already in the file. Stdin and stderr will have their default
connections.


If you type a command like this,

   > foo 2> errors.txt

the shell process will ask the operating system to redirect the
child process's standard error to the file errors.txt. Stdin
and stdout will have their default connections.

                  foo process
               +-----------------+
               |                 |
 keyboard >--->> stdin    stdout >>----> console window
               |                 |
               |          stderr >>----> errors.txt
               |                 |
               +-----------------+


If you type a command like this,

   > foo < data.txt > results.txt 2> errors.txt

the shell process will ask the operating system to redirect
all three of the child's standard streams to files.

                  foo process
               +-----------------+
               |                 |
 data.txt >--->> stdin    stdout >>----> results.txt
               |                 |
               |          stderr >>----> errors.txt
               |                 |
               +-----------------+


If you type a command like this,

   > foo < data.txt > results.txt  2>&1

the shell process will ask the operating system to redirect the
child process's standard input to the file data.txt and redirect
the child process's standard output to the file result.txt. The
shell will also ask the operating system to redirect the child's
stderr to the same place as the child's stdout, in this case,
the file results.txt.

Look carefully at how the syntax of this command-line is similar
to the previous command-line. The '&1' (standard stream number 1)
is the target of '2>' instead of the file errors.txt.

                  foo process
               +-----------------+
               |                 |
 data.txt >--->> 0             1 >>----+----> results.txt
               |                 |     |
               |               2 >>----+
               |                 |
               +-----------------+


The files in this folder show how to use Java's ProcessBuilder
class to implement the shell's I/O redirection operators.

   <
   >
   >>
   2>
   2>>
   2>&1

Here is a summary of the example code.

var pb = new ProcessBuilder("myProgram")
             .redirectInput(new File("input.txt"))                      //  < input.txt
             .redirectOutput(new File("output.txt"))                    //  > output.txt
             .redirectOutput(Redirect.appendTo(new File(output.txt")))  // >> output.txt
             .redirectError(new java.io.File("errors.txt"))             // 2> errors.txt
             .redirectError(Redirect.appendTo(new File(errors.txt")))   // 2>>errors.txt
             .redirectErrorStream(true)                                 // 2>&1
