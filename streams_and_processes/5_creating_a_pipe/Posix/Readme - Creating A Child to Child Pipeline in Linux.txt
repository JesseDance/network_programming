
   Creating A Child to Child Pipeline in Linux

This folder contains several programs that show how a "shell like"
program can create a two stage pipeline with two child processes.

> ls | more
                  shell
              +------------+
              |            |
    +-------->> 0        1 >>----------------------------+---->
    |         |            |                             |
    |         |          2 >>------------------------+---|-->
 ---+         |            |                         |   |
    |         +------------+                         |   |
    |                                                |   |
    |                                                |   |
    |         ls                        more         |   |
    |   +------------+              +------------+   |   |
    |   |            |     pipe     |            |   |   |
    +-->> 0        1 >>--0======0-->> 0        1 >>------+
        |            |              |            |   |
        |          2 >>---+         |          2 >>--+
        |            |    |         |            |   |
        +------------+    |         +------------+   |
                          |                          |
                          +--------------------------+


This folder contains programs that demonstrate three different ways to create
the above pipeline.

In the first version, the parent process creates the first child process and
then that child process creates the pipe object and the second child process.
This is not really the correct way to create the pipeline, but it is the most
straight forward way so we do it this way first. The disadvantage of doing it
this way is that the parent cannot wait on the second child.

In the second version the parent process creates the pipe object and both the
first and second child processes. This is a bit more complicated, but the
parent process can now wait on both of the child processes.

The program create-pipeline-ver2b.c differs from create-pipeline-ver2a.c
only in that the former uses symbolic constants like STDIN_FILENO and
STDOUT_FILENO instead of descriptor numbers like 0 and 1. It also uses
pipefd[STDIN_FILENO] and pipefd[STDOUT_FILENO] instead of descriptors 3
and 4. In a real program you rarely know ahead of time which descriptors
a pipe will use, so you should use the pipe's variable name to refer to
the pipe's descriptor numbers.

In the third version the parent process creates the pipe object and then the
parent creates the first child process. The first child process then creates
the second child process. As in the first version, the parent can only wait
on the first child process.


For each version, the steps needed to create the pipeline are illustrated by
an accompanying text file.


The file pipeline.c is written to act like the Windows program Pipleline.c.
pipeline.c is written like create-pipeline-ver2b.c but it creates a
pipeline using double.c and to_upper_case.c.


It is possible to make subtle errors in this kind of programming.
Versions 1e and 3e get the order of the child processes in the pipeline
wrong and the parent process waits on the wrong child process.

When you run the two programs that are incorrect, they may leave your
terminal in a state where it is not functioning properly. When that
kind of thing happens, you can try using the reset command to see if
it can fix the terminal (it usually can).

man 1 reset   http://man7.org/linux/man-pages/man1/reset.1.html
