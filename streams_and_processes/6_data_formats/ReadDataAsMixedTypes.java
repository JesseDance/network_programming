/*

*/

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   This program reads 16 bytes of data from
   standard input and interprets those bytes
   as a variety of data types.
<p>
   This program reads 2 bytes as a short,
   then it reads 8 bytes as a long,
   then it reads 2 bytes as a short,
   then it reads 4 bytes as an int.
<p>
   Run this program on the command-line and
   have it read the data file created with
   CreateData.java.
<p>
   > java  ReadDataAsMixedTpes  <  data
<p>
   Alternatively, you can pipe the output from
   CreateData directly into this program.
<p>
   > java CreateData | java ReadDataAsMixedTpes
*/
public class ReadDataAsMixedTypes
{
   public static void main(String[] args)throws IOException
   {
      // Read a short.
      final int b1 = System.in.read(); // read() can throw an IOException.
      final int b2 = System.in.read();
      // Convert two bytes into a short value.
      final byte[] bytes1 = {(byte) b1,
                             (byte) b2};
      final short s1 = ByteBuffer.wrap(bytes1).getShort();
      System.out.println(s1);

      // Read a long.
      final int b3 = System.in.read();
      final int b4 = System.in.read();
      final int b5 = System.in.read();
      final int b6 = System.in.read();
      final int b7 = System.in.read();
      final int b8 = System.in.read();
      final int b9 = System.in.read();
      final int b10 = System.in.read();
      // Convert eight bytes into a long value.
      final byte[] bytes2 = {(byte) b3,
                             (byte) b4,
                             (byte) b5,
                             (byte) b6,
                             (byte) b7,
                             (byte) b8,
                             (byte) b9,
                             (byte) b10};
      final long n1 = ByteBuffer.wrap(bytes2).getLong();
      System.out.println(n1);

      // Read a short.
      final int b11 = System.in.read();
      final int b12 = System.in.read();
      // Convert two bytes into a short value.
      final byte[] bytes3 = {(byte) b11,
                             (byte) b12};
      final short s2 = ByteBuffer.wrap(bytes3).getShort();
      System.out.println(s2);

      // Read an int.
      final int b13 = System.in.read();
      final int b14 = System.in.read();
      final int b15 = System.in.read();
      final int b16 = System.in.read();
      // Convert four bytes into an int value.
      final byte[] bytes4 = {(byte) b13,
                             (byte) b14,
                             (byte) b15,
                             (byte) b16};
      final int n2 = ByteBuffer.wrap(bytes4).getInt();
      System.out.println(n2);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsMixedTypes() {
      throw new AssertionError();
   }
}
