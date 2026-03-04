/*
   In this example, eof is null.

   Run this program on the command-line and have
   it read the data file in the current directory,
      > java EOFv2  <  data
   or run this program with keyboard input,
      > java EOFv2
   and type data until you enter Control-z (or Control-d).
*/

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class EOFv2
{
   public static void main(String[] args)
   {
      final BufferedReader in = new BufferedReader(
                                  new InputStreamReader(System.in));
      try
      {
         String str;
         while ((str = in.readLine()) != null) // while not eof
         {
            System.out.println(str);
         }
      }
      catch (IOException e)
      {
         System.out.println(e);
      }
   }
}
