#
CC = gcc
CFLAGS = -g -std=c99 -pedantic -Wall -Wextra
RM = rm -fv

# See Section 5.4.3 of "GNU Make Manual"
# 08 July 2002, GNU make Version 3.80.
all: $(patsubst %.c,%,$(wildcard *.c))

# Use a simple suffix rule to compile any c file.
# See Section 11.7 of "GNU Make Manual"
# 08 July 2002, GNU make Version 3.80.
.c.:
	$(CC) $(CFLAGS) -o $@ $<

# delete only the files that we made
clean:
	$(RM) $(patsubst %.c,%,$(wildcard *.c))