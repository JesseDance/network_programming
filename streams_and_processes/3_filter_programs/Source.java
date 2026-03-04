/*

*/

/**
   This very simple program writes one line of text to its
   standard output stream.
<p>
   This program can be used as the source program in a pipeline.
   For example,
<p>
      > java Source | java EchoN 100

   @see SourceFd12
   @see Sink
*/
public class Source
{
   public static void main(String[] args)
   {
      System.out.println("this-string-of-text-was-sent-to-the-standard-OUTPUT-stream");

      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Source() {
      throw new AssertionError();
   }
}
