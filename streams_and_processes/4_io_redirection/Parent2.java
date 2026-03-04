/**
                          Parent2
                    +------------------+
                    |                  |
    keyboard >--+-->> stdin     stdout >>-----+---> console window
                |   |                  |      |
                |   |           stderr >>-----+
                |   |                  |      |
                |   +------------------+      |
                |                             |
                |          Child2             |
                |     +---------------+       |
                |     |               |       |
                +---->> stdin  stdout >>------+
                      |               |       |
                      |        stderr >-------+
                      |               |
                      +---------------+
*/
class Child2 {
   public static void main(String[] args) {
      System.out.println("Where does this standard output end up?");
      System.err.println("Where does this standard error end up?");
   }
}


public class Parent2 {
   public static void main(String[] args) throws Exception {
      System.out.println("Starting java Child2 process ...");
      final Process p = new ProcessBuilder("java", "Child2")
                            .inheritIO()
                            .start();
      p.waitFor();
      System.out.println("Child2 process terminated.");
   }
}
