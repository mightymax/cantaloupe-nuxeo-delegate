# Delegate for Cantaloupe / Nuxeo

This repository contains code to create a jar file that can be used as a [Delegate](https://cantaloupe-project.github.io/manual/5.0/delegate-system.html) for [Cantaloupe's](https://cantaloupe-project.github.io/) Delegate System to load source from [Nuxeo](https://www.nuxeo.com/).

## Before building, testing, running, etc
- [Download](https://github.com/cantaloupe-project/cantaloupe/releases) a recent Cantaloupe build (v.50 or higher) or build from source
- change the value of *cantaloupe_jar* in _lib/build/build-example.gradle_ to match the path of your Cantaloupe Jar file
- Rename _lib/build/build-example.gradle_ to _lib/build/build.gradle_
- Modify if needed and rename _cantaloupe.properties.sample_ to _cantaloupe.properties_

## Build
To build the jar file in lib/build/libs/:

`gradle jar`

## Run Cantaloupe with this Delegate
There is a minimal settings file for Cantaloupe in this repository "cantaloupe.minimal.properties"

`java -cp \
  "/opt/cantaloupe/cantaloupe-6.0-SNAPSHOT.jar":"./lib/build/libs/cantaloupe-nuxeo-delegate-1.0.jar" \
  -Dcantaloupe.config=cantaloupe.minmal.properties \
  edu.illinois.library.cantaloupe.StandaloneEntry
`