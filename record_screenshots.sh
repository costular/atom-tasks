#!/bin/bash
adb shell settings put global hidden_api_policy_pre_p_apps 1
adb shell settings put global hidden_api_policy_p_apps 1
adb shell settings put global hidden_api_policy 1

bash disable_animations.sh

./gradlew clean executeScreenshotTests -Precord
