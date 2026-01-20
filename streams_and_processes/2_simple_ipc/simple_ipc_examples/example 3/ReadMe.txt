
This example shows how a program can get input from four different places,

1) a command-line argument
2) an environment variable
3) the program's standard input stream.
4) a file

Any one of these can be used for "inter-process communication" (IPC).


The program UseAdder.java "calls" the Adder.java program as if it
were a kind of "remote function". The UseAdder process passes
parameters to the Adder process by the environment variable method
of inter-process communication (UseAdder.java could be rewritten
to use either command-line argument IPC or file base IPC).

https://en.wikipedia.org/wiki/Remote_procedure_call
