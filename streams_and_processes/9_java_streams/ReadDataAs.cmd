@javac CreateData.java
@javac ReadDataAsBoolean.java
@javac ReadDataAsByte.java
@javac ReadDataAsByteUnsigned.java
@javac ReadDataAsChar.java
@javac ReadDataAsDouble.java
@javac ReadDataAsFloat.java
@javac ReadDataAsInt.java
@javac ReadDataAsLong.java
@javac ReadDataAsShort.java
@javac ReadDataAsShortUnsigned.java
@javac ReadDataAsMixedTypes.java
java CreateData | java -cp filters.jar HexDump
java CreateData | java ReadDataAsLong
java CreateData | java ReadDataAsDouble
java CreateData | java ReadDataAsInt
java CreateData | java ReadDataAsFloat
java CreateData | java ReadDataAsShort
java CreateData | java ReadDataAsShortUnsigned
java CreateData | java ReadDataAsChar
java CreateData | java ReadDataAsBoolean
java CreateData | java ReadDataAsByte
java CreateData | java ReadDataAsByteUnsigned
java CreateData | java ReadDataAsMixedTypes
pause
