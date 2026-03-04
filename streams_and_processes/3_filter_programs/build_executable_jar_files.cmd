for  %%G in (*.class) do (
   jar cvfe  %%~nG.jar  %%~nG  %%G
)
pause
