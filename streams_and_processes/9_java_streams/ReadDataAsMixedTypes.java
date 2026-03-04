/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as a short followed by a double followed
   by two bytes followed by an int.
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsMixedTypes  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsMixedTypes
*/
public class ReadDataAsMixedTypes
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final short  n1 = in.readShort();
      final double n2 = in.readDouble();
      final byte   n3 = in.readByte();
      final byte   n4 = in.readByte();
      final int    n5 = in.readInt();

      System.out.println(n1);
      System.out.println(n2);
      System.out.println(n3);
      System.out.println(n4);
      System.out.println(n5);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsMixedTypes() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html
