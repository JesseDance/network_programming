
import java.io.File;

class Child7 {
   public static void main(String[] args) {
      System.out.println("Where does this standard output end up?");
      System.err.println("Where does this standard error end up?");
   }
}

// Choose any combination of redirections.
public class Parent7 {
   public static void main(String[] args) throws Exception {
      System.out.println("Starting java Child7 process ...");
      final Process p = new ProcessBuilder("java", "Child7")
//                          .inheritIO()
                            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//                          .redirectOutput(new File("output.txt"))            //  > output.txt
//                          .redirectOutput(ProcessBuilder.Redirect.appendTo(new File("output.txt"))) // >> output.txt
                            .redirectError(ProcessBuilder.Redirect.INHERIT)
//                          .redirectError(new File("errors.txt"))             // 2> errors.txt
//                          .redirectErrorStream(true)                         // 2>&1
                            .start();
      p.waitFor();
      System.out.println("Child7 process terminated.");
   }
}
