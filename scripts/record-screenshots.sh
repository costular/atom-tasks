#!/bin/bash

# A script to record screenshot baselines using Paparazzi

# Record new screenshot baselines
./gradlew recordPaparazziDebug
if [ $? -eq 0 ]; then
  echo "Screenshot baselines recorded successfully."
else
  echo "Failed to record screenshot baselines." >&2
  exit 1
fi
