
This example shows how a program can get input from four different places,
  1) a file,
  2) an environment variable,
  3) a command-line argument,
  4) the program's standard input stream.

Any one of these can be used for "Inter-process Communication" (IPC).


The program UseIncrementer.java "calls" the Incrementer.java program
as if it were a kind of "remote function". The UseIncrementer process
passes numbers to the Incrementer process by writing the numbers into
Incrementer's standard input stream.

https://en.wikipedia.org/wiki/Remote_procedure_call


The Incrementer.java program is a filter program that reads integers
from its standard input stream and writes incremented integers to
its standard output stream.

The Incrementer.java program adds an increment value to each of the
input numbers in its standard input stream and "returns" the results
using its standard output stream. The Incrementer program gets its
incrementing value by looking in four different places.

1. First, Incrementer has a built in (default) increment value.
2. Second, Incrementer looks for an increment value in a properties file.
3. Third, Incrementer looks for an increment value as an environment variable.
4. Fourth, Incrementer looks for an increment value as a command-line argument.

In this example, since the UseIncrementer program calls Incrementer with
a command-line argument, the Incrementer process will find its increment
value that way and ignore the default value, the value in the properties
file, and any value in an environment variable.


When UseIncrementer and Incrementer are both running, here is a picture
of what all their streams look like. Notice that the UseIncrementer
process has two additional streams along with its three standard streams.

                      UseIncrementer
                +-----------------------+
                |                       |
   keyboard >-->> stdin          stdout >>-----+---> console window
                |                       |      |
                |                stderr >>-----+
                |                       |      |
                |     ps       scanner  |      |
                +----\ /---------/|\----+      |
                      |           |            |
                      |           |            |
               +------+           +------+     |
               |                         |     |
               |       Incrementer       |     |
               |    +---------------+    |     |
               |    |               |    |     |
               +--->> stdin  stdout >>---+     |
                    |               |          |
                    |        stderr >----------+
                    |               |
                    +---------------+

In UseIncrementer, the following line of code creates the new output
stream and connects it to the standard input stream of Incrementer.

   PrintStream ps = new PrintStream( p.getOutputStream() );

The following line of code creates the new input stream and connects
it to the standard output stream of Incrementer.

   Scanner scanner = new Scanner( p.getInputStream() );

Notice how confusing the method names are in the Process class.

We call getInputStream() to connect to a process's output stream
(but we are creating an input stream for the calling process).

We call getOutputStream() to connect to a process's input stream
(but we are creating an output stream for the calling process).

Every stream that connects two processes is both an input stream
and an output stream. It is an input stream to the process at one
end of the connection, and it is an output stream to the process
at the other end of the connection.
