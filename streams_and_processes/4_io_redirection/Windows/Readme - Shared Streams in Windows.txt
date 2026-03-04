
   Shared Streams in Windows

Windows is similar to Unix. When a parent process creates a
child process, the child automatically inherits the parent's
standard streams (see the file CreateChildInheritStdStreams.c).

There is one exception to this. If the parent asks for the child
to have its own console window, then the child does not
automatically inherit the standard streams (the new console's
standard streams are the default ones). But it is pretty easy
to redirect the standard streams of the new console back to
the parent's standard streams.

This folder also contains two pairs of examples that demonstrate
what happens when two processes share an input or an output stream.
   ParentChildShareStdout_parent.c
   ParentChildShareStdout_child.c
and
   ParentChildShareStdin_parent.c
   ParentChildShareStdin_child.c
There are a number of experiments that you should try with these files.

Run
   ParentChildShareStdin_parent.exe
   ParentChildShareStdin_child.exe
with a shared keyboard as stdin. Find the two processes in the
Task Manager. Type ^Z at the shared keyboard. Use Task Manager
to see which process gets the EOF condition. Type some more at
the keyboard to see that the other process is still running.
Type ^Z again to close the second process.


Introduce random pauses (using the Sleep() function) into both
ParentChildShareStdin_parent.c and ParentChildShareStdin_child.c
so that they do not just alternate reading from the shared input
stream.


Change the keyboard from cooked input to raw input. Try changing
just one of the two processes to raw keyboard  input. Try changing
both to raw input.
