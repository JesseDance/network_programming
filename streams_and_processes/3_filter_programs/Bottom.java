/*

*/

/**
   This program is an infinite loop. This program does not
   read or write any data to its I/O streams.
<p>
   This program is called "bottom" because that is the name
   given to it in Type Theory. The symbol for bottom is _|_.
<p>
   http://wiki.haskell.org/Bottom
<p>
   https://en.wikipedia.org/wiki/Bottom_type
<p>
   If you put this program in a pipeline, it makes the
   whole pipeline run forever.
<p>
   Compare
<p>
      > java Echo very-large-text-file.txt | java Sink
<p>
   with
<p>
      > java Echo very-large-text-file.txt | java Null
<p>
   with
<p>
      > java Echo very-large-text-file.txt | java Bottom
<p>
   Consider this coomand-line.
<p>
      > java Source | java EchoNFd12 500 | java Bottom
<p>
   This command-line lets us see how big a pipe buffer is (why?).
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Null
   @see Sink
*/
public class Bottom
{
   public static void main(String[] args)
   {
      while(true) { }
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Bottom() {
      throw new AssertionError();
   }
}
