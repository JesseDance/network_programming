/*
   This program demonstrates an older Java API for creating a process.

   See
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Runtime.html
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Runtime.html#getRuntime()
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Runtime.html#exec(java.lang.String%5B%5D)
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html
*/

import java.lang.Runtime;
import java.lang.Process;

public class RunPrograms_using_Runtime
{
   public static void main(String[] args)
   {
      try
      {
         // Create a Windows command-line for running a (GUI) program.
         // Notice that there is one command-line argument.
         final String[] cmd1 = {"notepad.exe",
                                "RunPrograms_using_Runtime.java"};
         // Execute the command line.
         final Process p1 = Runtime.getRuntime().exec(cmd1);
         System.out.println( "notepad pid: " + p1.pid() );


         // Create a Windows command-line for running another (GUI) program.
         // Notice that there are several command-line arguments.
         final String[] cmd2 = {"explorer",
                                "/n",
                                "/e",
                                "/root,C:\\Windows\\System32"};
         // Execute the command line.
         final Process p2 = Runtime.getRuntime().exec(cmd2);
         System.out.println( "explorer pid: " + p2.pid() );


         // Create a Windows command-line for running another (GUI) program.
         final String[] cmd3 = {"charmap.exe"};
         // Execute the command-line.
         final Process p3 = Runtime.getRuntime().exec(cmd3);
         System.out.println( "charmap pid: " + p3.pid() );


         // Create a Windows command-line for running another program.
         // Notice that this is not a GUI program, it's a CLI program.
         final String[] cmd4 = {"ipconfig.exe"};
         // Execute the command-line.
         final Process p4 = Runtime.getRuntime().exec(cmd4);
         System.out.println( "ipconfig pid: " + p4.pid() );
         // NOTE: There is no output from running this program.
         //       It should print output on the console screen,
         //       but it does not. This is one of the problems
         //       with using Runtime to start up processes.
      }
      catch (Exception e)
      {
         System.out.println( e );
      }
   }
}
