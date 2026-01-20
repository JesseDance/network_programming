/*
   This program adds all the numbers on its command-line
   and writes the result to standard output.
*/

public class Adder
{
   public static void main(String[] args)
   {
      // Set the initial result.
      int sum = 0;

      // Get the command-line arguments (if they exist).
      for (int i = 0; i < args.length; i++)
      {
         try
         {
            sum += Integer.parseInt(args[i]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command-line argument.
         }
      }

      // Retun the result.
      System.out.print( sum );
   }
}
