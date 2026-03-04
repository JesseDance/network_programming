var pb1 = new ProcessBuilder("java", "-cp", "filters.jar","ToUpperCase").
               redirectInput(new File("Readme.txt")).
               redirectError(ProcessBuilder.Redirect.appendTo(new File("errors.txt")))
var pb2 = new ProcessBuilder("java", "-cp", "filters.jar", "Reverse").
               redirectOutput(new File("temp.txt")).
               redirectError(ProcessBuilder.Redirect.appendTo(new File("errors.txt")))
var builders = java.util.Arrays.asList(pb1, pb2);
var pipeline = ProcessBuilder.startPipeline(builders);
for (final Process p : pipeline)
{
   p.waitFor();
}
/list
