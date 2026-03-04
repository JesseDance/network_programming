/*

*/

import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;

/**
   This program reads its standard input as a stream of bytes
   and prints to its standard output the string of binary digits
   that represents each byte.
<p>
   There are two optional command-line arguments.
<p>
   The first is the number of output columns. The default is eight.
<p>
   The second is the number of bytes in a "group". Each group of
   bytes is separated by a blank line. The default value is 0,
   meaning no grouping of bytes.
<p>
   When playing with file filters, it is useful to know that "end-of-file" is
   denoted at the command-line in Windows by Ctrl-Z and by Ctrl-D in Linux.

   @see HexDump
   @see BinaryDump
*/
public class BinaryDump
{
   /**
      @param args  two optional command-line arguments
   */
   public static void main(String[] args) throws IOException
   {
      final DataInputStream in = new DataInputStream(System.in);

      int columns = 8; // Default number of columns.
      int groups = 0;  // Default size of a group. Zero means no groups.

      if (args.length > 0)
      {
         int c = -1;
         try {c = Integer.parseInt(args[0]);} catch (Exception e){}
         if (c > 0) columns = c;
      }

      if (args.length > 1)
      {
         int g = -1;
         try {g = Integer.parseInt(args[1]);} catch (Exception e){}
         if (g >= 0) groups = g;
      }

      int colCount = 0;
      int groupCount = 0;

      while(true)
      {
         try
         {
            final byte b = in.readByte();
            final String binaryString = toBinary(b, 8);
            System.out.print(binaryString);

            ++colCount;
            ++groupCount;

            if (groupCount == groups)
            {
               System.out.printf("\n\n");
               groupCount = 0;
               colCount = 0;
            }
            else if (colCount == columns)
            {
               System.out.printf("\n");
               colCount = 0;
            }
            else
            {
               System.out.printf(" "); // One space between columns.
            }
         }
         catch (EOFException e)
         {
            if (0 != colCount)
            {
               System.out.printf("\n"); // A partial line should also end with a newline.
            }
            break;
         }
      }
   }


   /**
      @param c     an integer whose least significant bits will be converted to a String
      @param bits  the number of least significant bits to convert into a String
      @return the binary String representation of the least significant bits of c
   */
   public static String toBinary(long c, int bits)
   {
      String digits = "";
      for (int i = 0; i < bits; ++i)
      {
         if ( (c & 0x1L) == 0x1L)
         {
            digits = "1" + digits;
         }
         else
         {
            digits = "0" + digits;
         }
         c >>= 1;
      }
      return digits;
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private BinaryDump() {
      throw new AssertionError();
   }
}
