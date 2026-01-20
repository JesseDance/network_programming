
     I/O Redirection, Filters, and Pipelines

This folder shows a practically free way to implement inter-process
communication using pipes.

The example programs in this folder are all "filters". They are programs
that read from standard input and write to standard output. A filter
program will usually do some manipulation of what it has read from
standard input before it writes it to standard output.

Use these examples to experiment with I/O redirection and, more importantly,
with "pipes". That is, use pipes to combine several filters together (into
an inter-process pipeline).

Here are several pipe command-line examples.

    > java Reverse < Readme.txt > result.txt

    > java Double < Readme.txt | java Reverse

    > java Double | java ToUpperCase | java Reverse

    > java ShiftN 2 | java ToUpperCase | java Reverse

    > java Twiddle < Readme.txt | java ToUpperCase | java Double | java RemoveVowels > result2.txt
