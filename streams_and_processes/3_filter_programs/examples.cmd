@rem
@rem After compiling all the filter programs, try these command lines.
@rem
java ToUpperCase < Echo.java | java Reverse
@rem
java Double | java ToUpperCase | java Reverse
@rem
java MakeOneLine | java Reverse > text1.txt
@rem
java Twiddle < Echo.java | java ToUpperCase | java Double > text2.txt
@rem
java Twiddle < Echo.java | java ToUpperCase | java Double | java RemoveVowels > text3.txt
@rem
java Reverse < Echo.java | java ToUpperCase | java DoubleN 3 > text4.txt
