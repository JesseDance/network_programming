import java.util.Scanner;

public class TestScanner2
{
   public static void main(String[] args)
   {
      final Scanner in = new Scanner(System.in);
      System.out.println(in);
      try
      {
         while ( in.hasNext() )
         {
            int n = in.nextInt();
            System.out.println(in);
         }
      }
      catch (Exception e)
      {
         System.out.println(in);
         throw(e);
      }
      finally
      {
         in.close();  // try commenting out this line
         System.out.println(in);
      }
   }
}
