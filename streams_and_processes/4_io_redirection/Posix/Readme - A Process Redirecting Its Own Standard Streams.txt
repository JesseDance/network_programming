
   A Process Redirecting Its Own Standard Streams

The examples in this folder show how a process can redirect its
own standard streams to files. By this we mean that a process might
be running with the following configuration of its standard streams.

                    process
              +-------------------+
              |                   |
  keyboard--->> stdin      stdout >>-----+--> console window
              |                   |      |
              |            stderr >>-----+
              |                   |
              +-------------------+

We want to see how the process might, while it is running, change its
standard streams so that they look like the following picture, with
stdin and stdout connected to specific files chosen by the process.

                    process
              +-------------------+
              |                   |
  data.txt--->> stdin      stdout >>------> result.txt
              |                   |
              |            stderr >>-----> console window
              |                   |
              +-------------------+

This does not, by itself, seem like a very useful thing to be able
to do. It is almost exactly the same idea as just opening new file
streams for reading or writing (as in the picture below). The main
difference between the above picture and the picture below is that
the process above can use the scanf() and printf() functions to read
and write the files data.txt and result.txt while the process below
would have to use the fscanf() and fprintf() functions to read and
write to data.txt and result.txt.

                    process
              +-------------------+
              |                   |
  keyboard--->> stdin      stdout >>-----+---> console window
              |                   |      |
              |            stderr >>-----+
              |                   |
  data.txt--->> stream3   stream4 >>-----> result.txt
              |                   |
              +-------------------+

But seeing how a process can redirect its standard streams is a good
starting point for understanding the techniques that are used for IPC.

An important reason for a process to redirect its own stdin and stdout
streams is so that it can have a child process "inherit" those redirected
streams. We will discuss a child process "inheriting" its parent's standard
streams in the next two folders.
