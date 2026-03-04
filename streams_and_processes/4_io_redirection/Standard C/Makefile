#
# Type   
#      make
# to compile all the programs in this folder.
#
CC=gcc
CFLAGS=-g -std=c99 -pedantic -Wall -Wextra

all: echo  echo_from_file_to_file  redirect_standard_streams

echo: echo.c
	$(CC) $(CFLAGS) -o echo  echo.c

echo_from_file_to_file: echo_from_file_to_file.c
	$(CC) $(CFLAGS) -o echo_from_file_to_file  echo_from_file_to_file.c

redirect_standard_streams: redirect_standard_streams.c
	$(CC) $(CFLAGS) -o redirect_standard_streams  redirect_standard_streams.c

clean:
	rm -f  echo  echo_from_file_to_file  redirect_standard_streams
