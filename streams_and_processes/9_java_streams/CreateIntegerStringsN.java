/*

*/

import java.util.Random;

/**
   This program writes exactly N random integer
   string values to its standard output stream,
   one value per line.
<p>
   Run this program on the command-line and
   use it to create a data file called data.txt.
<p>
   > java  CreateIntegerStringsN  >  data.txt
<p>
   Use data.txt as input to either IntServerN.java
   or IntServerN_Buffered.java to see how much the
   buffering speeds up the program. You can also
   pipe CreateIntegerStringsN directly into IntServerN_Buffered.
<p>
   > java CreateIntegerStringsN 10000000 | java IntServerN_Buffered 10000000 > binaryData
*/
public class CreateIntegerStringsN
{
   public static void main(String[] args)
   {
      int n = 1_000; // Default value for n.

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

      final Random random = new Random();

      for (int i = 0; i < n; ++i)
      {
       //System.out.println( random.nextInt() );     // one number per line
         System.out.print( random.nextInt() + " " ); // one continuous line
      }
      System.out.close();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private CreateIntegerStringsN() {
      throw new AssertionError();
   }
}
