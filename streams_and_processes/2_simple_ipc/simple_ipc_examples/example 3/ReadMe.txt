
This example shows how a program can get input from three different places,
  1) a file,
  2) an environment variable,
  3) a command-line argument.

Any one of these can be used for "Inter-process Communication" (IPC).


The program UseAdder.java "calls" the Adder.java program as if it
were a kind of "remote function". The UseAdder process passes
parameters to the Adder process by the environment variable method
of inter-process communication (UseAdder.java could be rewritten
to use either command-line arguments or file based IPC).

https://en.wikipedia.org/wiki/Remote_procedure_call


The Adder.java program adds two numbers and "returns" the result
using its standard output stream. The Adder program gets the values
of its two input numbers by looking in four different places.

1. First, Adder has two built in (default) values.
2. Second, Adder looks for two values in a properties file.
3. Third, Adder looks for two values as environment variables.
4. Fourth, Adder looks for two values as command-line arguments.


In this example, since the UseAdder program writes two environment
variables, the Adder process will find its two numbers that way and
ignore the default values and the values in the properties file.

https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Properties.html
