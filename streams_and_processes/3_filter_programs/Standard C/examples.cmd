@rem
@rem After compiling all the filter programs, try these command lines.
@rem
to_upper_case < echo.c | reverse
@rem
double | to_upper_case | reverse
@rem
make_one_line | reverse > text1.txt
@rem
twiddle < echo.c | to_upper_case | double > text2.txt
@rem
twiddle < echo.c | to_upper_case | double | remove_vowels > text3.txt
@rem
reverse < echo.c | to_upper_case | doubleN 3 > text4.txt
