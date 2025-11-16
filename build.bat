@echo off
if not exist out mkdir out

javac -d out Main.java src\datastructures\*.java src\algorithms\*.java src\models\*.java

if %errorlevel% neq 0 (
    echo Build failed.
    exit /b 1
)

echo Build successful.