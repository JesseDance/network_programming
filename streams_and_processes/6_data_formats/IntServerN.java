/*

*/

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
   This program writes N binary int values to its standard output stream.
   The value of N is an optional command-line argument. The default value
   for N is 1 int value (4 bytes).
<p>
   The int values that are written to standard output are read from the
   standard input stream as String values.
<p>
   Try running this program with this command-line, so you can see
   what bytes are written by this program.
<p>
       > java IntServerN 4 | java -cp filters.jar  HexDump 4
<p>
   Also run this program with its companion client program.
<p>
       > java IntServerN 4 | java IntClient 4
       > java IntServerN 4 | java IntClient 1
       > java IntServerN 1 | java IntClient 4
<p>
   Be sure you can explain the results from the last two command-lines.
*/
public class IntServerN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int n = 1; // Default value for n.

      // Check for an optional command-line argument.
      if (args.length >= 1)
      {
         try
         {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      final Scanner in = new Scanner(System.in);
      for (int j = 0; j < n; ++j)
      {
         final int i = in.nextInt();
         byte[] bytes = ByteBuffer.allocate(Integer.BYTES)
                                  .putInt(i)
                                  .array();
         System.out.write(bytes[0]);
         System.out.write(bytes[1]);
         System.out.write(bytes[2]);
         System.out.write(bytes[3]);
         System.out.flush(); // Try commenting this out.
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private IntServerN() {
      throw new AssertionError();
   }
}
