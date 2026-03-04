/*
   Course: CS 33600
   Name: Jesse Dance
   Email: dancej@pnw.edu
   Assignment: HW1
*/

import java.util.Scanner;

/**
   This program uses the Java Process API
   to run several Windows programs.
   See
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ProcessBuilder.html
      https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html
*/
public class Launcher
{
   private final static Scanner in = new Scanner(System.in);
   private final static String[] cmds = {"Taskmgr.exe",
                                         "notepad.exe",
                                         "charmap.exe",
                                         "sndvol.exe",
                                         "winver.exe",
                                         "msinfo32.exe",
                                         "nslookup.exe",
                                         "cmd.exe"
                                        };

   public static void main(String[] args)
   {
      final String systemDrive = System.getenv("SystemDrive");
      final String system32 = systemDrive + "\\Windows\\system32\\";

      System.out.println("Please make a choice from the following list.");
      System.out.println("0: Quit");
      System.out.println("1: Run TaskManager");
      System.out.println("2: Run Notepad");
      System.out.println("3: Run Character Map");
      System.out.println("4: Run Sound Volume");
      System.out.println("\"5: Run "About Windows\" ");
      System.out.println("\"6: Run "System Information\" ");
      System.out.println("*7: Run NS Lookup");
      System.out.println("*8: Run Cmd shell");
      System.out.println("Enter your choice: 7");
  
  
  
  

   }


}
