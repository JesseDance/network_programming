/*

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
   This program adds two numbers and writes the result to standard output.

   The default values for the two numbers are 0 and 0.
   These default values can be overridden by
      1.) values from a configuration file, or by
      2.) environment variables, or by
      3.) command-line arguments.
*/
public class Adder
{
   public static void main(String[] args)
   {
      // Set the default values.
      int n1 = 0;
      int n2 = 0;

      // Override the default values with values from the configuration file.
      try (final var fileInput = new Scanner(new File("adder.cfg")))
      {
         if (fileInput.hasNextLine())
         {
            // Read the first line of the config file.
            final String oneLine = fileInput.nextLine();
            try
            {
               // Get an operand from the config file.
               n1 = Integer.parseInt(oneLine);
            }
            catch (NumberFormatException e)
            {
               // Ignore this line in the config file.
            }
         }

         if (fileInput.hasNextLine())
         {
            // Read the second line of the config file.
            final String oneLine = fileInput.nextLine();
            try
            {
               // Get an operand from the config file.
               n2 = Integer.parseInt(oneLine);
            }
            catch (NumberFormatException e)
            {
               // Ignore this line in the config file.
            }
         }
      }
      catch (FileNotFoundException e)
      {
         // Ignore the configuration file.
      }

      // Override the default values with environment
      // variable values (if they exist and can be parsed).
      final String op1 = System.getenv( "adder_n1" );
      if (op1 != null )
      {
         try
         {
            // Get an operand from the environment.
            n1 = Integer.parseInt(op1);
         }
         catch (NumberFormatException e)
         {
            // Ignore this environment variable.
         }
      }

      final String op2 = System.getenv( "adder_n2" );
      if (op2 != null )
      {
         try
         {
            // Get an operand from the environment.
            n2 = Integer.parseInt(op2);
         }
         catch (NumberFormatException e)
         {
            // Ignore this environment variable.
         }
      }

      // Override the default values with command-line
      // argument values (if they exist and can be parsed).
      if (args.length > 0)
      {
         try
         {
            // Get an operand from the command-line.
            n1 = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command-line argument.
         }
      }

      if (args.length > 1)
      {
         try
         {
            // Get an operand from the command-line.
            n2 = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command-line argument.
         }
      }

      System.out.print( n1 + n2 );
   }
}
