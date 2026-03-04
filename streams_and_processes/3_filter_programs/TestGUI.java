/*

*/

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;

/**
   This program is in the filters folder to test
   the executable jar file feature. The executable
   jar file for this GUI program works fine when
   you double-click on it, but it runs without a
   console window. That is why the executable jar
   files for the console programs don't seem to work
   when you double-click on them, they do not get
   a console window to run in (like the .exe console
   programs do).
<p>
   Run this program using the following command-line.
<p>
      > javac TestGUI.java
      > java  TestGUI
<p>
   Notice the output in the console window when you
   click on the program's button.
<p>
   Run this program using the following command-line
   (notice the "w" at the end of "javaw").
<p>
      > javaw TestGUI
<p>
   The console window is missing. (This is how Windows
   runs an executable jar file when you double-click on
   it.) But the standard output stream is still there.
   Run this program with this command-line (still using
   "javaw").
<p>
      > javaw TestGUI > log.txt
<p>
   While the program is running, open the file "log.txt"
   in a text editor (but DON'T use Notepad). Press the
   GUI program's button while switching back and forth
   between the GUI program and the editor. You will see
   that the GUI program's standard output stream really
   is working and is being redirected to the file.
<p>
   The "javaw" version of the JVM runs a Java program
   without a console window, but the program still has
   all three of its standard streams. But they are not
   connected to anything.
*/
public class TestGUI
{
   public static void main(String[] args)
   {
      final JFrame jf = new JFrame("Test GUI");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.setSize(300, 300);
      jf.setLocationRelativeTo(null);
      jf.setVisible(true);

      jf.setLayout(new FlowLayout());
      final JButton jb = new JButton("Click Me");
      jf.add(jb);
      jb.addActionListener(e -> System.out.println("I work."));
   }


   // Private default constructor to enforce noninstantiable class.
   // See Item 4 in "Effective Java", 3rd Ed, Joshua Bloch.
   private TestGUI() {
      throw new AssertionError();
   }
}

/*
Modify this program so that it reads one token from the
standard input stream each time you click the button and
prints that token to the standard output stream. If the
standard input stream has reached the end-of-file, then
print "eof" each time the button is clicked. Run your
modified version with these command-lines.

   > java  TestGUI < Readme.txt
   > javaw TestGUI < Readme.txt > temp.txt

*/
