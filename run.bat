@echo off
echo ========================================
echo AI Keyboard - Full Stack Application
echo ========================================
echo.
echo Choose an option:
echo 1. Start Backend Server
echo 2. Install Android App
echo 3. Install Backend Dependencies
echo 4. Exit
echo.
set /p choice=Enter your choice (1-4): 

if "%choice%"=="1" (
    echo Starting Backend Server...
    cd backend
    npm start
) else if "%choice%"=="2" (
    echo Installing Android App...
    cd android
    gradlew.bat installDebug
) else if "%choice%"=="3" (
    echo Installing Backend Dependencies...
    cd backend
    call npm install
    echo.
    echo Dependencies installed successfully!
    pause
) else if "%choice%"=="4" (
    exit
) else (
    echo Invalid choice!
    pause
)