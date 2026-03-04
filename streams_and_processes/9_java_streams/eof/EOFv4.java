/*
   In this example, eof is false.

   Run this program on the command-line and have
   it read the data file in the current directory,
      > java EOFv4  <  data
   or run this program with keyboard input,
      > java EOFv4
   and type data until you enter Control-z (or Control-d).
*/

import java.util.Scanner;

public class EOFv4
{
   public static void main(String[] args)
   {
      final Scanner in = new Scanner(System.in);

      while ( in.hasNext() ) // while not eof
      {
         final String str = in.next();
         System.out.println(str);
      }
   }
}
