/*

*/

import java.io.DataInputStream;
import java.io.IOException;

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
   > java CreateData | java ReadDataAsChar
*/
public class ReadDataAsChar
{
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      final char c1 = in.readChar(); // readChar() can throw an IOException.
      final char c2 = in.readChar();
      final char c3 = in.readChar();
      final char c4 = in.readChar();
      final char c5 = in.readChar();
      final char c6 = in.readChar();
      final char c7 = in.readChar();
      final char c8 = in.readChar();

      System.out.println(c1);
      System.out.println(c2);
      System.out.println(c3);
      System.out.println(c4);
      System.out.println(c5);
      System.out.println(c6);
      System.out.println(c7);
      System.out.println(c8);
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ReadDataAsChar() {
      throw new AssertionError();
   }
}

// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html#readChar()
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInput.html#readChar()
