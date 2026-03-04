
   Creating A Child to Child Pipeline in Windows

We want to create the following pipeline using the Windows
operating system.
                       parent
                  +----------------+
                  |                |
       +--------->> stdin   stdout >>------------------------------+---->
       |          |                |                               |
       |          |         stderr >>--------------------------+---|--->
       |          |                |                           |   |
   ----+          +----------------+                           |   |
       |                                                       |   |
       |                                                       |   |
       |        child_1                        child_2         |   |
       |   +----------------+              +----------------+  |   |
       |   |                |     pipe     |                |  |   |
       +-->> stdin   stdout >>--0======0-->> stdin   stdout >>-|---+
           |                |              |                |  |
           |         stderr >>---+         |         stderr >>-+
           |                |    |         |                |  |
           +----------------+    |         +----------------+  |
                                 |                             |
                                 +-----------------------------+

To set up this pipeline, the parent process needs to do (at least)
these seven steps.

  1.) Ask the OS to create a pipe object.
  2.) The stdin of the 1st child should be inherited from the parent.
  3.) Redirect the stdout of the 1st child to the input of the pipe.
  4.) Ask the OS to create the 2nd child processes,
  5.) The stdout of the 2nd child should be inherited from the parent,
  6.) Redirect the stdin of the 2nd child to the output of the pipe.
  7.) Ask the OS to create the 2nd child processes,

The parent also needs to do several other smaller steps that are described
below.


Here are pictures that explain the steps in creating the pipeline.
Start with a parent process that has the keyboard as its stdin and
the console window as its stdout and stderr.

                   parent
             +------------------+
             |                  |
 keyboard--->> stdin     stdout >>-------> console window
             |                  |
             |           stderr >>------> console window
             |                  |
             +------------------+

The parent first needs to create the pipe object. Creating a pipe
creates two new streams, an input stream and an output stream.

                   parent
             +-------------------+
             |                   |
 keyboard--->> stdin      stdout >>-------> console window
             |                   |
             |            stderr >>------> console window
             |                   |
        +--->> hPipeOut_Rd       |
        |    |                   |
        |    |        hPipeIn_Wr >>----+
        |    |                   |     |
        |    +-------------------+     |
        |                              |
        |                              |
        +-----------0======0-----------+
                      pipe

The parent creates the child_1 process and tells the operating
system to have child_1 inherit the pipe's input stream as the
child's standard output stream and also have child_1 inherit
(share) both of the parent's standard input and error streams
(this is all done using the child's STARTUPINFO data structure).

                    parent
             +--------------------+
             |                    |
kbd--+------>> stdin       stdout >>--------------> console window
     |       |                    |
     |       |             stderr >>---------+--> console window
     |       |                    |          |
     |   +-->> hPipeOut_Rd        |          |
     |   |   |                    |          |
     |   |   |         hPipeIn_Wr >>---+     |
     |   |   |                    |    |     |
     |   |   +--------------------+    |     |
     |   |                             |     |
     |   |            pipe             |     |
     |   +----------0======0-----------+     |
     |                                 |     |
     |                                 |     |
     |              child_1            |     |
     |         +------------------+    |     |
     |         |                  |    |     |
     +-------->> stdin     stdout >>---+     |
               |                  |          |
               |           stderr >>---------+
               |                  |
               +------------------+

Notice that the parent and child are sharing the input stream into
the pipe. But the parent has no need to write anything to the pipe,
so the parent can close its copy of that stream.

                    parent
             +--------------------+
             |                    |
kbd--+------>> stdin       stdout >>--------------> console window
     |       |                    |
     |       |             stderr >>---------+--> console window
     |       |                    |          |
     |   +-->> hPipeOut_Rd        |          |
     |   |   |                    |          |
     |   |   +--------------------+          |
     |   |                                   |
     |   |            pipe                   |
     |   +----------0======0----------+      |
     |                                |      |
     |                                |      |
     |              child_1           |      |
     |         +------------------+   |      |
     |         |                  |   |      |
     +-------->> stdin     stdout >>--+      |
               |                  |          |
               |           stderr >>---------+
               |                  |
               +------------------+

Let us reorganize this picture a bit.

                    parent
             +--------------------+
             |                    |
kbd--+------>> stdin       stdout >>--------------------> console window
     |       |                    |
     |       |             stderr >>-----------------+--> console window
     |       |                    |                  |
     |       |                    |                  |
     |       |        hPipeOut_Rd <<-------------+   |
     |       |                    |              |   |
     |       +--------------------+              |   |
     |                                           |   |
     |                                           |   |
     |              child_1                      |   |
     |         +------------------+              |   |
     |         |                  |      pipe    |   |
     +-------->> stdin     stdout >>---0======0--+   |
               |                  |                  |
               |           stderr >>-----------------+
               |                  |
               +------------------+

Now the parent creates the child_2 process and tells the operating
system to have child_2 inherit the pipe's output stream as the child's
standard input stream and also have child_2 inherit (share) both of
the parent's stdout and stderr streams (this is all done using the
child's STARTUPINFO data structure).

                    parent
             +--------------------+
             |                    |
kbd--+------>> stdin       stdout >>----------------------------------------+--->
     |       |                    |                                         |
     |       |             stderr >>-------------------------------------+--|-->
     |       |                    |                                      |  |
     |       |                    |                                      |  |
     |       |        hPipeOut_Rd <<-----------+                         |  |
     |       |                    |            |                         |  |
     |       +--------------------+            |                         |  |
     |                                         |                         |  |
     |                                         |                         |  |
     |              child_1                    |        child_2          |  |
     |         +----------------+              |   +----------------+    |  |
     |         |                |      pipe    |   |                |    |  |
     +-------->> stdin   stdout >>---0======0--+-->> stdin   stdout >>---|--+
               |                |                  |                |    |
               |         stderr >>--------+        |         stderr >>---+
               |                |         |        |                |    |
               +----------------+         |        +----------------+    |
                                          |                              |
                                          +------------------------------+

Notice that the parent and child are sharing the output stream from
the pipe. But the parent has no need to read anything from the pipe,
so the parent can close its copy of that stream.

                    parent
             +--------------------+
             |                    |
kbd--+------>> stdin       stdout >>----------------------------------------+-->
     |       |                    |                                         |
     |       |             stderr >>-------------------------------------+--|-->
     |       |                    |                                      |  |
     |       +--------------------+                                      |  |
     |                                                                   |  |
     |                                                                   |  |
     |              child_1                             child_2          |  |
     |         +------------------+                +----------------+    |  |
     |         |                  |      pipe      |                |    |  |
     +-------->> stdin     stdout >>---0======0--->> stdin   stdout >>---|--+
               |                  |                |                |    |
               |           stderr >>-------+       |         stderr >>---+
               |                  |        |       |                |    |
               +------------------+        |       +----------------+    |
                                           |                             |
                                           +-----------------------------+
The pipeline is now complete.
