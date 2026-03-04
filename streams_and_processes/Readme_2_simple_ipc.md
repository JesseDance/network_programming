
Simple Forms of IPC
===================

As we mentioned in the last document, the simplest forms of
Inter-process Communication are:

1. command-line arguments
2. environment variables
3. program exit code (return value)
4. files
5. I/O redirection
6. pipes
7. sockets

In this document we will briefly go over the first four of these techniques.
I/O redirection and pipes will be explained in the document about Streams.
Sockets will be explained in the documents about network programming.

All the example code mentioned in this document is in the sub folder called
"simple_ipc" from the following zip file.

* <http://cs.pnw.edu/~rlkraft/cs33600/for-class/streams_and_processes.zip>


## Command-line arguments

As we saw above, the creation of a process is based on a command-line string.
This is true for the Java `ProcessBuilder` and `Runtime` classes and also the
Windows `CreateProcess()` function (but not the Linux `fork()` function). The
command-line string mimics what we would type in a command-prompt window to
run a program. A command-line to run a program starts with the name of the
program's executable file, followed by **command-line arguments**. Every
process running on a computer was started from a command-line and every
process has access to its command-line arguments.

A process's command-line arguments are collected into an array of `String` and
that array is a parameter to the program's `main()` method. Traditionally, that
array argument is named `args` (but it can be given any name you want).

```java
    public static void main(Strin[] args)
```

Since the command-line arguments are entries in an array, it is easy to
access them. Here is a complete Java program that prints out all of its
command-line arguments.

```java
public class CommandLineArguments
{
   public static void main(String[] args)
   {
      System.out.println("There were " + args.length + " command line arguments.");
      System.out.println("They are:");

      for (int i = 0; i < args.length; ++i)
      {
         System.out.println( args[i] );
      }
   }
}
```

Command-line arguments are a simple form of one way IPC between a parent
process and a child process. The parent process "sends" the command-line
arguments when it calls the `ProcessBuilder` constructor. The child process
"receives" the command-line arguments when it accesses its `args` array.

* <https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html>


We have said several times that *every* process is created from a command-line
and has command-line arguments (not just programs run from a command-prompt
window). Here is a way to verify this on a Windows computer.

Run Window's "Task Manager" program. There are several ways to start this
program.

1. Hold down the Ctrl and Shift keys and then strike the Esc key.
2. Click on the Windows Start menu, search for the "Run" item,
   type in the program name "taskmgr.exe", and click on OK.
3. Open a command-prompt window and at the prompt type the
   program name, "taskmgr.exe".
4. Go to the folder "C:\Windows\System32", find the file
   "taskmgr.exe" and double click on it.

When you have Task Manager running, click on the "Details" tab. Then
right-click on the "Name" column heading and click on "Select columns".
Scroll down the list in the pop-up window and look for the item called
"Command line" (the items are not in alphabetical order). Click on the
box next to this item to put a check-mark in the box. Click on the "OK"
button. In the Task Manager window you should now see a list of all the
processes you are running and there should be a "Command line" column for
each process. This lets you see the actual command-line string used to
create each of those processes.

If you start a program by double-clicking on a "shortcut icon" (for example,
a shortcut icon on your desktop, or an item in Window's Start Menu), then the
command-line (and command-line arguments) for the process is stored in the
icon. Right click on a shortcut icon and then click on the "Properties" item
from the pop-up menu. You should see a tabbed pop-up window with a tab called
"Shortcut". In that tab is a textbox labeled "Target" and in that textbox is
the process's command-line along with any command-line arguments. Since this
is a textbox, you can modify the command-line and its arguments to change what
the shortcut does when you double-click on it.



## Environment variables

The operating system provides every process with a data structure called the
**environment**. This is a data structure of key-value pairs, where each key
and value is a string. A key in the environment is called an **environment
variable**.

The operating system provides functions that a process can call to query and
modify its environment. A process can retrieve or modify the value of any
environment variable, a process can create new environment variables, and
a process can delete an existing environment variable (the four basic CRUD
operations).

When a parent process creates a child process, the child process inherits a
copy of the parent's (current) environment. This makes the environment a
simple form of IPC. The parent process can send small (string) messages to
the child process as the values of environment variables. This IPC is one
directional, from parent to child, and it must be done when the child
process is created.

The operating system maintains a master copy of an environment data structure
which can be used as a default environment for some processes (usually process
that are created by the operating system itself). On a Windows computer, you
can access and modify this master copy of the environment, but it is usually
a good idea to never do that.

Most programming languages provide functions that processes can use to query
and modify their environment. Java has the `getenv(String)` method in the
`java.lang.System` class for reading the value of an environment variable (but
Java does not have a method for changing the value of an environment variable).

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#getenv(java.lang.String)">java.lang.System.getenv(String)</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#getenv()">java.lang.System.getenv()</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/java.lang.ProcessBuilder.html#environment()">ProcessBuilder.environment()</a></li>
</ul>

* <https://docs.oracle.com/javase/tutorial/essential/environment/env.html>


You can see your computer's default set of environment variables by opening
a command-prompt window and at the prompt typing the following simple
command-line.

```text
    > set
```

The `set` command will print out a list of all the current environment
variables and their values. If you want to see the value of just one
environment variable, put its name as a command-line argument after the
`set` command. For example the `prompt` environment variable determines
what your command-line prompt looks like.

```text
    > set prompt
```

You can use the `set` command to change the value of an environment variable.
For example, the following command will change the prompt in the current
command-prompt window (but not in other command-prompt windows).

```text
    > set prompt=$T$G
```

You can create a new environment variable by defining it using the `set`
command.

```text
    > set hello=there
    > set
```

The second command displays all the environment variables so that you can see
that the new `hello` variable is there.

You can remove an environment variable by setting it value to be "empty".

```text
    > set hello=
    > set
```

Notice that the `hello` variable is no longer in the environment.

You can use the ProcessExplorer.exe or SystemInformer.exe programs to
observe the environment data structure of any running process. Start up
either ProcessExplorer.exe or SystemInformer.exe. Right click on any process
and choose the "Properties" item from the pop-up menu. You should see a tabbed
pop-up window with one tab labeled "Environment". Click on that tab and you
will see a list of all of the environment variables that the process inherited
from its parent process. On a Windows computer, most of the processes that
you look at will have pretty much the same environment variables. Windows
programs do not usually make much use of environment variables (other than
the standard ones set by the operating system). On Unix/Linux computers,
lots of programs make use of lots of environment variables.


* <https://ss64.com/nt/set.html>
* <https://ss64.com/nt/syntax-variables.html>
* <https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/set_1>
* <https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmd#using-environment-variables>

* <https://ss64.com/nt/prompt.html>
* <https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/prompt>



## Exit code

Just as a method will have a return value, a process will have an **exit
code**. Methods let you declare the type of their return value, but all
processes have the same type for their exit code, an integer.

The purpose of the exit code is much more specific than a return value from
a method. For the most part, a process uses its exit code to send a simple
"success or failure" message to its parent process. Many programs are
designed to return the value `0` to mean a "successful" completion of the
process. Any non-zero exit code means that the process "failed" somehow, and
the value of the exit code can be considered an "error code" that denotes the
failure mode. (Notice how this kind of mimics the C definition that the
integer value `0` is `false` and any non-zero integer is `true`, but here
the exit code `0` means "success" and any non-zero exit code means "failure".)

A Java program can set its exit code using the static method
`java.lang.System.exit(int)`.

A Java program that is the parent of a process can use the
`java.lang.Process.exitValue()` method to retrieve the exit code from its
child process after the child process has terminated (the child process
does *not* need to be a Java program).

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#exit(int)">java.lang.System.exit(int)</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html#exitValue()">java.lang.Process.exitValue()</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Process.html#waitFor()">java.lang.Process.waitFor()</a></li>
</ul>


* <https://man7.org/linux/man-pages/man3/exit.3.html>
* <https://man7.org/linux/man-pages/man1/bash.1.html#EXIT_STATUS>
* <https://en.cppreference.com/w/c/program/exit.html>
* <https://en.cppreference.com/w/c/program/EXIT_status.html>


* <https://ss64.com/nt/exit.html>
* <https://learn.microsoft.com/en-us/windows/win32/debug/system-error-codes>



## Files

A simple form if IPC is for a parent process to write some data into a file
and then expect its child process to open the file and read the data. The
parent process might use some other form of IPC (an environment variable or
a command-line argument) to let the child know the name of the file, or the
parent and child programs might have the name of the file coded directly
into them.


### Configuration files

A common example of this is "configuration files". These are files with
a specific name that a program is coded to open and retrieve data from.
The data in a configuration file is used to set the initial configuration
of the program's state. A parent process can open its child's configuration
file and update values in the file to configure how the child process
initially behaves.

* <https://en.wikipedia.org/wiki/Configuration_file>
* <https://www.redhat.com/en/topics/linux/what-configuration-file>
* <https://configu.com/blog/configuration-files-types-examples-and-5-critical-best-practices/>


### Properties files

Java provides built in support for simple configuration files. Java
calls these files **properties files** and they are implemented by the
`java.util.Properties` class. A properties file is a text file with the
extension `.properties`. The contents of the file are key-value pairs,
one pair per line (but a properties file can also be written as an XML file).

Suppose you have a simple properties file called "myApp.properties" and it
contains the following three key-value pairs.

```text
myArgument1 = true
myArgument2 = 5
myArgument3 = 3.14
```

Here is a brief outline of how you could read this properties file.

```java
    boolean p1 = false; // default value
    int p2 = 0;         // default value
    double p3 = 0.0;    // default value
    final Properties properties = new Properties();
    try (final var fis = new FileInputStream(new File("myApp.properties")))
    {
       properties.load(fis);
       final String op1 = properties.getProperty("myArgument1");
       final String op2 = properties.getProperty("myArgument2");
       final String op2 = properties.getProperty("myArgument3");
       if (op1 != null)     {p1 = Boolean.parseBoolen(op1);}
       if (op2 != null) try {p2 = Integer.parseInt(op2);}   catch (NumberFormatException e){}
       if (op2 != null) try {p2 = Double.parseDouble(op2);} catch (NumberFormatException e){}
    }
    catch (FileNotFoundException e)
    {
       // Ignore the configuration file.
    }
    catch (IOException e)
    {
       e.printStackTrace(System.err);
       System.exit(-1);
    }
```

Notice how every key has a `String` value. Just because a key's value is "true"
does not mean that the value is a `boolean`. Each key's value needs to be parsed
to a value with the appropriate Java type. In this simple code, if a key's value
doesn't parse (the parser throws an exception) then this code just ignores that
key. This is usually a good idea, since we do not want to crash a program just
because there is a syntax error in its config file. It would be a good idea
though to log some kind of simple error message.

Also notice that we ignore the `FileNotFoundException`. It is usually not
considered an error to not have a configuration file. If the config file
is absent, then the program should proceed with all its default values. But
if there is an error while reading the config file, then that is a problem
that needs to be logged and may be a good reason to halt the program.

<ul>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Properties.html">java.util.Properties</a></li>
<li><a href="https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Properties.html#load(java.io.Reader)">java.util.Properties.load(Reader)</a></li>
</ul>

* <https://docs.oracle.com/javase/tutorial/essential/environment/properties.html>
* <https://www.baeldung.com/java-properties>


### Layered configuration

When a parent process creates a child process, it is common for the parent to
"configure" the child process. The parent process wants to send information
to the child process that specifies how the child should behave. The
configuration information passed from the parent to the child is an
example of IPC.

A parent process needing to configure a child process is such a common design
pattern that there is a (semi) standard hierarchy of configuration information.

The child process will have certain variables in its code that determine
aspects of the program's behavior. Changing the values of these variables
changes how the program acts. Configuring the program is then a matter of
giving these variables values when the program starts executing. Most
programs set the values of these kinds of variables by going through a
hierarchy of changes.

First, the program gives every configuration variable a default value.

Second, the program looks for a configuration file and uses values from
the config file to update is configuration variables.

Third, the program looks for environment variables that can update the
program's configuration variables.

Fourth, the program looks for command-line arguments that can update the
program's configuration variables.

A simple example of this hierarchy is in the folder
"streams_and_processes/2_simple_ipc/simple_ipc_examples/example 3".


* <https://stackoverflow.com/questions/6133517/parse-config-files-environment-and-command-line-arguments>
* <https://docs.aws.amazon.com/cli/v1/userguide/cli-chap-configure.html>
* <https://dzone.com/articles/configuration-files>
* <https://docs.rs/config/latest/config/>
* <https://github.com/rust-cli/config-rs?tab=readme-ov-file>
