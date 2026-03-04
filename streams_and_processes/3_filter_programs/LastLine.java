/*

*/

import java.util.Scanner;

/**
   This program prints out the last line that
   it finds in its standard input stream.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class LastLine
{
   public static void main(String[] args)
   {
      final Scanner scanner = new Scanner( System.in );

      String lastLine = "";

      while ( scanner.hasNextLine() )
      {
         lastLine = scanner.nextLine();
      }
      System.out.println( lastLine );
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private LastLine() {
      throw new AssertionError();
   }
}
