/**
                          Parent
                    +------------------+
                    |                  |
    keyboard >----->> stdin     stdout >>-----+---> console window
                    |                  |      |
                    |           stderr >>-----+
                    |                  |
                    +------------------+

                           Child
                      +---------------+
            pipe      |               |       pipe
       >--0======0--->> stdin  stdout >>----0======0--->
                      |               |
                      |        stderr >-----0======0--->
                      |               |       pipe
                      +---------------+
*/
class Child {
   public static void main(String[] args) {
      System.out.println("Where does this standard output end up?");
      System.err.println("Where does this standard error end up?");
   }
}


public class Parent {
   public static void main(String[] args) throws Exception {
      System.out.println("Starting java Child process ...");
      final Process p = new ProcessBuilder("java", "Child")
                            .start();
      p.waitFor();
      System.out.println("Child process terminated.");
   }
}
