# pokedex

## Requirements

For building and running the application you need:

- [JDK 21](https://openjdk.org/projects/jdk/21/)
- [gradle 8.10.2](https://docs.gradle.org/8.10.2/release-notes.html)

## Build the application locally
```shell
./gradlew clean build
```

There are three kind of test into the project. In order to run each one separately:
```shell
./gradlew unit | integration | acceptance
```

## Running the application locally

Execute the `main` method in the `com.ivan.pokedex.PokedexApplication` class from your IDE.

Or using gradle:

```shell
./gradlew bootRun
```

## Change Log

## Version 0.0.0
* Empty spring boot service