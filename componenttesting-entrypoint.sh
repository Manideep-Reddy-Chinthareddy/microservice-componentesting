#!/bin/bash

pwd

ls -ltraSh


./gradlew componenttesting

EXIT_CODE=$?

if [ $EXIT_CODE -ne 0 ]; then
  echo "Component testing failed with exit code $EXIT_CODE"
  exit $EXIT_CODE
else
  echo "Component testing succeeded"
  exit $EXIT_CODE
fi
