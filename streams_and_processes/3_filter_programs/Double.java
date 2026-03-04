/*

*/

import java.util.Scanner;

/**
   This program reads one line at a time from standard input,
   and then writes each character twice to standard output.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see DoubleN
*/
public class Double
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         for (int i = 0; i < oneLine.length(); i++)
         {
            System.out.print( oneLine.charAt(i) );
            System.out.print( oneLine.charAt(i) );
         }
         System.out.println();
         if ( System.out.checkError() )
            throw new RuntimeException("System.out has encountered an IOException");
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Double() {
      throw new AssertionError();
   }
}
