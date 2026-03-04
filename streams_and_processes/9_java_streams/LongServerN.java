/*

*/

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
   This program writes N binary long values to its standard output stream.
   The value of N is an optional command-line argument. The default value
   for N is 1 long value (8 bytes).
<p>
   The long values that are written to standard output are read from the
   standard input stream as String values.
<p>
   Try running this program with this command-line, so you can see
   what bytes are written by this program.
<p>
       > java LongServerN 4 | java -cp filters.jar  HexDump 8
<p>
   Also run this program with its companion client program.
<p>
       > java LongServerN 4 | java LongClient 4
       > java LongServerN 4 | java LongClient 1
       > java LongServerN 1 | java LongClient 4
<p>
   Be sure you can explain the results from the last two command-lines.
*/
public class LongServerN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args) throws IOException
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

      final DataOutputStream out = new DataOutputStream(System.out);
      final Scanner in = new Scanner(System.in);

      for (int j = 0; j < n; ++j)
      {
         final long i = in.nextLong();
         out.writeLong(i); // writeLong() can throw an IOException.
         out.flush();
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private LongServerN() {
      throw new AssertionError();
   }
}
