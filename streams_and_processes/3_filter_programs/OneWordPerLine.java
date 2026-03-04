/*

*/

import java.util.Scanner;

/**
   This program prints each token in its standard input
   om its own line.
<p>
   A token is a sequence of characters that does not contain
   the space character.
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
public class OneWordPerLine
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         // Create a Scanner that breaks the input line into tokens.
         final Scanner scanner2 = new Scanner( oneLine );
         while ( scanner2.hasNext() ) // While there is another token.
         {
            final String word = scanner2.next(); // Consume the token.
            System.out.println( word );          // Print the token.
            if ( System.out.checkError() )
               throw new RuntimeException("System.out has encountered an IOException");
         }
      }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private OneWordPerLine() {
      throw new AssertionError();
   }
}
