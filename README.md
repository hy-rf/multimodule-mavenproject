# Its my multi-module maven sample project.

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

4. API service is now at port 8080.
