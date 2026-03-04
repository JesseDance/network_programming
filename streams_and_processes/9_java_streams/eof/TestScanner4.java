import java.util.Scanner;

public class TestScanner4
{
   public static void main(String[] args)
   {
      final Scanner in = new Scanner(System.in);
      System.out.println(in);
      try
      {
         if ( in.hasNextLine() )
         {
            System.out.println(in);
            final String s = in.nextLine();
            System.out.println("input line = \"" + s + "\"");
            System.out.println(in);
         }
         else
         {
            System.out.println(in);
            System.out.println("nothing");
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
