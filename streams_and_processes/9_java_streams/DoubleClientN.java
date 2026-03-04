/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads N binary double values from its standard input stream.
   The value of N is an optional command-line argument. The default value
   for N is 1 double value (8 bytes).
<p>
   The double values that are read from standard input are written as String
   values to the standard output stream.
*/
public class DoubleClientN
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

      final DataInputStream in = new DataInputStream(System.in);

      for (int j = 0; j < n; ++j)
      {
         // Read eight bytes and interpret them as a double value.
         final double d = in.readDouble(); // readDouble() can throw an IOException.
         System.out.println(d);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private DoubleClientN() {
      throw new AssertionError();
   }
}
