# Password Strength Evaluator
This project is for educational purposes only. Please don't use such tools to evaluate your real passwords strength.
We never know what the programmer or an attacker does with your password. Even if the source code is available publicly it does not mean that this code runs unchanged on a server.

## Installation
`./gradlew vaadinBuildFrontend` prepares all the Vaadin frontend files.
`./gradlew startCassandra` starts the included Cassandra database on a Windows host in the background.
`./gradlew runApplication` starts the application. The application is reachable on http://127.0.0.1:8080.

## Unit tests
`./gradlew test` runs all unit tests.

## UI tests
Requirements:
* Installed Chrome Browser

`./gradlew startCassandra` starts the included Cassandra database on a Windows host in the background.
`./gradlew uitest` starts the application and all UI tests with Selenium in the Chrome Browser.
`./gradlew stopCassandra` stops the included Cassandra database again.
