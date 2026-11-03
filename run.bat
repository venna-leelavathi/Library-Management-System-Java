@echo off
if not exist bin mkdir bin

echo Compiling...
javac -cp "lib/*" -d bin src/LibraryApp.java src/model/*.java src/dao/*.java src/util/*.java
if errorlevel 1 (
  echo Compile failed. Check Java is installed and mysql connector jar is in lib\
  pause
  exit /b 1
)

echo Running...
java -cp "bin;lib/*" LibraryApp
pause
