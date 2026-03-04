/*

*/

import java.io.DataInputStream;
import java.io.IOException;

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
   > java CreateData | java ReadDataAsDouble
*/
public class ReadDataAsDouble
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final double d1 = in.readDouble(); // readDouble() can throw an IOException.
      final double d2 = in.readDouble();

      System.out.println(d1);
      System.out.println(d2);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsDouble() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readDouble()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readDouble()
