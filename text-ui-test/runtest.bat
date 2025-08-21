@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT 2>nul

REM compile the code into the bin folder using full JDK path
"C:\Users\cavan\.jdks\openjdk-24.0.2+12-54\bin\javac" -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt and redirect the output to ACTUAL.TXT
"C:\Users\cavan\.jdks\openjdk-24.0.2+12-54\bin\java" -classpath ..\bin GoldenKnight < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
