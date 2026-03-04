/**
   You can run this program from the command-line
   or in the Java Visualizer.
<p>
   You might think that print(123) and write(123)
   would produce similar results, but they do not.
<p>
   What exactly does print(123) do?
   What exactly does write(123) do?
*/
public class WhatIsAnInteger
{
   public static void main(String[] args)
   {
      System.out.print(123);
      System.out.print("__");
      System.out.write(123); // Look in an ASCII table.
      System.out.println();
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private WhatIsAnInteger() {
      throw new AssertionError();
   }
}

//https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/PrintStream.html#print(int)
//https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/PrintStream.html#write(int)
