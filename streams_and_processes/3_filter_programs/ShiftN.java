/*

*/

import java.io.IOException;

/**
   This program reads one character at a time from standard input,
   shifts the character by N places in the ASCII table, and then
   writes the new character to standard output. The default value
   for N is 1.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Shift
*/
public class ShiftN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args) throws IOException
   {
      int n = 1;   // Default value for n.

      // Check for an optional command-line argument.
      if (args.length >= 1)
      {
         try
         {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore the argument.
         }
      }

      int ch = System.in.read();
      while (ch != -1)
      {
         if (ch != 10 && ch != 13) // Don't change LF or CR.
            ch += n;
         System.out.print((char)ch);
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
         ch = System.in.read();
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private ShiftN() {
      throw new AssertionError();
   }
}
