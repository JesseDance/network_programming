javac  -encoding Cp1252 IllegalCharacters.java
javac  -encoding UTF-8  IllegalCharacters.java
javac  -encoding Cp437  IllegalCharacters.java
java  IllegalCharacters
chcp 65001
java  -Dfile.encoding=UTF-8 IllegalCharacters
java  IllegalCharacters
chcp 1252
java  -Dfile.encoding=Cp1252 IllegalCharacters
java  IllegalCharacters
pause
