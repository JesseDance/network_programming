/**
   This program writes 127 bytes of data to
   standard output. What characters is this
   program writing?
*/
public class CreateCharacterData_Ex_ASCII
{
   public static void main(String[] args)
   {
    //for (int i = 128;  i < 255;  ++i)
      for (int i = 0x80; i < 0xFF; ++i)
      {
         System.out.write(i);
      }
      System.out.flush(); // Try commenting out this line.
   }
}
