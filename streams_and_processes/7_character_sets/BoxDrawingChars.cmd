javac BoxDrawingChars_Cp437.java
chcp 437
java BoxDrawingChars_Cp437
chcp 65001
java BoxDrawingChars_Cp437
chcp 1252
java BoxDrawingChars_Cp437

javac -g -encoding Cp437 BoxDrawingChars_Cp437.java
chcp 437
java BoxDrawingChars_Cp437
chcp 65001
java BoxDrawingChars_Cp437
chcp 1252
java BoxDrawingChars_Cp437

chcp 437
java BoxDrawingChars_Cp437 > temp1.txt
type temp1.txt
java -cp filters.jar HexDump < temp1.txt
java -Dfile.encoding=Cp437 BoxDrawingChars_Cp437 > temp2.txt
type temp2.txt
java -cp filters.jar HexDump < temp2.txt

chcp 65001
java BoxDrawingChars_Cp437 > temp3.txt
type temp3.txt
java -cp filters.jar HexDump < temp3.txt
java -Dfile.encoding=utf-8 BoxDrawingChars_Cp437 > temp4.txt
type temp4.txt
java -cp filters.jar HexDump < temp4.txt


javac -g -encoding utf-8 BoxDrawingChars_UTF_8.java
chcp 437v
java BoxDrawingChars_UTF_8
chcp 65001
java BoxDrawingChars_UTF_8
chcp 1252
java BoxDrawingChars_UTF_8

chcp 65001
java BoxDrawingChars_UTF_8 > temp5.txt
type temp5.txt
java -cp filters.jar HexDump < temp5.txt
java -Dfile.encoding=utf-8 BoxDrawingChars_UTF_8 > temp6.txt
type temp6.txt
java -cp filters.jar HexDump < temp6.txt

chcp 437
java BoxDrawingChars_UTF_8 > temp7.txt
type temp7.txt
java -cp filters.jar HexDump < temp7.txt
java -Dfile.encoding=Cp437 BoxDrawingChars_UTF_8 > temp8.txt
type temp8.txt
java -cp filters.jar HexDump < temp8.txt


java -cp filters.jar HexDump 16 < BoxDrawingChars_Cp437.java
java -cp filters.jar HexDump 16 < BoxDrawingChars_UTF_8.java

javap -c BoxDrawingChars_Cp437.class
javap -c BoxDrawingChars_UTF_8.class
pause
