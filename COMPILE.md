# Инструкция по компиляции Android APK

К сожалению, автоматическая компиляция APK требует полного Android SDK и дополнительных инструментов, которые не доступны в текущей среде. Однако вы можете собрать APK самостоятельно.

## Вариант 1: Через Android Studio (Рекомендуется)

1. **Установка Android Studio**
   - Скачайте: https://developer.android.com/studio
   - Установите Android Studio
   - Установите Android SDK (API 34)

2. **Открытие проекта**
   ```bash
   Откройте папку android/ в Android Studio
   ```

3. **Сборка APK**
   - В меню: Build → Build Bundle(s) / APK(s) → Build APK(s)
   - APK будет в: `android/app/build/outputs/apk/debug/app-debug.apk`

## Вариант 2: Через командную строку

### Требования:
- Java JDK 17+
- Android SDK
- Android Build Tools

### Команды:

```bash
# 1. Установите переменные окружения
export ANDROID_HOME=/path/to/android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# 2. Перейдите в папку android
cd android

# 3. Скачайте зависимости
./gradlew dependencies

# 4. Соберите debug APK
./gradlew assembleDebug

# 5. Найдите APK
ls app/build/outputs/apk/debug/app-debug.apk
```

## Вариант 3: На Windows через Gradle

```batch
cd android
gradle.bat assembleDebug
```

APK будет в: `android\app\build\outputs\apk\debug\app-debug.apk`

## Установка APK на устройство

### Через USB:
```bash
adb install android/app/build/outputs/apk/debug/app-debug.apk
```

### Через файловый менеджер:
1. Скопируйте APK на телефон
2. Откройте файл
3. Разрешите установку из неизвестных источников
4. Нажмите "Установить"

## Создание Release APK

```bash
./gradlew assembleRelease
```

Для release APK требуется подпись ключом. Создайте ключ:

```bash
keytool -genkey -v -keystore my-key.keystore -alias my-alias -keyalg RSA -keysize 2048 -validity 10000
```

Добавьте в `android/app/build.gradle`:

```gradle
android {
    signingConfigs {
        release {
            storeFile file("my-key.keystore")
            storePassword "your-password"
            keyAlias "my-alias"
            keyPassword "your-password"
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
        }
    }
}
```

## Проблемы и решения

### Ошибка: "SDK location not found"
Решение: Установите ANDROID_HOME переменную окружения

### Ошибка: "Build tools not found"
Решение: Установите через SDK Manager: Build Tools 34.0.0

### Ошибка: "Licence not accepted"
Решение: 
```bash
sdkmanager --licenses
```

### Ошибка компиляции Kotlin
Решение: Обновите Kotlin plugin в Android Studio

## Альтернатива: Онлайн сборка

Вы можете использовать онлайн сервисы для сборки:
- GitHub Actions (добавьте workflow)
- Bitrise CI/CD
- App Center

## Пример GitHub Actions Workflow

```yaml
name: Build Android APK

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Gradle
        run: |
          cd android
          ./gradlew assembleDebug
      - name: Upload APK
        uses: actions/upload-artifact@v2
        with:
          name: app-debug
          path: android/app/build/outputs/apk/debug/app-debug.apk
```

## Backend

Backend (Node.js) работает прямо сейчас:

```bash
cd backend
npm start
```

Server будет на: http://localhost:5000

## Ссылки для скачивания инструментов

- Android Studio: https://developer.android.com/studio
- JDK 17: https://adoptium.net/
- ADB (Android Debug Bridge): https://developer.android.com/studio/releases/platform-tools

## Помощь

Если возникнут проблемы:
1. Проверьте ANDROID_HOME
2. Убедитесь, что Java JDK 17+ установлен
3. Установите Android SDK API 34
4. Примите лицензии: `sdkmanager --licenses`