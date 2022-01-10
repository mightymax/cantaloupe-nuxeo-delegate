#!/bin/bash

# Convenience Bash script to build Delegate and start Cantaloupe server
# @Author Mark Lindeman <mark@lindeman.nu>

script=$0
cd $(dirname $script)

./gradlew jar  

GradleBuildFile="./lib/build.gradle"
test -f $GradleBuildFile || {
  echo "[ERROR] expected Gradle build file at $GradleBuildFile" 1>&2
  exit 2
}

echo -n "Locate Cantaloupe JAR: "
CantaloupeJar=$(grep 'def CANTALOUPE_JAR = ' ./lib/build.gradle | sed 's/.*"\(.*\)"/\1/')
test -z "$CantaloupeJar" && {
  echo "error"
  echo "[ERROR] failed to auto detect Cantaloupe Jar from gradle implementation" 1>&2
  exit 1
}

test -f "$CantaloupeJar" || {
  echo "not found"
  echo "[ERROR] JAR '$CantaloupeJar' is not a file" 1>&2
  exit 2
}

echo $CantaloupeJar

# Autodetect Delegate JAR:
archivesBaseName=$(grep "archivesBaseName" ./lib/build.gradle|cut -d '"' -f 2)
test -z $archivesBaseName && {
  echo "error"
  echo "[ERROR] failed to grep 'archivesBaseName' from build.gradle" 1>&2
  exit 3
}
version=$(grep 'version ' ./lib/build.gradle | cut -d '"' -f 2)
DelegateJar="./lib/build/libs/$archivesBaseName-$version.jar"
test -f $DelegateJar || {
  echo "not found"
  echo "[ERROR] Expected Delegate JAR at $DelegateJar" 1>&2
  exit 3
}

java -cp \
  "$CantaloupeJar":"$DelegateJar" \
  -Dcantaloupe.config=cantaloupe.minimal.properties \
  edu.illinois.library.cantaloupe.StandaloneEntry
