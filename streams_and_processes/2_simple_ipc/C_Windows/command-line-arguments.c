/*
   This program demonstrates a simple use of argc and argv.

   argc is a count of the number of strings that were on the
   command line that started the program.

   argv is an array of strings and it holds the strings from
   the command line that started the program. The strings are
   contained in argv in the order in which they appeared on
   the command line reading the command line from left to right.
   The first string in argv is always the name of the program
   itself (which must have been the first string on the command line).

   The strings on the command line, except for the name of the
   program, are considered as "arguments" for the program (analogous
   to "arguments" for a function call). Hence the name argc, which
   is an abbreviation of "argument count", and argv, which is an
   abbreviation of "argument vector" (the word vector is sometimes
   used as a synonym for array).

   Be sure to try examples like
     using-argc-and-argv.exe  a b "c d"
     using-argc-and-argv.exe  *
     using-argc-and-argv.exe  *.c
*/
#include <stdio.h>

int main(int argc, char* argv[])
{
   int i;

   printf("There were %d strings on the command line.\n", argc);
   printf("The strings are:\n");

   /* walk the argv array printing out each argument string */
   for (i = 0; i < argc; i++)
   {
      printf("  %s\n", argv[i]);
   }

   return 0;
}

/*
   Question:
   When you run the program, notice that the strings on the
   command line are delimited by spaces (or tabs). How do you
   use the command line to give a program an argument string
   that has spaces in it?
*/