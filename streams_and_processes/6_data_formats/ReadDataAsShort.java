/*

*/

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as eight shorts (each short is two bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsShort  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsInt
*/
public class ReadDataAsShort
{
   public static void main(String[] args)throws IOException
   {
      // Read eight shorts.
      for (int i = 0; i < 8; ++i)
      {
         // These are really bytes, not ints.
         final int b1 = System.in.read(); // read() can throw an IOException.
         final int b2 = System.in.read();

         // Convert two bytes into a short value.
         final byte[] bytes = {(byte) b1,
                               (byte) b2};
         final short n = ByteBuffer.wrap(bytes).getShort();
         System.out.println(n);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsShort() {
      throw new AssertionError();
   }
}
