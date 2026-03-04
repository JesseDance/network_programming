/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as unsigned bytes.
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsByteUnsigned  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsByteUnsigned
*/
public class ReadDataAsByteUnsigned
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final int[] b = new int[16];     // Notice that we need to use int here.

      for (int i = 0; i < b.length; ++i)
      {
         b[i] = in.readUnsignedByte(); // Notice that unsigned bytes become ints.
      }

      for (int i = 0; i < b.length; ++i)
      {
         System.out.println(b[i]);
       //System.out.printf("%02X\n", b[i]);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsByteUnsigned() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readUnsignedByte()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readUnsignedByte()
