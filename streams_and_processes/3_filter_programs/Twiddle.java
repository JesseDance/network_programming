/**
   This program reads two characters at a time from
   standard input, and then writes the two characters
   to standard output in their reverse order.

   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).
*/
import java.util.Scanner;

public class Twiddle
{
   public static void main(String[] args)
   {
      // Create a Scanner object to make it easier to use System.in
      final Scanner scanner = new Scanner( System.in );

      while ( scanner.hasNextLine() )
      {
         String oneLine = scanner.nextLine();
         for (int i = 0; i < oneLine.length(); i+=2)
         {
            if ( i+1 == oneLine.length() )
            {
               System.out.print( oneLine.charAt(i) );
            }
            else
            {
               System.out.print( oneLine.charAt(i+1) );
               System.out.print( oneLine.charAt(i) );
            }
         }
         System.out.println();
      }
   }
}
