
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


### Monitoring via Prometheus and Grafana
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