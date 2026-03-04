var pb1 = new ProcessBuilder("java", "-cp", "filters.jar", "ToUpperCase").
               redirectError(ProcessBuilder.Redirect.appendTo(new File("errors.txt")))
var pb2 = new ProcessBuilder("java", "-cp", "filters.jar", "DoubleN", "3").
               redirectError(ProcessBuilder.Redirect.appendTo(new File("errors.txt")))
var builders = java.util.Arrays.asList(pb1, pb2)
var pipeline = ProcessBuilder.startPipeline(builders)
var out = pipeline.get(0).getOutputStream()
var in  = pipeline.get(1).getInputStream()
out.write( "hello".getBytes() )
out.close()
var bytes = new byte[15]
in.read(bytes)
bytes
new String(bytes)
/list
