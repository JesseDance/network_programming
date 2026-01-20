/*
   The program "calls" Adder.class as an external process.
   This is a baby example of parallel programming, or a very
   simplified example of how a "remote procedure call" might work.
*/
import java.util.Scanner;
import java.io.*;

public class UseAdder
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      // Get two command-line arguments for this process.
      int x1 = 0;
      int x2 = 0;
      if (args.length >= 2)
      {
         try
         {
            x1 = Integer.parseInt(args[0]);
            x2 = Integer.parseInt(args[1]);
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
      ProcessBuilder pb = new ProcessBuilder("java", "Adder");

      // Pass arguments to Adder using environment variables.
      pb.environment().put( "adder-op1", new Integer(x1).toString() );
      pb.environment().put( "adder-op2", new Integer(x2).toString() );

      // "call" the Adder program.
      Process p = pb.start();
      // Wait for the "called" process to return.
      p.waitFor();

      // Read the result from the Adder process's standard output.
      // Create a new stream to read data from the output of the
      // Adder process.
      Scanner scanner = new Scanner( p.getInputStream() );
      int result = scanner.nextInt();

      // Write the result to stdout.
      System.out.println( result );
   }
}
