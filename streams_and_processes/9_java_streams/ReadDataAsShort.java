/*

*/

import java.io.DataInputStream;
import java.io.IOException;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as eight shorts (each short is two bytes).
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsShort  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsShort
*/
public class ReadDataAsShort
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final short n1 = in.readShort(); // readShort() can throw an IOException.
      final short n2 = in.readShort();
      final short n3 = in.readShort();
      final short n4 = in.readShort();
      final short n5 = in.readShort();
      final short n6 = in.readShort();
      final short n7 = in.readShort();
      final short n8 = in.readShort();

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
   private ReadDataAsShort() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readShort()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readShort()
