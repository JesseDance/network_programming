
   A Linux Process Redirecting Its Own Standard Streams

As mentioned in the previous folder, Linux uses "file descriptors" as the
way for processes to identify resources given to them by the operating
system. In particular, since every process is created with three standard
I/O streams, every Linux process will need to be given three "standard file
descriptors" to those standard streams. File descriptors are integers and
the three standard file descriptors are

   0 for standard input,
   1 for standard output,
   2 for standard error.

Linux defines three symbolic constants for these file descriptors.

    #define  STDIN_FILENO 0
    #define STDOUT_FILENO 1
    #define STDERR_FILENO 2

So we can think of a Linux process as looking like this.

                   process
              +--------------+
              |              |
  keyboard--->> 0          1 >>---------> console window
              |              |
              |            2 >>------> console window
              |              |
              +--------------+

Or, more awkwardly, like this.

                         process
              +------------------------------+
              |                              |
  keyboard--->> STDIN_FILENO   STDOUT_FILENO >>------> console window
              |                              |
              |                STDERR_FILENO >>---> console window
              |                              |
              +------------------------------+


The most important fact to know about file descriptors is that whenever
you ask the operating system for a new one (say by opening a file) the
operating system always returns the lowest available file descriptor
number.

There are three standard techniques for redirecting the standard streams.
The three techniques are referred to as
   close-open,
   open-close-dup-close,
   open-dup2-close.
All three techniques make use of the above fundamental fact about new file
descriptors. See the discussion below and also the three files
   redirect_standard_in_ver_1.c
   redirect_standard_in_ver_2.c
   redirect_standard_in_ver_3.c


Linux I/O redirection works better than Windows in a really important way.
In all the examples in this folder, after we do the I/O redirection we use
Standard C library functions to do I/O. We do not need to use the Linux low
level I/O functions. But in Windows, after we "redirect" the standard streams,
the high level Standard C library functions don't work with the redirected
streams (they still use the original streams). We have to make use of Windows
low level I/O functions.


a.) close-open

Here is how a Linux process can redirect its standard input stream to a file.
using the "close-open" technique. This uses the fundamental Linux idea that
when you open() a file, the operating system always returns the lowest
available file descriptor number. So if a process starts like this

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

and calls close(0), then we get this.

                   process
              +---------------+
              |               |
              |             1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

A call to open("data.txt") then results in this (since 0
is now the lowest available file descriptor).

                   process
              +---------------+
              |               |
  data.txt--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

So now the data file is using the standard input file descriptor,



b.) open-close-dup-close

Here is how a Linux process can redirect its standard input stream to
a file using the "open-close-dup-close" technique. If a process starts
like this
                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

and calls open("data.txt"), then we get this (since 3 is the lowest
available file descriptor).

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
  data.txt--->> 3             |
              |               |
              +---------------+

A call to close(0) then results in this.

                   process
              +---------------+
              |               |
              |             1 >>---------> console window
              |               |
              |             2 >>------> console window
  data.txt--->> 3             |
              |               |
              +---------------+

A call to dup(3) then results in this because dup() will copy the given
file descriptor to the lowest available file descriptor number (which is 0).

                   process
              +---------------+
              |               |
           +->> 0           1 >>---------> console window
           |  |               |
           |  |             2 >>------> console window
  data.txt-+->> 3             |
              |               |
              +---------------+

Finally we call close(3) since we no longer need that file descriptor.

                   process
              +---------------+
              |               |
  data.txt--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

So now the data file is using the standard input file descriptor,


c.) open-dup2-close

Here is how a Linux process can redirect its standard input stream
to a file using the "open-dup2-close" technique. If a process starts
like this
                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

and calls open("data.txt"), then we get this (since 3 is the lowest
available file descriptor).

                   process
              +---------------+
              |               |
  keyboard--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
  data.txt--->> 3             |
              |               |
              +---------------+

A call to dup2(3, 0) then results in this because dup2() will close file
descriptor 0 and copy file descriptor 3 to 0.

                   process
              +---------------+
              |               |
           +->> 0           1 >>---------> console window
           |  |               |
           |  |             2 >>------> console window
  data.txt-+->> 3             |
              |               |
              +---------------+

Finally we call close(3) since we no longer need that file descriptor.

                   process
              +---------------+
              |               |
  data.txt--->> 0           1 >>---------> console window
              |               |
              |             2 >>------> console window
              |               |
              +---------------+

So now the data file is using the standard input file descriptor,


Exercise: Rewrite the three files
   redirect_standard_in_ver_1.c
   redirect_standard_in_ver_2.c
   redirect_standard_in_ver_3.c
to create three new files
   redirect_standard_out_ver_1.c
   redirect_standard_out_ver_2.c
   redirect_standard_out_ver_3.c
that use the three basic techniques to redirect the standard output
stream.

Exercise: Combine
   redirect_standard_in_ver_1.c
and
   redirect_standard_out_ver_1.c
to create an example of a process that redirects both its standard
input and output streams to files.
