var p = new ProcessBuilder("java", "-cp", "filters.jar", "DoubleN", "3").
                      redirectError(ProcessBuilder.Redirect.INHERIT).
                      start();
var out = p.getOutputStream();
var in  = p.getInputStream();
out.write( "hello".getBytes() )
out.close()
var bytes = new byte[15]
in.read(bytes)
bytes
new String(bytes)
/list
