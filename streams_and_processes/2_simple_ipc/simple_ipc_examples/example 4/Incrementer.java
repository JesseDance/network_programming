/*

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
   This program is a "filter program" that reads integer
   values from its standard input stream and writes
   incremented integer values to its standard output stream.
<p>
   This program adds an increment value to every integer
   it finds in its standard input stream. The incremented
   numbers are written to the standard output stream.
<p>
   The default increment value is 1. This default increment
   value can be overridden by
      1.) a value from a properties file, or by
      2.) a value in an environment variable, or by
      3.) a values in a command-line argument.
*/
public class Incrementer
{
   public static void main(String[] args)
   {
      // Set the default increment value.
      int inc = 1;

      // Override the default increment value with a value from the properties file.
      final Properties properties = new Properties();
      try (final var fis = new FileInputStream(new File("incrementer.properties")))
      {
         properties.load(fis);
         final String op = properties.getProperty("inc");
         if (op != null) try {inc = Integer.parseInt(op);} catch (Exception e){}
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

      // Override the default increment value with an environment
      // variable value (if it exists and can be parsed).
      final String op = System.getenv( "increment" );
      if (op != null )
      {
         try
         {
            inc = Integer.parseInt(op);
         }
         catch (NumberFormatException e)
         {
            // Ignore this environment variable
         }
      }

      // Override the default increment value with a command-line
      // argument value (if it exists and can be parsed).
      if (args.length > 0)
      {
         try
         {
            inc = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            // Ignore this command-line argument.
         }
      }

      final Scanner in = new Scanner(System.in);
      while ( in.hasNext() )
      {
         final String num = in.next(); // Read a token.
         try // to parse this token.
         {
            final int number = Integer.parseInt(num);
            System.out.println( number + inc );
         }
         catch (NumberFormatException e)
         {
            // Ignore this token.
         }
      }
   }
}
