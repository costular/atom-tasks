#!/bin/bash
adb shell settings put global hidden_api_policy 1
./gradlew executeScreenshotTests
bash disable_animations.sh
