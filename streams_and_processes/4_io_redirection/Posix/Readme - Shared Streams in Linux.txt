
   Shared Streams in Linux

The main shell loop looks like this.

  while ( ! end_of_input )
  {
    get_command();      // read a command from user
    rv = fork();        // create a child to run the command
    if ( 0 == rv )      // child process,
      exec( command );  //   run the command (and interacts with the user)
    else                // parent process,
      wait_for_child(); //   waits for child to complete
 }

One issue with this loop is how does the user switch from
interacting with the shell process (when the user types in
a command) to interacting with the child process (when the
command is running) to interacting again with the shell
(after the command exits)?

The answer has to do with a tricky use of "shared streams".

Here is a more precise definition of a "shared stream". Two file
descriptors (either in the same process or in different processes)
share a stream if the two file descriptors point to the same file
object in the operating system's "open file table". If two file
descriptors share a stream, then they certainly also share the file
that the stream is connected to. But if two file descriptors share
a file, they may or may not be sharing a stream. (If two file
descriptors point to two different entries in the "open file table",
but both of those entries point to the same entry in the operating
system's "inode table", then the two file descriptors share a file
but don't share a stream.) The main idea when sharing a stream is that
the two file descriptors share the same "offset" into the file (file
offsets are stored in the "open file table", not in the "inode table").


When a parent process creates a child process, the child automatically
inherits (and shares) the parent's standard streams. See the file
   create_child_inherit_std_streams.c

Here is an illustration of a parent and a child sharing an input stream
and an output stream (using the stdin and stdout file descriptors). The
streams themselves could be from files (on a drive) or devices, like the
keyboard or the console window (or even a network interface device).

                     parent
                 +------------+
                 |            |
            +--->> 0        1 >>-------+
            |    |            |        |
            |    |          2 >>-->    |
            |    |            |        |
            |    +------------+        |
 in-stream--+                          +-----> out-stream
 (shared)   |           child          |       (shared)
            |       +------------+     |
            |       |            |     |
            +------>> 0        1 >>----+
                    |            |
                    |          2 >>-->
                    |            |
                    +------------+


This folder contains two examples that demonstrate what happens when
two processes share an input or an output stream.
   parent_child_share_stdout.c
and
   parent_child_share_stdin.c

There are a number of experiments that you should try with these files.

Run
   parent_child_share_stdin
with a shared keyboard as stdin. Find the two processes with top.
Type ^D at the shared keyboard. Use top to see which process gets
the EOF condition. Type some more at the keyboard to see that the
other process is still running. Type ^D again to close the second
process.


Introduce random pauses (using the sleep() function) into both
processes in parent_child_share_stdin.c so that they do not just
alternate reading from the shared input stream.


Change the keyboard from cooked input to raw input. Try changing
just one of the two processes to raw keyboard  input. Try changing
both to raw input.
