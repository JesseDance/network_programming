/*
   In this example, eof is an exception.

   Run this program on the command-line and have
   it read the data file in the current directory,
      > java EOFv3  <  data
   or run this program with keyboard input,
      > java EOFv3
   and type data until you enter Control-z (or Control-d).
*/

import java.io.IOException;
import java.io.EOFException;
import java.io.DataInputStream;

public class EOFv3
{
   public static void main(String[] args)
   {
      final DataInputStream in = new DataInputStream(System.in);

      try
      {
         while (true)
         {
            final byte b = in.readByte();
            System.out.println(b);
         }
      }
      catch (EOFException e) // read until eof
      {
      }
      catch (IOException e)
      {
         System.out.println(e);
      }
   }
}
