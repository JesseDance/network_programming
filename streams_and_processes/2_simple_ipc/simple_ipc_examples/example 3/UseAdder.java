/*

*/

import java.util.Scanner;
import java.io.IOException;

/**
   The program "calls" Adder.class as an external process.
   This is a baby example of parallel programming, or a very
   simplified example of how a "remote procedure call" might work.
*/
public class UseAdder
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      // Get two command-line arguments for this process.
      int n1 = 0;
      int n2 = 0;
      if (args.length >= 2)
      {
         try
         {
            n1 = Integer.parseInt(args[0]);
            n2 = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e)
         {
            System.err.println("Usage: UseAdder <number> <number>");
            System.exit(1);
         }
      }
      else // There are not two command-line arguments.
      {
         System.err.println("Usage: UseAdder <number> <number>");
         System.exit(1);
      }

      // Create a ProcessBuilder object for running Adder.class
      final ProcessBuilder pb = new ProcessBuilder("java", "Adder"); // Try using "Adder2".

      // Pass arguments to Adder using environment variables.
      pb.environment().put( "adder_n1", String.valueOf(n1) );
      pb.environment().put( "adder_n2", String.valueOf(n2) );

      // "call" the Adder program.
      final Process p = pb.start();
      // Wait for the "called" process to return.
      p.waitFor();

      // Read the result from the Adder process's standard output stream.
      // Create a new input stream to read data from the output stream
      // of the Adder process.
      final Scanner scanner = new Scanner( p.getInputStream() );
      int result = scanner.nextInt();

      // Write the result to stdout.
      System.out.println( result );
   }
}
