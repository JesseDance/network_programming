System.out.println("Copying \"Readme.txt\" to \"ReadmeCopy.txt\".")
var inSaved  = System.in
var outSaved = System.out
System.setIn( new FileInputStream( "Readme.txt" ) )
System.setOut( new PrintStream( new FileOutputStream( "ReadmeCopy.txt" ) ) )
var scanner = new Scanner(System.in)
while ( scanner.hasNextLine() ) {
   final String oneLine = scanner.nextLine();
   System.out.println( oneLine );
}
scanner.close()
System.in.close()
System.out.close()
System.setIn(inSaved)
System.setOut(outSaved)
System.out.println("Done copying \"Readme.txt\" to \"ReadmeCopy.txt\".")
