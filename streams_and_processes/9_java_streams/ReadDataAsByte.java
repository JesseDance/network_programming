/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input.
<p>

   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsByte  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsByte
*/
public class ReadDataAsByte
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final byte[] b = new byte[16]; // Try 17.

      for (int i = 0; i < b.length; ++i)
      {
         b[i] = in.readByte(); // readByte() can throw an IOException.
      }

      for (int i = 0; i < b.length; ++i)
      {
         System.out.println(b[i]);           // print in decimal
       //System.out.printf("%02X\n", b[i]);  // print in hexadecimal
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsByte() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readByte()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readByte()
