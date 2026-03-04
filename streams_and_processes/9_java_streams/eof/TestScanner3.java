import java.util.Scanner;

public class TestScanner3
{
   public static void main(String[] args)
   {
      try (final Scanner in = new Scanner(System.in)) // try-with-resources
      {
         System.out.println(in);
         try
         {
            while ( in.hasNext() )
            {
               int n = in.nextInt();
               System.out.println(in);
            }
            System.out.println(in);
         }
         catch (Exception e)
         {
            System.out.println(in);
            throw(e);
         }
      }// the Scanner gets closed because of the try-with-resources
      finally
      {
         //System.out.println(in); // in is out of scope
      }
   }
}
