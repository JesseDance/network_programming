/*

*/

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
   The program "calls" Adder.class as an external process.
   This is a baby example of parallel programming, or a very
   simplified example of how a "remote procedure call" might work.
*/
public class UseAdder
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      // Build a command-line string for ProcessBuilder.
      final List<String> childArgs = new ArrayList<>();
      childArgs.add("java");
      childArgs.add("Adder");
      // Put the command-line arguments of this process
      // on the command-line for the child process.
      for (final String arg : args)
      {
         childArgs.add(arg);
      }

      // Create a ProcessBuilder object for running Adder.class.
      final ProcessBuilder pb = new ProcessBuilder( childArgs );

      // "call" the Adder program.
      final Process p = pb.start();
      // Wait for the "called" process to return.
      p.waitFor();

      // Read the result from the Adder process's standard output stream.
      // Create a new input stream to read data from the output stream
      // of the Adder process.
      final Scanner scanner = new Scanner( p.getInputStream() );
      final int result = scanner.nextInt();

      // Write the result to stdout.
      System.out.println( result );
   }
}
