@echo off

echo Compiling Java files...
javac -cp "libs/postgresql-42.7.5.jar" -d bin src/*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    exit /b %ERRORLEVEL%
)

echo Running the application...
java -cp "bin;libs/postgresql-42.7.5.jar" Main

pause
