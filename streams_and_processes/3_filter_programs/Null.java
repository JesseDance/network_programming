/*

*/

/**
   This program does not do anything.
<p>
   Try putting this program into a pipeline, at the
   beginning, the end, or in the middle.
<p>
   Since this program does not read any input, it does
   not empty its input buffer. How does that affect
   what comes before it in a pipeline?
<p>
   Compare
<p>
      > java Source | java EchoN 5000 | java Sink
<p>
   with
<p>
      > java Source | java EchoN 5000 | java Bottom
<p>
   with
<p>
      > java Source | java EchoN 5000 | java Null
<p>
   with
<p>
      > java Source | java EchoN1and2 5000 | java Null
<p>
   This program immediately closes its output streams. How
   does that affect what comes after it in a pipeline?
<p>
   Compare
<p>
      > java CountLines < very-large-text-file.txt | java Sink | java CountWords
<p>
   with
<p>
      > java CountLines < very-large-text-file.txt | java Null | java CountWords
<p>
   This command-line
<p>
   > java Null < input.txt > output.txt
<p>
   will create an empty output.txt file, and the command-line
   will open input.txt file, but it will not read any data
   from input.txt
<p>
   When using Windows, if standard input is the console
   keyboard, use ^z (Control-z) to denote the end of file
   (and you must use ^z at the beginning of a line!).

   @see Bottom
   @see Sink
*/

public class Null
{
   public static void main(String[] args)
   {
      System.exit(-1); // Terminate with an error condition.
    //System.exit(0);  // Terminate normally.
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private Null() {
      throw new AssertionError();
   }
}
