# Its my multi-module maven sample project.

### Prerequisite

1. JDK 21

### How to run

1. Fill your config files in `app` module.

2. Build with maven:

```shell
./mvnw clean install
```

3. Find your jar file and run.

```shell
java -jar app/target/app-version.jar
```

or dev server:

```shell
./mvnw spring-boot:run -pl app
```

4. API service is now at port 8080.

5. You can move jar file to anywhere as standalone runnable jar file.

### Test

1. Run test command:

```shell
./mvnw clean test
```
