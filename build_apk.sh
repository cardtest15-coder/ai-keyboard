#!/bin/bash

echo "========================================"
echo "  AI Keyboard - APK Builder"
echo "========================================"
echo ""

# Проверка наличия Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ ANDROID_HOME не установлен"
    echo ""
    echo "Установите Android Studio и настройте ANDROID_HOME:"
    echo "export ANDROID_HOME=/путь/к/android/sdk"
    echo ""
    echo "Или скачайте APK из GitHub Actions:"
    echo "https://github.com/ваш-репозиторий/actions"
    exit 1
fi

echo "✅ Android SDK найден: $ANDROID_HOME"
echo ""

# Проверка Java
if ! command -v java &> /dev/null; then
    echo "❌ Java не установлена"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "✅ Java версия: $JAVA_VERSION"
echo ""

# Сборка APK
echo "🔨 Начинаю сборку APK..."
echo ""

cd android

# Если gradlew существует
if [ -f "gradlew" ]; then
    echo "Использую Gradle Wrapper..."
    ./gradlew assembleDebug
else
    echo "Использую системный Gradle..."
    gradle assembleDebug
fi

# Проверка результата
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "✅ APK успешно собран!"
    echo ""
    echo "📦 Расположение: android/app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    
    # Копирование в корень проекта
    cp app/build/outputs/apk/debug/app-debug.apk ../ai-keyboard-debug.apk
    echo "✅ APK скопирован в: ai-keyboard-debug.apk"
    echo ""
    
    # Размер файла
    SIZE=$(du -h ../ai-keyboard-debug.apk | cut -f1)
    echo "📊 Размер APK: $SIZE"
    echo ""
    
    # Инструкция по установке
    echo "========================================"
    echo "  Установка на устройство"
    echo "========================================"
    echo ""
    echo "Способ 1: Через ADB"
    echo "  adb install ai-keyboard-debug.apk"
    echo ""
    echo "Способ 2: Через USB"
    echo "  1. Скопируйте ai-keyboard-debug.apk на телефон"
    echo "  2. Откройте файл на телефоне"
    echo "  3. Разрешите установку из неизвестных источников"
    echo "  4. Установите"
    echo ""
else
    echo ""
    echo "❌ Ошибка сборки APK"
    echo ""
    echo "Проверьте:"
    echo "  - Установлен ли Android SDK"
    echo "  - Установлены ли Build Tools"
    echo "  - Приняты ли лицензии: sdkmanager --licenses"
    exit 1
fi