@echo off
for %%G in ("%cd%") do set "cur_dir=%%~nG"
set prompt=%cur_dir%$G 
cmd
