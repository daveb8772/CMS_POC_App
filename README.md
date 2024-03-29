
# CMS App
A proof of concept webserver for a Charging Management System (CMS)
* The CMS webserver is already populated with fake data to be used by other services
* Supports User login, Depots (Locations), Charging Points, Connectors, Charging Sessions, Tariffs.
* All data is persisted in a CMS_Data postgres database

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or later
- Maven
- PostgreSQL
- Docker
- Kubernetes (e.g., Minikube or Docker Desktop)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/daveb8772/CMS_App.git
   ```
2. Navigate to the project directory and install dependencies:
   ```bash
   mvn install
   ```
3. Set up the PostgreSQL database according to the application's configuration.
    * Access PostgreSQL with your default superuser (usually your OS username):
    ```bash
    psql postgres
    ```
    * If the postgres user does not exist, you need to create it:
    ```bash
    CREATE ROLE postgres WITH LOGIN SUPERUSER PASSWORD 'password';
    ```
    * List all roles in PostgreSQL and make sure new user is created
    ```bash
    \du
    ```
    * Login with the new user
    ```bash
    psql -U postgres
    ```
    * Create the database
    ```bash
    CREATE DATABASE "CMS_Data";
    ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Docker and Kubernetes Deployment

1. Build the Docker image using the provided script:
   ```bash
   ./scripts/build-docker-image.sh
   ```
2. Deploy the application and dependencies to Kubernetes:
   ```bash
   ./scripts/deploy-all-k8s-resources.sh
   ```

### Jenkins CI/CD

- A `Jenkinsfile` is included for setting up CI/CD pipelines.

## APP API Usage

The application supports various CMS endpoints. For example:
  ```bash
  curl -u user:password -X GET http://localhost:8081/cms/listEndpoints
  ```

### User Authorization

- **Authorize User**: Authorizes a user and retrieves an authorization token.
  ```bash
    curl -u user:password -X POST -H "Content-Type: application/json"  -d '{
    "name": "user1",
    "password": "password1"
    }' http://localhost:8081/cms/user/auth
  ```

### Charging Sessions
- **Get Charging Sessions**: Retrieves information about charging Sessions.
    ```bash
    curl -u user:password -X GET http://localhost:8081/cms/getChargingSessions
    ```


### Locations

- **Location as a grouping of charging points**: Retrieves Locations
  ```bash
    curl -u user:password -X GET http://localhost:8081/cms/getAllLocationInfo
    ```
- **Or for each Location:
  ```bash
    curl -u user:password -X GET http://localhost:8081/cms/getLocationInfo/{name}
    ```

### Charging Points

- **Get Charging Points**: Retrieves information about charging points.
    ```bash
    curl -u user:password -X GET http://localhost:8081/cms/getChargingPoints
    ```
- **Get Charging Point**: Retrieves information for a specific charging point by ID.

  ```bash
  curl -u user:password -X GET http://localhost:8081/cms/getChargingPoint/{cpId}
  ```



### Tariffs

- **Get Tariffs**: Retrieves a list of tariffs.
  ```bash
  curl -u user:password -X GET http://localhost:8081/cms/tariffs
  ```
- **Get Tariff**: Retrieves information for a specific tariff by ID.
  ```bash
  curl -u user:password -X GET http://localhost:8081/cms/tariffs/{tariffId}
  ```

_(Replace `<password>`, `{cpId}`, `{tariffId}`, `{name}`, and `<value>` with actual values.)_


## Monitoring via Prometheus and Grafana
Install both and run Prometheus at: http://localhost:9090/ Grafana at http://localhost:3000/ while scraping from the App at http://localhost:8081/

For installing in osx
  ```bash
brew install prometheus
brew install grafana

  ```
for editing config:
  ```
nano /opt/homebrew/etc/prometheus.yml
  ```
And add:
  ```
 global:
 scrape_interval:     15s
 evaluation_interval: 15s

 scrape_configs:
  - job_name: 'prometheus'
   static_configs:
     - targets: ['localhost:9090']

   - job_name: 'spring-boot'  # A new job for scraping your Spring Boot app
    metrics_path: '/actuator/prometheus'  # Default actuator Prometheus endpoint
     scrape_interval: 5s
    static_configs:
      - targets: ['localhost:8081']
    basic_auth:
      username: user
      password: password
  ```
check that it is running using:
  ```
brew services list
  ```

## Authors

- **Dave Bowman** - *Initial work* - [daveb8772](https://github.com/daveb8772)

See also the list of [contributors](https://github.com/daveb8772/CMS_App/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
