# java-client
Deepcode Public API package in Java

For low level APIs look for `DeepCodeRestApi` public methods. For descriptions look [Rest APIs and CLI](https://deepcode.freshdesk.com/support/solutions/folders/60000321393)

For high-level APIs look inside `ai.deepcode.javaclient.core` package.
Here common logic for any Java made IDE presented as `abstract` classes that need to be instantiated and finalised with platform specific code.
See [jetbrains-plugin](https://github.com/DeepCodeAI/jetbrains-plugin) for usage example.

Architect overview of common logic: Inversion of Control (Dependency Injection pattern) is implemented through constructor parameters injection. 

## Build the jar

To make a standalone jar file with all dependencies use `shadowJar` gradle task.
- Run gradle task: `source gradlew shadowJar`
- Look for resulting JAR file at `./build/libs`

## Run tests

- Run gradle test task: `source gradlew test --stacktrace --scan`