javac -encoding Cp1252  Euro_Cp1252.java
javac -encoding Cp1252  Euro_UTF_8.java
chcp 1252
java Euro_Cp1252
java Euro_UTF_8
chcp 65001
java Euro_Cp1252
java Euro_UTF_8

javac -encoding utf-8  Euro_Cp1252.java
javac -encoding utf-8  Euro_UTF_8.java
chcp 65001
java Euro_UTF_8
chcp 1252
java Euro_UTF_8
pause
