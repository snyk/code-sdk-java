# java-client
Deepcode Public API package in Java

[![deepcode](https://www.deepcode.ai/api/gh/badge?key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwbGF0Zm9ybTEiOiJnaCIsIm93bmVyMSI6IkRlZXBDb2RlQUkiLCJyZXBvMSI6ImphdmEtY2xpZW50IiwiaW5jbHVkZUxpbnQiOmZhbHNlLCJhdXRob3JJZCI6MTI0NjksImlhdCI6MTU5NjA5NzIzMn0.a8lZClW69fj53juqAW0NJ6uWh-1iOXiR-mn5pN3eATc)](https://www.deepcode.ai/app/gh/DeepCodeAI/java-client/_/dashboard?utm_content=gh%2FDeepCodeAI%2Fjava-client)

For low level APIs look for `DeepCodeRestApi` public methods. For descriptions - look [Rest APIs and CLI](https://deepcode.freshdesk.com/support/solutions/folders/60000321393)

For high-level APIs look inside `ai.deepcode.javaclient.core` package.
Here common logic for any Java made IDE presented as `abstract` classes that need to be instantiated and finalised with platform specific code.
See [jetbrains-plugin](https://github.com/DeepCodeAI/jetbrains-plugin) for usage example.

Architect overview of common logic: Inversion of Control (Dependency Injection pattern) is implemented through constructor parameters injection. 

## Build the jar

To make a standalone jar file with all dependencies use `shadowJar` gradle task.
- Run gradle task: `source gradlew shadowJar`
- Look for resulting JAR file at `./build/libs`

## Run tests

- 2 environment variables with __already logged__ Tokens need to be declared:

`DEEPCODE_API_KEY` - logged at https://www.deepcode.ai Token 

`DEEPCODE_API_KEY_STAGING` - logged at https://www.deepcoded.com Token

- Run gradle test task: `source gradlew test --stacktrace --scan`