/*
   In this example, eof is -1.

   Run this program on the command-line and have
   it read the data file in the current directory,
      > java EOFv1  <  data
   or run this program with keyboard input,
      > java EOFv1
   and type data until you enter Control-z (or Control-d).
*/

import java.io.IOException;

public class EOFv1
{
   public static void main(String[] args)
   {
      try
      {
         int b;
         while ((b = System.in.read()) != -1) // while not eof
         {
            System.out.println(b);
         }
      }
      catch (IOException e)
      {
         System.out.println(e);
      }
   }
}
