# Testing docker network for r2dbc

# Build

mvn clean package

## Tests

### Non-docker for app

Start a postgres container:

```shell
docker-compose -f docker-compose-test-1.yml up --force-recreate -V
```

Start app:

```shell
DB_HOST=localhost DB_PORT=5432 DB_USER=test DB_PASSWORD=test java -jar target/test-pg-r2dbc.jar
```

### Docker for app

Create container:

```shell
docker build -t test-pg-r2dbc:latest .
```

Run app and postgres:

```shell
docker-compose -f docker-compose-test-2.yml up --force-recreate -V
```

