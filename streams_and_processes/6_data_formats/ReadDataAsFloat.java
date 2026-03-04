/*

*/

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as four floats (each float is four bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsFloat  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsInt
*/
public class ReadDataAsFloat
{
   public static void main(String[] args)throws IOException
   {
      // Read four floats.
      for (int i = 0; i < 4; ++i)
      {
         // These are really bytes, not ints.
         final int b1 = System.in.read(); // read() can throw an IOException.
         final int b2 = System.in.read();
         final int b3 = System.in.read();
         final int b4 = System.in.read();

         // Convert four bytes into a float value.
         final byte[] bytes = {(byte) b1,
                               (byte) b2,
                               (byte) b3,
                               (byte) b4};
         final float n = ByteBuffer.wrap(bytes).getFloat();
         System.out.println(n);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsFloat() {
      throw new AssertionError();
   }
}
