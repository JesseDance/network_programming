/*

*/

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as two doubles (each double is eight bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsDouble  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsInt
*/
public class ReadDataAsDouble
{
   public static void main(String[] args)throws IOException
   {
      // Read two doubles.
      for (int i = 0; i < 2; ++i)
      {
         // These are really bytes, not ints.
         final int b1 = System.in.read(); // read() can throw an IOException.
         final int b2 = System.in.read();
         final int b3 = System.in.read();
         final int b4 = System.in.read();
         final int b5 = System.in.read();
         final int b6 = System.in.read();
         final int b7 = System.in.read();
         final int b8 = System.in.read();

         // Convert eight bytes into a double value.
         final byte[] bytes = {(byte) b1,
                               (byte) b2,
                               (byte) b3,
                               (byte) b4,
                               (byte) b5,
                               (byte) b6,
                               (byte) b7,
                               (byte) b8};
         final double n = ByteBuffer.wrap(bytes).getDouble();
         System.out.println(n);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsDouble() {
      throw new AssertionError();
   }
}
