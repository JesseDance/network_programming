/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as 16 booleans (each boolean is one byte).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsBoolean  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsBoolean
*/
public class ReadDataAsBoolean
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final boolean[] b = new boolean[16]; // Try 17.

      for (int i = 0; i < b.length; ++i)
      {
         b[i] = in.readBoolean(); // readBoolean() can throw an IOException.
      }

      for (int i = 0; i < b.length; ++i)
      {
         System.out.println( b[i] );
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsBoolean() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readBoolean()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readBoolean()
