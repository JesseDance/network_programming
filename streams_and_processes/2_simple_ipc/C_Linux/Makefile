#
CC=gcc
CFLAGS=-g -std=c99 -pedantic -Wall -Wextra

all: command-line-arguments  environment-variables

clean:
	rm -f  command-line-arguments
	rm -f  environment-variables

command-line-arguments: command-line-arguments.c
	$(CC) $(CFLAGS) -o command-line-arguments  command-line-arguments.c

environment-variables: environment-variables.c
	$(CC) $(CFLAGS) -o environment-variables  environment-variables.c
