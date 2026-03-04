
This example shows how one process can "call" another process
and have the "called" process do a calculation and then return
a result to the "calling" process.

The program UseAdder.java "calls" the Adder.java program as
if it were a kind of "remote function". The UseAdder process
passes parameters to the Adder process by the command-line
argument method of inter-process communication. The Adder
process passes its result back to UseAdder using its standard
output stream.

UseAdder gets Adder's return value by calling the getInputStream()
method on the Process object that represents the Adder process.
That lets the UseAdder process read as input what the Adder process
wrote as output to its standard output stream. This is a simple form
of I/O redirection (the I/O redirection is done after the process
terminates!).

https://en.wikipedia.org/wiki/Remote_procedure_call
