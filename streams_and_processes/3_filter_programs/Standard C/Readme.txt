
   I/O Redirection, Filters, and Pipelines - Standard C

This folder shows a practically free way to implement
inter-process communication using pipes.

The example programs in this folder are all "filters".
They are programs that read from standard input and
write to standard output. A filter program will usually
do some manipulation of what it has read from standard
input before it writes it to standard output.

Use these examples to experiment with I/O redirection
and, more importantly, with "pipes". That is, use pipes
to combine several filters together (into an inter-process
pipeline).

Here are several pipe command-line examples.

C:\> reverse < Readme.txt > result.txt

C:\> double < Readme.txt | reverse

C:\> double | to_upper_case | reverse

C:\> shiftN 2 | to_upper_case | reverse

C:\> twiddle < Readme.txt | to_upper_case | double  | remove_vowels > result2.txt
