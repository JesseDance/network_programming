/*

*/

/**
   This program writes exactly 16 bytes of
   data to standard output. Those 16 bytes
   of data can be read as input in any way
   we choose, for example as four ints (with
   four bytes per int), or as two doubles (with
   eight bytes per double), or as eight chars
   (with two bytes per char), etc.
<p>
   Run this program on the command-line and
   use it to create a data file called data.
<p>
   > java  CreateData  >  data
<p>
   Use that data file as input to any one of the
   "ReadDataAs..." programs in this directory.
*/
public class CreateData
{
   public static void main(String[] args)
   {
      for (int i = 0x78; i <= 0x78 + 15; ++i)
      {
         System.out.write(i); // Write one byte.
      }
      System.out.flush();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private CreateData() {
      throw new AssertionError();
   }
}

/*
   Try running this program in the PowerShell command-line.
   Explain (in detail) the results.

   PS > java  CreateData  >  data
*/
