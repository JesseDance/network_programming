/*

*/

import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Scanner;

/**
    A buffered output stream version of IntServerN.
*/
public class IntServerN_Buffered
{
   /**
      @param args  one optional command-line argument
   */
   public static void main(String[] args) throws IOException
   {
      int n = 1; // Default value for n.

      // Check for an optional command-line argument.
      if (args.length >= 1)
      {
         try
         {
            n = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore the argument.
         }
         if (n <= 0) n = 1;
      }

      final Scanner in = new Scanner(System.in);
//    final var out = new DataOutputStream(          // does not work
//                       new BufferedOutputStream(
//                          System.out, 4096));
//    final var out = new DataOutputStream(          // works, but not what we want
//                       new BufferedOutputStream(
//                          new FileOutputStream("binaryData"), 4096));
      final var out = new DataOutputStream(
                         new BufferedOutputStream(
                            new FileOutputStream(FileDescriptor.out), 4096));

      for (int j = 0; j < n; ++j)
      {
         final int i = in.nextInt();
         out.writeInt(i); // writeInt() can throw an IOException.
      }
      out.flush();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private IntServerN_Buffered() {
      throw new AssertionError();
   }
}
