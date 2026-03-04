rem This is NOT the correct encoding for all the files in this directory!
javadoc -encoding Cp437  -d html  -Xdoclint:all,-missing  -link https://docs.oracle.com/en/java/javase/21/docs/api/  -linksource -quiet -nohelp -nosince -nodeprecatedlist -nodeprecated -version -author -tag param -tag return -tag throws  *.java
pause
