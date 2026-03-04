/**
   This program adds all the numbers on its command-line
   and returns the result as its exit value.
<p>
   To see the return value of this program at the Windows
   command prompt, use the following command after running
   this program.
       > echo %errorlevel%
*/

public class Adder
{
   public static void main(String[] args)
   {
      // Set the initial result.
      int sum = 0;

      // Get the command-line arguments (if they exist).
      for (final String arg : args)
      {
         try
         {
            sum += Integer.parseInt(arg);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command-line argument.
         }
      }

      // Retun the result.
      System.exit( sum );
   }
}
