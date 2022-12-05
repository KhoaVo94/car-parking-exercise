#!/bin/sh

component=$1
echo "Build docker ${component}"

sh ./gradlew :${component}:clean :${component}:build

cp -p $(find ${component}/build/libs -name ${component}-*-SNAPSHOT.jar) docker/production/${component}/${component}.jar

docker-compose build ${component}

rm docker/production/${component}/${component}.jar