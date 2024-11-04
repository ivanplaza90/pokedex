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
In order to run unit tests:

```shell
./gradlew unit
```

In order to run integration and acceptance test there are a docker-compose with all infrastructure dependencies. Run it as:

```shell
docker compose up -d
```
and then
```shell
./gradlew | integration | acceptance
```

Also, you can run first the docker-compose and then working with the service

## Running the application locally

Execute the `main` method in the `com.ivan.pokedex.PokedexApplication` class from your IDE.

Or using gradle:

```shell
./gradlew bootRun
```

## Rest API documentation

Into document directory there is a rest-api.yml file with the open api documentation.
You can open it in a swagger editor ir in postman directly in order to have all the rest endpoints ready to be launched 


## Change Log

## Version 0.4.0
* Add get trainer favorites pokemon feature

## Version 0.3.0
* Add get pokemon detail feature

## Version 0.2.0
* Add find pokemon feature

## Version 0.1.0
* Add get all pokemon feature

## Version 0.0.0
* Empty spring boot service