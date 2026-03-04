/**
   This program writes 95 bytes of data to
   standard output. What characters is this
   program writing?
*/
public class CreateCharacterData_ASCII
{
   public static void main(String[] args)
   {
    //for (int i = 32;   i < 127;  ++i)
      for (int i = 0x20; i < 0x7F; ++i)
      {
         System.out.write(i);
      }
      System.out.flush(); // Try commenting out this line.
   }
}
