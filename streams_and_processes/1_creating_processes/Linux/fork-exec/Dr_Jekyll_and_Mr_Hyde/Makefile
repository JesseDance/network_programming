#
#
CC=gcc
CFLAGS=-g -std=c99 -pedantic -Wall -Wextra

all: Dr_Jekyll  Mr_Hyde  Dr_Jekyll_and_Mr_Hyde  Dr_Jekyll_and_Mr_Hyde_v2

clean:
	rm -f Dr_Jekyll  Mr_Hyde  Dr_Jekyll_and_Mr_Hyde  Dr_Jekyll_and_Mr_Hyde_v2

Dr_Jekyll: Dr_Jekyll.c
	$(CC) $(CFLAGS) -o Dr_Jekyll  Dr_Jekyll.c

Mr_Hyde: Mr_Hyde.c
	$(CC) $(CFLAGS) -o Mr_Hyde  Mr_Hyde.c

Dr_Jekyll_and_Mr_Hyde: Dr_Jekyll_and_Mr_Hyde.c
	$(CC) $(CFLAGS) -o Dr_Jekyll_and_Mr_Hyde  Dr_Jekyll_and_Mr_Hyde.c

Dr_Jekyll_and_Mr_Hyde_v2: Dr_Jekyll_and_Mr_Hyde_v2.c
	$(CC) $(CFLAGS) -o Dr_Jekyll_and_Mr_Hyde_v2  Dr_Jekyll_and_Mr_Hyde_v2.c
