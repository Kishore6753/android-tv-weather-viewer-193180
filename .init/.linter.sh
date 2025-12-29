#!/bin/bash
cd /home/kavia/workspace/code-generation/android-tv-weather-viewer-193180/weather_app_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

