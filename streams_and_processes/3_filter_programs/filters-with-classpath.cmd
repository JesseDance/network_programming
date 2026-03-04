@echo off
rem  Set the CLASSPATH environment variable so that you do
rem  not need to set the classpath in every command-line.
rem  Set the PROMPT to a manageable length.
rem
cmd /k "set CLASSPATH=.;filters.jar & set PROMPT=filters$G "
rem
rem  The above line is equivalent to the following three lines.
rem set CLASSPATH=.;filters.jar
rem set PROMPT=filters$G 
rem cmd
