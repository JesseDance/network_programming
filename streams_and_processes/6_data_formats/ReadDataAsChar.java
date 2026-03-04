/*

*/

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as eight chars (each char is two bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsChar  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsInt
*/
public class ReadDataAsChar
{
   public static void main(String[] args)throws IOException
   {
      // Read eight chars.
      for (int i = 0; i < 8; ++i)
      {
         // These are really bytes, not ints.
         final int b1 = System.in.read(); // read() can throw an IOException.
         final int b2 = System.in.read();

         // Convert two bytes into a char value.
         final byte[] bytes = {(byte) b1,
                               (byte) b2};
         final char n = ByteBuffer.wrap(bytes).getChar();
         System.out.println(n);
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsChar() {
      throw new AssertionError();
   }
}
