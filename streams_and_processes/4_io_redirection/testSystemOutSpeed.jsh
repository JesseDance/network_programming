long startTime1 = System.currentTimeMillis();
for (int i = 0; i < 50_000; ++i) {
   System.out.println("This is a timed test of the speed of the System.out stream.");
}
long stopTime1 = System.currentTimeMillis();

System.setOut(new PrintStream(new BufferedOutputStream(System.out, 4096), false));
long startTime2 = System.currentTimeMillis();
for (int i = 0; i < 50_000; ++i) {
   System.out.println("This is a timed test of the speed of the buffered System.out stream.");
}
long stopTime2 = System.currentTimeMillis();

System.out.printf("Wall-clock time: %,d milliseconds\n", stopTime1 - startTime1);
System.out.printf("Wall-clock time: %,d milliseconds\n", stopTime2 - startTime2);
//System.out.flush(); // Not needed in JShell?
