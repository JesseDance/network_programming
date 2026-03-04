/*

*/

import java.util.Scanner;

/**
   This program prints out the longest line found in its
   standard input stream.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class LongestLine
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      String longestLine = "";

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         if ( oneLine.length() > longestLine.length() )
         {
            longestLine = oneLine;
         }
      }

      System.out.println( longestLine );
      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private LongestLine() {
      throw new AssertionError();
   }
}
