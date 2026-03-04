/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as four ints (each int is four bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsInt  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsInt
*/
public class ReadDataAsInt
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final int n1 = in.readInt(); // readInt() can throw an IOException.
      final int n2 = in.readInt();
      final int n3 = in.readInt();
      final int n4 = in.readInt();

      System.out.println(n1);
      System.out.println(n2);
      System.out.println(n3);
      System.out.println(n4);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsInt() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readInt()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readInt()
