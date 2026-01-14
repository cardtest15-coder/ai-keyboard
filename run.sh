#!/bin/bash

echo "========================================"
echo "AI Keyboard - Full Stack Application"
echo "========================================"
echo ""
echo "Choose an option:"
echo "1. Start Backend Server"
echo "2. Install Android App"
echo "3. Install Backend Dependencies"
echo "4. Exit"
echo ""
read -p "Enter your choice (1-4): " choice

case $choice in
    1)
        echo "Starting Backend Server..."
        cd backend
        npm start
        ;;
    2)
        echo "Installing Android App..."
        cd android
        ./gradlew installDebug
        ;;
    3)
        echo "Installing Backend Dependencies..."
        cd backend
        npm install
        echo ""
        echo "Dependencies installed successfully!"
        ;;
    4)
        exit 0
        ;;
    *)
        echo "Invalid choice!"
        ;;
esac