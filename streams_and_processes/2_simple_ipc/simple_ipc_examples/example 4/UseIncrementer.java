/*

*/

import java.util.Scanner;
import java.io.PrintStream;
import java.io.IOException;

/**
   The program "calls" Incrementer.class as an external process.
   This is a baby example of parallel programming, or a very
   simplified example of how a "remote procedure call" might work.
<p>
   This program's first command-line argument is passed as a
   command-line argument to Incrementer as its incrementing
   value. The rest of this program's command-line arguments
   are passed to Incrementer in its standard input stream.
   Incrementer's results are read from its standard output
   stream and printed to this program's standard output stream.
*/
public class UseIncrementer
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      if (args.length < 2) // There are not at least two command-line arguments.
      {
         System.err.println("Usage: UseIncrementer <increment> <number>...");
         System.exit(1);
      }

      // Get the first command-line argument.
      int inc = 0;
      try
      {
         inc = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e)
      {
         System.err.println("Usage: UseIncrementer <increment> <number>...");
         System.exit(1);
      }

      // Create a ProcessBuilder object for running Incrementer.class
      final ProcessBuilder pb = new ProcessBuilder("java",
                                                   "Incrementer",
                                                   "" + inc);

      // "call" the Adder program.
      final Process p = pb.start();

      // Put the rest of the command-line arguments of this process
      // in the standard input stream the child process.
      // Create a new output stream to send data to the input stream
      // of the Incrementer process.
      final PrintStream ps = new PrintStream( p.getOutputStream() );
      for (int i = 1; i < args.length; ++i)
      {
         ps.println( args[i] );
      }
      ps.close();

      // Wait for the "called" process to return.
      p.waitFor();

      // Read the results from the Incrementer process's standard output stream.
      // Create a new input stream to read data from the output stream
      // of the Incrementer process.
      final Scanner scanner = new Scanner( p.getInputStream() );
      while ( scanner.hasNextInt() )
      {
         System.out.println( scanner.nextInt() );
      }
   }
}

/*
Here is a picture of all the streams that this program uses.

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
*/
