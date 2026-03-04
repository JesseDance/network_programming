/*

*/

import java.io.DataInputStream;
import java.io.IOException;

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
   > java CreateData | java ReadDataAsFloat
*/
public class ReadDataAsFloat
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final float n1 = in.readFloat(); // readFloat() can throw an IOException.
      final float n2 = in.readFloat();
      final float n3 = in.readFloat();
      final float n4 = in.readFloat();

      System.out.println(n1);
      System.out.println(n2);
      System.out.println(n3);
      System.out.println(n4);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsFloat() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readFloat()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readFloat()
