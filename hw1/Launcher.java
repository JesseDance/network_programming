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
public class Launcher {

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

   public static void main(String[] args) {

      final String systemDrive = System.getenv("SystemDrive");
      final String system32 = systemDrive + "\\Windows\\system32\\";
   
      //begin launcher command running loop
      while(true) {
         //print options and prompt for input
         int selection = getUserSelection();

         if(selection == 0) {
            break;
         }

         if(selection < 0 || selection > 8) {
            System.out.println();
            continue;
         }

         try {
            ProcessBuilder pb = new ProcessBuilder(system32 + cmds[selection - 1]);

            if(selection < 7) {
               startProcess(pb, selection);
            } else {
               //need to inherit this cmd io
               pb.inheritIO();
               Process p = startProcess(pb, selection);
               System.out.printf("Launcher waiting on program %d...\n\n", selection);
               //wait for cmd io to prevent conflicts
               p.waitFor();

               //attempt to record cpu time
               long cpuTime = (p.info().totalCpuDuration().get()).toMillis();

               System.out.printf(
                  "Program %d exited with return value %d and ran for %d cpu milliseconds.\n\n",
                  selection,
                  p.exitValue(),
                  cpuTime);
            }

         } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
         }
      }
   }

   //attempt to start selected process
   //print PID on success, Error on failure
   public static Process startProcess(ProcessBuilder pb, int selection) 
                                                         throws Exception {

      Process p = pb.start();

      System.out.printf("Started program %d with PID %d \n",
                        selection, 
                        p.pid()
                        );

      return p;
   }

   //attempt to get user input
   //no exception messages
   public static int getUserSelection() {
      printOptions();
      
      //user input loop
      while(true) {
         System.out.printf("Enter your choice: ");
         String input = in.nextLine();
         Scanner lineIn = new Scanner(input);

         try {
            int selection = Integer.parseInt(input);

            if (selection >= 0 && selection <= 8) {
               return selection;
            }

         } catch (Exception e) {
            // System.out.println("Error: " + e.getMessage());
         }
      }

   }

   public static void printOptions() {
      System.out.println("Please make a choice from the following list.");
      System.out.println("  0: Quit");
      System.out.println("  1: Run TaskManager");
      System.out.println("  2: Run Notepad");
      System.out.println("  3: Run Character Map");
      System.out.println("  4: Run Sound Volume");
      System.out.println("  5: Run \"About Windows\" ");
      System.out.println("  6: Run \"System Information\" ");
      System.out.println(" *7: Run NS Lookup");
      System.out.println(" *8: Run Cmd shell");
      
   }


}
