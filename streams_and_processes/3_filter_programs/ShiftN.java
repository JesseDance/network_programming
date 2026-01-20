/**
   This program reads one character at a time from standard input,
   shifts the character by N places in the ASCII table, and then
   writes the new character to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.io.IOException;

public class ShiftN
{
   public static void main(String[] args) throws IOException
   {
      int n = 1;   // default value for n
      // Check for a command line argument.
      if (args.length >= 1)
      {
         try
         {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // ignore the argument
         }
      }

      int ch = System.in.read();
      while (ch != -1)
      {
         if (ch != 10 && ch != 13) // don't change LF or CR
            ch += n;
         System.out.print((char)ch);
         ch = System.in.read();
      }
   }
}
