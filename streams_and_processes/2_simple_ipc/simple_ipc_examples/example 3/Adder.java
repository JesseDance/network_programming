/*
   This program adds two numbers and writes the result to standard output.

   The default values for the two numbers are 0 and 0.
   These default values can be overridden by
      1.) values from a configuration file, or by
      2.) environment variables, or by
      3.) command line arguments.
*/
import java.io.*;
import java.util.Scanner;

public class Adder
{
   public static void main(String[] args)
   {
      // Set the default values.
      int x1 = 0;
      int x2 = 0;

      // Override the default values with values from the configuration file.
      try
      {  // Open the configuration file.
         final Scanner fileInput = new Scanner( new File("adder.cfg") );
         if (fileInput.hasNextLine())
         {
            // Read the first line of the config file.
            final String oneLine = fileInput.nextLine();
            try
            {  // get an operand from the config file
               x1 = Integer.parseInt(oneLine);
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
            {  // get an operand from the config file
               x2 = Integer.parseInt(oneLine);
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
      String op1 = System.getenv( "adder-op1" );
      if (op1 != null )
      {
         try
         {
            // Get an operand from the environment.
            x1 = Integer.parseInt(op1);
         }
         catch (NumberFormatException e)
         {
            // Ignore this environment variable
         }
      }

      String op2 = System.getenv( "adder-op2" );
      if (op2 != null )
      {
         try
         {
            // Get an operand from the environment.
            x2 = Integer.parseInt(op2);
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
            // Get an operand from the command line.
            x1 = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command line argument.
         }
      }

      if (args.length > 1)
      {
         try
         {
            // Get an operand from the command line.
            x2 = Integer.parseInt(args[1]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command line argument.
         }
      }

      System.out.print( x1 + x2 );
   }
}
