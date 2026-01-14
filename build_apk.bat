@echo off
echo ========================================
echo   AI Keyboard - APK Builder
echo ========================================
echo.

REM Проверка Java
where java >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ОШИБКА] Java не установлена
    echo.
    echo Установите Java 17+ с: https://adoptium.net/
    pause
    exit /b 1
)

for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do set JAVA_VERSION=%%i
echo [OK] Java версия: %JAVA_VERSION%
echo.

REM Проверка Android SDK
if "%ANDROID_HOME%"=="" (
    echo [ОШИБКА] ANDROID_HOME не установлен
    echo.
    echo Установите Android Studio: https://developer.android.com/studio
    echo Или скачайте APK из GitHub Actions
    pause
    exit /b 1
)

echo [OK] Android SDK: %ANDROID_HOME%
echo.

REM Сборка APK
echo [ИНФО] Сборка APK...
echo.

cd android

if exist "gradlew.bat" (
    echo Использую Gradle Wrapper...
    call gradlew.bat assembleDebug
) else (
    echo Использую системный Gradle...
    call gradle assembleDebug
)

REM Проверка результата
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo.
    echo ========================================
    echo [УСПЕХ] APK собран!
    echo ========================================
    echo.
    echo [ПУТЬ] android\app\build\outputs\apk\debug\app-debug.apk
    echo.
    
    REM Копирование в корень
    copy "app\build\outputs\apk\debug\app-debug.apk" "..\ai-keyboard-debug.apk" >nul
    echo [КОПИЯ] ai-keyboard-debug.apk
    echo.
    
    REM Размер файла
    for %%F in ("..\ai-keyboard-debug.apk") do set SIZE=%%~zF
    set /a SIZE_MB=%SIZE% / 1048576
    echo [РАЗМЕР] %SIZE_MB% MB
    echo.
    
    echo ========================================
    echo   Установка на устройство
    echo ========================================
    echo.
    echo Способ 1: Через ADB
    echo   adb install ai-keyboard-debug.apk
    echo.
    echo Способ 2: Через USB
    echo   1. Скопируйте ai-keyboard-debug.apk на телефон
    echo   2. Откройте файл на телефоне
    echo   3. Разрешите установку из неизвестных источников
    echo   4. Установите
    echo.
    
    pause
) else (
    echo.
    echo [ОШИБКА] APK не собран
    echo.
    echo Проверьте:
    echo   - Установлен ли Android SDK
    echo   - Установлены ли Build Tools
    echo   - Приняты ли лицензии: sdkmanager --licenses
    echo.
    pause
    exit /b 1
)

cd ..