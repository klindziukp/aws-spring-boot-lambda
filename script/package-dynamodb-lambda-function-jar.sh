#!/usr/bin/env bash

echo
echo "Packaging jar file"
echo "------------------"
./gradlew clean build shadowJar -x test -x processAot

echo
echo "Copying to dynamodb-lambda-function/shared folder"
echo "-------------------------------------------------"
mkdir -p dynamodb-lambda-function/shared
cp payment-handler/build/libs/payment-handler-0.0.1-SNAPSHOT.jar dynamodb-lambda-function/shared

echo "Done!"
echo