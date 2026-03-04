/*

*/

import java.util.Scanner;

/**
   This program concatenates all the lines of its input
   into one single output line.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class MakeOneLine
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      // A fence-post problem.
      if ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.print( oneLine );
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
      }
      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         System.out.print( " " + oneLine );
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private MakeOneLine() {
      throw new AssertionError();
   }
}
