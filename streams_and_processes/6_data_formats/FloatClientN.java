/*

*/

import java.nio.ByteBuffer;
import java.io.IOException;

/**
   This program reads N binary float values from its standard input stream.
   The value of N is an optional command-line argument. The default value
   for N is 1 float value (4 bytes).
<p>
   The float values that are read from standard input are written as String
   values to the standard output stream.
*/
public class FloatClientN
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

      for (int j = 0; j < n; ++j)
      {
         final int b0 = System.in.read(); // read() can throw an IOException.
         final int b1 = System.in.read();
         final int b2 = System.in.read();
         final int b3 = System.in.read();
         final byte[] bytes = {(byte)b0,
                               (byte)b1,
                               (byte)b2,
                               (byte)b3};
         final float d = ByteBuffer.wrap(bytes)
                                   .getFloat();
         System.out.println(d);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private FloatClientN() {
      throw new AssertionError();
   }
}
