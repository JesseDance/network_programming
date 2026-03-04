/*

*/

import java.util.Scanner;

/**
   This program reads N lines from standard input and
   echos them to standard output. After reading N lines
   this program stops reading its input and it closes
   its output stream and then terminates.
<p>
   The value of N can be set by an optional command-line
   argument. The default value for N is 10 (like the
   Linux "head" command).
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class FirstN
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      int n = 10; // Default value for n.

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
         if (n <= 0) n = 10;
      }

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      int lineNumber = 1;
      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.println( oneLine );
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException at line " + lineNumber);
         if (lineNumber >= n) break;
         ++lineNumber;
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private FirstN() {
      throw new AssertionError();
   }
}
