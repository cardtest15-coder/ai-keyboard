# AI Keyboard - Installation Guide

## Windows Users

### Option 1: Use the batch script
Double-click `run.bat` to start the application menu.

### Option 2: Manual installation

#### Backend
```bash
cd backend
npm install
npm start
```

#### Android App
```bash
cd android
gradlew.bat installDebug
```

## Linux/Mac Users

### Option 1: Use the shell script
```bash
chmod +x run.sh
./run.sh
```

### Option 2: Manual installation

#### Backend
```bash
cd backend
npm install
npm start
```

#### Android App
```bash
cd android
./gradlew installDebug
```

## After Installation

1. **Backend will start on** http://localhost:5000
2. **Android App** will be installed on your connected device/emulator
3. **Enable Keyboard**: Go to Settings > Language & Input > Current Keyboard > Select "AI Keyboard"

## Troubleshooting

### Backend not starting
- Check if MongoDB is running: `mongod`
- Check if port 5000 is available
- Check .env file configuration

### Android not installing
- Enable USB Debugging on device
- Connect device via USB
- Run `adb devices` to verify connection
- Make sure Android SDK is installed

## API Testing

You can test the API endpoints using curl or Postman:

```bash
# Test predictions
curl http://localhost:5000/api/predictions/predict?text=hello&language=en

# Test autocorrect
curl -X POST http://localhost:5000/api/autocorrect/correct -H "Content-Type: application/json" -d "{\"text\":\"teh\",\"language\":\"en\"}"
```