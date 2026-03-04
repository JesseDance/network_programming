import java.util.Scanner;

public class TestScanner1
{
   public static void main(String[] args)
   {
      final Scanner in = new Scanner(System.in);
      System.out.println(in);
      try
      {
         int n1 = in.nextInt();
         System.out.println(in);
         int n2 = in.nextInt();
         System.out.println(in);
         int n3 = in.nextInt();
         System.out.println(in);
         int n4 = in.nextInt();
         System.out.println(in);
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
