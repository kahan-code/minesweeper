@echo off
cd /d E:\minesweeper

if not exist bin mkdir bin

echo Compiling Java files...
javac -d bin src\kahan\*.java

if %errorlevel% equ 0 (
    echo Running game...
    java -cp bin kahan.Main
) else (
    echo Compilation failed.
)

pause