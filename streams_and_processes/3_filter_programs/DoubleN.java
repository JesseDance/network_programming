/**
   This program reads one line at a time from standard input,
   and then writes each character N times to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class DoubleN
{
   public static void main(String[] args)
   {
      int n = 2;   // default value for n
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
         if (n <= 0) n = 2;
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         for (int i = 0; i < oneLine.length(); i++)
         {
            for (int j = 0; j < n; j++)
            {
               System.out.print( oneLine.charAt(i) );
            }
         }
         System.out.println();
      }
   }
}
