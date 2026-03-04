/*

*/

import java.util.Scanner;

/**
   This program reads one line at a time from standard input,
   and then writes each character N times to standard output,
   where N is a optional command-line argument. The default
   value for N is 2.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Double
*/
public class DoubleN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int n = 2;  // Default value for n.

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
         if (n <= 0) n = 2;
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         for (int i = 0; i < oneLine.length(); ++i)
         {
            for (int j = 0; j < n; ++j)
            {
               System.out.print( oneLine.charAt(i) );
            }
         }
         System.out.println();
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private DoubleN() {
      throw new AssertionError();
   }
}
