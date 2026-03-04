@set PROMPT=$G 
javac -encoding utf-8  BoxDrawingChars_UTF_8.java
chcp 1252
java  BoxDrawingChars_UTF_8
java -Dfile.encoding=utf-8   BoxDrawingChars_UTF_8
java -Dstdout.encoding=utf-8 BoxDrawingChars_UTF_8
java -Dstdout.encoding=Cp437 BoxDrawingChars_UTF_8
chcp 437
java -Dstdout.encoding=utf-8 BoxDrawingChars_UTF_8
java  BoxDrawingChars_UTF_8
chcp 65001
java -Dstdout.encoding=Cp437 BoxDrawingChars_UTF_8
pause
