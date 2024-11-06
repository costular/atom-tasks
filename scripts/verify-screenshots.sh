#!/bin/bash

# A script to verify screenshot tests using Paparazzi

# Verify screenshot tests
./gradlew verifyPaparazziDebug
if [ $? -eq 0 ]; then
  echo "Screenshot tests verified successfully."
else
  echo "Failed to verify screenshot tests." >&2
  exit 1
fi

