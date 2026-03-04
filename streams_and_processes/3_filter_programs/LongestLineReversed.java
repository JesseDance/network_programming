/*

*/

import java.util.Scanner;

/**
   This program prints out the reverse of the longest line
   found in its standard input stream.
<p>
   The real purpose of this program is to have an example
   of a simple filter that does not work correctly when you
   terminate its input stream using Ctrl-c instead of Ctrl-z
   or Ctrl-d.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class LongestLineReversed
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

      for (int i = longestLine.length() - 1; i >= 0; i--)
      {
         System.out.print( longestLine.charAt(i) );
      }
      System.out.println();
      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private LongestLineReversed() {
      throw new AssertionError();
   }
}
