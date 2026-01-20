/**
   This program reads lines from standard input and
   writes their reverse to standard output.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class Reverse
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         final String oneLine = scanner.nextLine();
         for (int i = oneLine.length() - 1; i >= 0; i--)
         {
            System.out.print( oneLine.charAt(i) );
         }
         System.out.println();
      }
   }
}
