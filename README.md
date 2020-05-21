# java-client
Deepcode Public API package in Java

For APIs look for `DeepCodeRestApi` public methods. For descriptions look [Rest APIs and CLI](https://deepcode.freshdesk.com/support/solutions/folders/60000321393)

## Build the jar

To make a standalone jar file with all dependencies use `shadowJar` gradle task.
- Run gradle task: `source gradlew shadowJar`
- Look for resulting JAR file at `./build/libs`

## Run tests

- Run gradle test task: `source gradlew test --stacktrace --scan`