/*

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
   This program adds two numbers and writes the result to standard output.

   The default values for the two numbers are 0 and 0.
   These default values can be overridden by
      1.) values from a properties file, or by
      2.) environment variables, or by
      3.) command line arguments.
*/
public class Adder2
{
   public static void main(String[] args)
   {
      // Set the default values.
      int n1 = 0;
      int n2 = 0;

      // Override the default values with values from the properties file.
      final Properties properties = new Properties();
      try (final var fis = new FileInputStream(new File("adder.properties")))
      {
         properties.load(fis);
         final String op1 = properties.getProperty("n1");
         final String op2 = properties.getProperty("n2");
         if (op1 != null) try {n1 = Integer.parseInt(op1);} catch (Exception e){}
         if (op2 != null) try {n2 = Integer.parseInt(op2);} catch (Exception e){}
      }
      catch (FileNotFoundException e)
      {
         // Ignore the properties file.
      }
      catch (IOException e)
      {
         e.printStackTrace(System.err);
         System.exit(-1);
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
