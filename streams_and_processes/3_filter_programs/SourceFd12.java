/*

*/

/**
   This very simple program writes one line of text to its
   standard output stream and one line of text to its
   standard error stream.
<p>
   Recall that the standard output stream is file descriptor 1
   and the standard error stream is file descriptor 2, hence
   the program name SourceFd12.
<p>
   This program can be used as the source program in a pipeline to
   test how the pipeline operator handles the two output streams.
<p>
   For example, compare these command-lines.
<p>
      > java SourceFd12      |  java Double
      > java SourceFd12 2>&1 |  java Double
      $ java SourceFd12      |& java Double
<p>
   This program can also be used to test the output redirection
   operators `>` and `2>` and `&>` and `2>&1`.
<p>
   Compare these command-lines.
<p>
      > java SourceFd12  > temp1.txt
      > java SourceFd12 2> temp2.txt
      > java SourceFd12  > temp3.txt 2>&1
      $ java SourceFd12 &> temp4.txt

   @see Source
   @see Sink
*/
public class SourceFd12
{
   public static void main(String[] args)
   {
      System.out.println("this-string-of-text-was-sent-to-the-standard-OUTPUT-stream");
      System.err.println("this=string=of=text=was=sent=to=the=standard=ERROR=stream");

      if ( System.out.checkError() )
         throw new RuntimeException("System.out has encountered an IOException");
      if ( System.err.checkError() )
         throw new RuntimeException("System.err has encountered an IOException");
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private SourceFd12() {
      throw new AssertionError();
   }
}
