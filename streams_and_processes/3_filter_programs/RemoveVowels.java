/**
   This program reads lines from standard input, removes
   all the vowels, and writes the rest to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class RemoveVowels
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
            if ( -1 == "aeiouAEIOU".indexOf( oneLine.charAt(i) ) )
               System.out.print( oneLine.charAt(i) );
         }
         System.out.println();
      }
   }
}
