/*

*/

import java.util.Scanner;

/**
   This program reads a search string from the command-line
   and then it reads lines from standard input and echos
   those lines that contain the search string.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class Find
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args)
   {
      // Check for a mandatory comand-line argument.
      if (0 == args.length)
      {
         System.out.println("Usage: java Find <string>");
         System.exit(-1);
      }

      final String searchTerm = args[0];

      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         if (-1 != oneLine.indexOf(searchTerm))
         {
            System.out.println( oneLine );
            if ( System.out.checkError() )
               throw new RuntimeException("System.out has encountered an IOException");
         }
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Find() {
      throw new AssertionError();
   }
}
