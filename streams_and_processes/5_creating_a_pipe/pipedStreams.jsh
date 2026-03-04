var pos = new PipedOutputStream()
var pis = new PipedInputStream(pos)
pos.write( "hello".getBytes() )
var bytes = new byte[5]
pis.read(bytes)
bytes
new String(bytes)
/list
