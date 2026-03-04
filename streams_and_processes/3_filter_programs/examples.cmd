@rem
@rem After compiling all the filter programs, try these command lines.
@rem
java ToUpperCase < Readme.txt | java Reverse
@rem
java Double | java ToUpperCase | java Reverse
@rem
java MakeOneLine | java Reverse > text1.txt
@rem
java Twiddle < Readme.txt | java ToUpperCase | java Double > text2.txt
@rem
java Twiddle < Readme.txt | java ToUpperCase | java Double | java RemoveVowels > text3.txt
@rem
java Reverse < Readme.txt | java ToUpperCase | java DoubleN 3 > text4.txt
pause
