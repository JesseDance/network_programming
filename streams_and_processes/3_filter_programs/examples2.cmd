@rem
@rem  After compiling all the filter programs and building the filters.jar file,
@rem  here is how you run the filter programs using the jar file.
@rem
java -cp filters.jar Reverse < Readme.txt | java -cp filters.jar DoubleN 3 | java -cp filters.jar MakeOneLine > text1.txt
@rem
java -cp filters.jar Reverse < Readme.txt | java -cp filters.jar ToUpperCase | java -cp filters.jar DoubleN 3 > text2.txt
pause
