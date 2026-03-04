/**
                          Parent4
                    +------------------+
                    |                  |
     keyboard >---->> stdin     stdout >>-----+---> console window
                    |                  |      |
                    |           stderr >>-----+
                    |                  |      |
                    +------------------+      |
                                              |
                           Find               |
                      +---------------+       |
                      |               |       |
     Readme.txt >---->> stdin  stdout >>-----------> temp.txt
                      |               |       |
                      |        stderr >>------+
                      |               |
                      +---------------+
*/

import java.io.File;

public class Parent4 {
   public static void main(String[] args) throws Exception {
      System.out.println("Starting java Child4 process ...");
      final Process p = new ProcessBuilder("java", "-cp", "filters.jar",
                                           "Find",
                                           "Redirection")
                            .redirectInput(new File("Readme.txt"))   // < Readme.txt
                            .redirectOutput(new File("temp.txt"))    // < temp.txt
                            .redirectError(ProcessBuilder.Redirect.INHERIT)
                            .start();
      p.waitFor();
      System.out.println("Child4 process terminated.");
   }
}
