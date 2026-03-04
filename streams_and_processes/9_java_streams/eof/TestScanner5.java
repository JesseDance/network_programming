import java.util.Scanner;

public class TestScanner5
{
   public static void main(String[] args)
   {
      final Scanner in = new Scanner(System.in);
      System.out.println(in);
      try
      {
         if ( in.hasNextInt() )
         {
            System.out.println(in);
            final int n = in.nextInt();
            System.out.println("input integer = \"" + n + "\"");
            System.out.println(in);
         }
         else
         {
            System.out.println(in);
            System.out.println("not an int");
         }
      }
      catch (Exception e)
      {
         System.out.println(in);
         throw(e);
      }
      finally
      {
         in.close();
         System.out.println(in);
      }
   }
}
