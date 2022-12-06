# car-parking-exercise
An simple API-provided application for checking car parking available in Singapore

# Features
- Find nearest available carparking lots
- Periodically update latest car parking availability (around every minute) and car parking information (4AM at every day).
- Main APIs:
 1. Update car parking information: PATH `<host>/v1/carparks/information/update`
 2. Update car parking availability: PATH `<host>/v1/carparks/availability/update`
 3. Get nearest car park availability with coordination: GET <host>/v1/carparks/nearest?lattitude=<>&longitude=<>&page=<>&per_page=<>

# Approching
- Use `SpringBoot` for initialize Spring project.
- Use `MySql DB` for storing data.
- Use `MyBatis` to custom bussiness SQL, mapping and transaction management into domain with simple configuration.
- Use reactive `WebFlux` webclient for calling external system.
- Use `TestContainers` for testing SQL integrations.
- Use `Groovy - Spock test` for testing unit tests.
- Use Spring framework - `Scheduler` for scheduling task to update car parking information and availability.

In scaling concerns, I separated API backend service and scheduler tasks that we will have ability to scale up instances of worker or service without effecting each others. Beside, we will have flexibility to start/stop scheduler task without major change in system.

In order to get car parking information, instead of download static data with CSV, I re-use configured WebClient for getting latest data with approprite scheduling, so that system can make sure data will be latest almost the time.

# Project build and deploy Guideline
## Build and deploy with Docker container

### Note: This project use `Java 17` for development.

### 1. Setup MySql DB server
You can start your own MySql DB server (container in Docker Hub, cloud/server domain, install local,...), and version should be equals or larger than `8.0.0`
There are properties in source code that need to be modified as you need:
- `spring.flyway.url`, `spring.flyway.user`, `spring.flyway.password`
- `spring.datasource.query.jdbc-url`, `spring.datasource.query.username`, `spring.datasource.query.password`
- `spring.datasource.register.jdbc-url`, `spring.datasource.register.username`, `spring.datasource.register.password`
properties files according to active profile:
`appliction.properties` and `application-database.properties` is base profile (if not be modified on other profile)
`appliction-prod.properties` for active profile is `prod`(deploying on Docker containers).


### 2. Run `build.sh` script at `docker` folder
- ```cd <project path - car-parking-exercise>```

To build `carparking-api` image on docker:

- ```./docker/production/build.sh carparking-api```

To build `worker` image on docker:

- ```./docker/production/build.sh worker```

Run docker images: you can run and start docker container with `carpark/carpark_api` and `carpark/worker`, but with port mapping at required, should be `8080` for `carpark/carpark_api` and `8083` `carpark/worker`, but at your calls.

Example:

- ```docker run --name "carparking-api" -d -p 8080:8080 carpark/carpark_api:0.0.1```

- ```docker run --name "carparking-worker" -d -p 8083:8083 carpark/worker:0.0.1```

### 3. Build and run source codes (Optional)
- ```cd <project path - car-parking-exercise>```

- ```./gradlew :carparking-api:clean :carparking-api:build```
- ```./gradlew :carparking-api:bootRun```

If need to start worker for scheduler tasks

- ```./gradlew :worker:clean :worker:build```
- ```./gradlew :worker:bootRun```

# Concerns
- Car parking availability data will have more than one records for one car car parking.
![Screenshot 2022-12-05 at 19 01 04](https://user-images.githubusercontent.com/16128469/205633474-341bf742-cb36-42b0-8ffd-51bed18fe945.png)
With current response schema as request in exercise, it doesn't have `lot_type` information, so it can be seems dupplicated data if each item has same car parking address.
So I make additional `lot_type` information on car parking availability response.
