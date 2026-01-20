
This example shows how one process can "call" another process
and have the "called" process do a calculation and then return
a result to the "calling" process.

The program UseAdder.java "calls" the Adder.java program as
if it were a kind of "external function". The UseAdder process
passes parameters to the Adder process by the command-line
method of inter-process communication. The Adder process passes
its result back to UseAdder using its standard output stream.
UseAdder calls a method that lets it read as input what Adder
wrote as output to its standard output stream. This is a simple
form of I/O redirection.
