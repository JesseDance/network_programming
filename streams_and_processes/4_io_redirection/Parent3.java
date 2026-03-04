/**
                          Parent3
                    +------------------+
                    |                  |
    keyboard >----->> stdin     stdout >>-----+---> console window
                    |                  |      |
                    |           stderr >>-----+
                    |                  |      |
                    +------------------+      |
                                              |
                           Child3             |
                      +---------------+       |
            pipe      |               |       |
       >--0======0--->> stdin  stdout >>------+
                      |               |
                      |        stderr >-----0======0--->
                      |               |       pipe
                      +---------------+
*/
class Child3 {
   public static void main(String[] args) {
      System.out.println("Where does this standard output end up?");
      System.err.println("Where does this standard error end up?");
   }
}


public class Parent3 {
   public static void main(String[] args) throws Exception {
      System.out.println("Starting java Child3 process ...");
      final Process p = new ProcessBuilder("java", "Child3")
                            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                            .start();
      p.waitFor();
      System.out.println("Child3 process terminated.");
   }
}
