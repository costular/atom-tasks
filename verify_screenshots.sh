#!/bin/bash
mkdir testResults
adb logcat > testResults/logcat.txt &

adb shell settings put global hidden_api_policy_pre_p_apps 1
adb shell settings put global hidden_api_policy_p_apps 1
adb shell settings put global hidden_api_policy 1

bash disable_animations.sh

./gradlew clean executeScreenshotTests \
-Pandroid.testInstrumentationRunnerArguments.annotation=com.costular.atomtasks.screenshots.ScreenshotTest || touch sorry_but_tests_are_failing

mv app/build/reports/androidTests/connected testResults/
test ! -f sorry_but_tests_are_failing
