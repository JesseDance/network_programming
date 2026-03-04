/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as eight unsigned shorts (each unsigned
   short is two bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsShortUnsigned  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsShortUnsigned
*/
public class ReadDataAsShortUnsigned
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final int n1 = in.readUnsignedShort(); // Notice that unsigned shorts become ints.
      final int n2 = in.readUnsignedShort();
      final int n3 = in.readUnsignedShort();
      final int n4 = in.readUnsignedShort();
      final int n5 = in.readUnsignedShort();
      final int n6 = in.readUnsignedShort();
      final int n7 = in.readUnsignedShort();
      final int n8 = in.readUnsignedShort();

      System.out.println(n1);
      System.out.println(n2);
      System.out.println(n3);
      System.out.println(n4);
      System.out.println(n5);
      System.out.println(n6);
      System.out.println(n7);
      System.out.println(n8);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsShortUnsigned() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readUnsignedShort()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readUnsignedShort()
