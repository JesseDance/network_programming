/*

*/

import java.util.Scanner;

/**
   This program reads lines from standard input and
   echos them to standard output and adds a line
   number to the beginning of each line.
<p>
   This program takes an optional command-line argument
   that sets the width of the integer line numbers.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class LineNumbers
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int width = 3; // The default value for width.

      // Check for an optional command-line argument.
      if (args.length >= 1)
      {
         try
         {
            width = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore the argument.
         }
         if (width <= 0) width = 3;
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      int lineNumber = 1;
      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.printf("%,0"+width+"d: %s\n", lineNumber, oneLine);
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException at line " + lineNumber);
         ++lineNumber;
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private LineNumbers() {
      throw new AssertionError();
   }
}
