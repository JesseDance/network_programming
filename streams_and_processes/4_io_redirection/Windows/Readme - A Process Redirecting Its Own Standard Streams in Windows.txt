
    A Process Redirecting Its Own Standard Streams in Windows	

As mentioned in the previous folder, Windows uses "handles" as the way for
processes to identify resources given to them by the operating system. In
particular, since every process is created with three standard I/O streams,
every Windows process will need to be given three "standard handles" to
those standard streams. The Windows function
      SetStdHandle()
lets a Windows process change the stream that one of those "standard handles"
refers to. So if a Windows process should open a new I/O stream (by calling
the CreateFile() function), then the process can use SetStdHandle() to
substitute the new stream for one of its original standard streams.

The RedirectStandardStreams.c file demonstrates using the SetStdhandle()
function. That program calls CreateFile() two times to open a new input and
a new output stream and then is calls SetStdHandle() twice to redirect
stdin and stdout to those two new streams.

Here are a sequence of picture illustrating the sequence of streams as
the RedirectStandardStreams.exe process runs.

Suppose that initially stdin is the keyboard and stdin and stderr are
the console window.
                    process
               +------------------+
               |                  |
   keyboard--->> stdin     stdout >>---------> console window
               |                  |
               |           stderr >>------> console window
               |                  |
               +------------------+

The process first calls CloseHandle() to close the stdin and stdout
file handles, since those two files will no longer be used.

                    process
               +------------------+
               |                  |
           --->> stdin     stdout >>--->
               |                  |
               |           stderr >>------> console window
               |                  |
               +------------------+

The process then calls CreateFile() to open an input stream
(using a temporary input handle).

                    process
               +-------------------+
               |                   |
           --->> stdin      stdout >>--->
               |                   |
               |            stderr >>------> console window
               |                   |
 Readme.txt--->> hIn               |
               |                   |
               +-------------------+

Then the process calls CreateFile() again to open an output stream (using
a temporary output handle).

                    process
               +-------------------+
               |                   |
           --->> stdin      stdout >>--->
               |                   |
               |            stderr >>------> console window
               |                   |
 Readme.txt--->> hIn          hOut >>-----> result.txt
               |                   |
               +-------------------+

Now the process calls SetStdHandle() two times to redirect stdin and
stdout to the new file streams.

                    process
               +-------------------+
               |                   |
 Readme.txt-+->> stdin      stdout >>--+-----> result.txt
            |  |                   |   |
            |  |            stderr >>------> console window
            |  |                   |   |
            +->> hIn          hOut >>--+
               |                   |
               +-------------------+

At this point, the two temporary handles are no longer needed and can be
set to INVALID_HANDLE_VALUE (why would closing these two handles be
a mistake?).

                    process
               +-------------------+
               |                   |
 Readme.txt--->> stdin      stdout >>--------> result.txt
               |                   |
               |            stderr >>------> console window
               |                   |
               |                   |
               +-------------------+

The I/O redirection is complete and the process can treat the two new files
streams as its stdin and stdout.


At this point the RedirectStandardStreams.c program acts as a simple echo
filter between stdin and stdout. But notice that the program uses the low
level Windows I/O functions ReadFile() and WriteFile(). Why not use the
higher level C functions getchar() and printf()? The answer is that they
no longer work the way you would expect them to. Remember that Standard C
functions do not use Window's handles. Instead, Standard C functions use
pointers to FILE objects. The I/O redirection done by the SetStdHandle()
function does NOT redirect the C Library's standard FILE objects (as you
would expect it to). The file
   RedirectStandardStreams_broken.c
demonstrates this by using the C functions getchar() and printf() after
the calls to SetStdHandle(). These two functions still work on the
original stdin and stdout streams! They have not been redirected.

The file
   RedirectStandardStreams_kind_of_broken.c
demonstrates a kind of fix for this situation. It shows how to re-sync the
C Library's standard FILE objects with the Windows standard handles. The
code used in this program "works" but it is not correct since it works by
breaking the FILE object interface (the program writes to a FILE object
pointer, which is something that should never be done).


In this folder there are also the two files
   EchoFromFileToFile.c
   Echo_weird.c
These two programs are there mostly to make you think about the difference
between these three command lines (which, more or less, do the same thing).

   C:\> RedirectStandardStreams.exe
   C:\> EchoFromFileToFile.exe
   C:\> Echo_weird.exe < Readme.txt > result.txt
