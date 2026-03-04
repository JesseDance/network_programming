/*

*/

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
   This program writes N binary float values to its standard output stream.
   The value of N is an optional command-line argument. The default value
   for N is 1 float value (4 bytes).
<p>
   The float values that are written to standard output are read from the
   standard input stream as String values.
<p>
   Try running this program with this command-line, so you can see
   what bytes are written by this program.
<p>
       > java FloatServerN 4 | java -cp filters.jar  HexDump 4
<p>
   Also run this program with its companion client program.
<p>
       > java FloatServerN 4 | java FloatClient 4
       > java FloatServerN 4 | java FloatClient 1
       > java FloatServerN 1 | java FloatClient 4
<p>
   Be sure you can explain the results from the last two command-lines.
*/
public class FloatServerN
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
         final float d = in.nextFloat();
         out.writeFloat(d); // writeFloat() can throw an IOException.
         out.flush();
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private FloatServerN() {
      throw new AssertionError();
   }
}
