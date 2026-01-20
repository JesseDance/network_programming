
   Simple Inter-process Communication

Two of the simplest forms of Inter-process Communication are
"command-line arguments" and "environment variables".


Command-line arguments are passed to a process when it is created. It is
important to realize that ALL running programs have command-line arguments
(not just programs run from a "command-line"). Here is one way to prove
that every running program has a "command-line" and also "command-line
arguments".

Run Window's "Task Manager" program. There are several ways to run this
program.

1.) Hold down the Ctrl and Shift keys and then strike the Esc key.
2.) Click on the Windows Start menu, search for the "Run" item,
    type in the program name taskmgr.exe, and click on OK.
3.) Open a command-prompt window and at the prompt type the
    program name, taskmgr.exe.
4.) Go to the folder "C:\Windows\System32", find the file
    taskmgr.exe and double click on it.

When you have Task Manager running, click on the "Details" tab. Then
right-click on the "Name" column heading and click on "Select columns".
Scroll down the list in the pop-up window and look for the item called
"Command Line" (the items are not in alphabetical order). Click on the
box next to this item to put a check-mark in the box. Click on the "OK"
button. In the Task Manager window you should now see a list of all the
processes you are running and there should be a "Command line" column for
each process. This lets you see the actual command-line used to start each
of those processes.

If you start a GUI program by double-clicking on a "shortcut icon" (for
example, a shortcut icon from your desktop, or an item from Window's
Start Menu), the command-line for the process is stored in the icon. Right
click on a shortcut icon and click on the bottom item from the pop-up menu
(the "Properties" item). You should see a tabbed pop-up window with a tab
called "Shortcut". In that tab is a textbox labeled "Target" and in that
textbox is the process's command-line along with any command-line arguments.
Since this is a textbox, you can modify the command-line and its arguments
to change what the shortcut does when you double-click on it.



Environment variables are similar to command-line arguments in that they are
fairly small messages, they can only be passed in one direction (from parent
to descendant processes) and the messages must be sent at the time the child
process is created. Just as ALL processes have command-line arguments, so do
ALL processes have environment variables.

You can use the ProcessExplorer.exe and SystemInformer.exe programs to
observe the environment variables for each running process. Start up either
ProcessExplorer.exe or SystemInformer.exe. Right click on any process and
choose the "Properties" item from the pop-up menu. You should see a tabbed
pop-up window with one tab labeled "Environment". Click on that tab and you
will see a list of all of the environment variables that the process inherited
from its parent process. On a Windows computer, most of the processes that
you look at will have pretty much the exact same environment variables.
Windows programs do not usually make much use of environment variables
(other than the standard ones set by the operating system). On Unix/Linux
computers, lots of programs make use of lots of environment variables.

You can see your computer's default set of environment variables by opening
a command-prompt window and at the prompt typing the following simple
command-line

    > set

The set command will print out a list of all the current environment
variables and their values. If you want to see the value of just one
environment variable, put its name as a command-line argument after the
set command. For example the "prompt" environment variable determines
what your command-line prompt looks like.

    > set prompt

You can use the set command to change the value of an environment variable.
For example, the following command will change the prompt in the current
command-prompt window (but not in other command-prompt windows).

    > set prompt=$T$G
