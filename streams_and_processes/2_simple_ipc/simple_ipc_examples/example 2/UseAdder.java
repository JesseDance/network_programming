/*
   The program "calls" Adder.class as an external process.
   This is a baby example of parallel programming, or a very
   simplified example of how a "remote procedure call" might work.
*/
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class UseAdder
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      // Build a command-line string for ProcessBuilder.
      List<String> childArgs = new ArrayList<String>();
      childArgs.add("java");
      childArgs.add("Adder");
      // Put the command-line arguments of this process
      // on the command-line for the child process.
      for (int i = 0; i < args.length; i++)
      {
         childArgs.add(args[i]);
      }

      // Create a ProcessBuilder object for running Adder.class.
      ProcessBuilder pb = new ProcessBuilder( childArgs );

      // "call" the Adder program.
      Process p = pb.start();
      // Wait for the "called" process to return.
      p.waitFor();

      // Read the result from the Adder process's standard output.
      // Create a new stream to read data from the output of the
      // Adder process.
      Scanner scanner = new Scanner( p.getInputStream() );
      int result = scanner.nextInt();

      // write the result to stdout
      System.out.println( result );
   }
}
