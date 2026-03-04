/*

*/

/**
   This program writes an arbitrary sequence
   of bytes to standard output.
<p>
   You can set the arbitrary sequence of bytes
   by changing the hexadecimal data in the
   byte array.
<p>
   Notice that for hexadecimal values above
   0x7F, Java requires that they be cast to
   a byte (because they are larger than 127
   and the range for bytes is -128 to 127).
   Another way to put this is, if the most
   significant bit of the byte is a 1, then
   the byte needs to be cast.
<p>
   Another program can read those bytes as input
   in any way the program chooses, for example
   as ints, or as doubles, or as chars, etc.
<p>
   Run this program on the command-line and
   use it to create a data file called data2.
<p>
   > java  CreateData2  >  data2
<p>
   Use that data2 file as input to any one of the
   "ReadDataAs..." programs in this directory.
*/
public class CreateData2
{
   public static void main(String[] args)
   {
      final byte[] data = {// Change this data to anything you want.
                           0x01,
                           0x12,
                           0x23,
                           0x34,
                           0x45,
                           0x56,
                           0x67,
                           0x78,
                     (byte)0x89,
                     (byte)0x9A,
                     (byte)0xAB,
                     (byte)0xBC,
                     (byte)0xCD,
                     (byte)0xDE,
                     (byte)0xEF,
                     (byte)0xF0
                          };

      for (final byte b : data)
      {
         System.out.write(b); // Write one byte.
      }
      System.out.flush();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private CreateData2() {
      throw new AssertionError();
   }
}
