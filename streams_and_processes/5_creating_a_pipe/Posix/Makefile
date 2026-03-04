#
# Type  
#   $ make 
# to compile all the programs in this folder.
#
CC=gcc
CFLAGS=-g -std=c99 -pedantic -Wall -Wextra

all: double  to_upper_case  create-pipeline-ver1a  create-pipeline-ver1b  create-pipeline-ver2  create-pipeline-ver2e  create-pipeline-ver3  create-pipeline-ver3e

double: double.c
	$(CC) $(CFLAGS) -o double  double.c

to_upper_case: to_upper_case.c
	$(CC) $(CFLAGS) -o to_upper_case  to_upper_case.c

create-pipeline-ver1a: create-pipeline-ver1a.c
	$(CC) $(CFLAGS) -o create-pipeline-ver1a  create-pipeline-ver1a.c

create-pipeline-ver1b: create-pipeline-ver1b.c
	$(CC) $(CFLAGS) -o create-pipeline-ver1b  create-pipeline-ver1b.c

create-pipeline-ver2: create-pipeline-ver2.c
	$(CC) $(CFLAGS) -o create-pipeline-ver2   create-pipeline-ver2.c

create-pipeline-ver2e: create-pipeline-ver2e.c
	$(CC) $(CFLAGS) -o create-pipeline-ver2e  create-pipeline-ver2e.c

create-pipeline-ver3: create-pipeline-ver3.c
	$(CC) $(CFLAGS) -o create-pipeline-ver3   create-pipeline-ver3.c

create-pipeline-ver3e: create-pipeline-ver3e.c
	$(CC) $(CFLAGS) -o create-pipeline-ver3e  create-pipeline-ver3e.c

clean:
	rm -f  double  to_upper_case  create-pipeline-ver1a  create-pipeline-ver1b  create-pipeline-ver2  create-pipeline-ver2e  create-pipeline-ver3  create-pipeline-ver3e
