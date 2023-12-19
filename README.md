
# CMS App
A proof of concept (POC) for a Charging Management System (CMS) implementing the Open Charge Point Interface (CMS) protocol.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or later
- Maven
- PostgreSQL

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
      CREATE ROLE postgres WITH LOGIN SUPERUSER PASSWORD password;
    ```
    * List all roles in PostgreSQL and make sure new user is created
    ```bash
      \du
    ```
   * login with the new user
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

## Usage

The application supports various CMS endpoints:
  ```bash
  curl -u user:password -X GET http://localhost:8080/cms/listEndpoints
  ```


### User Authorization

- **Authorize User**: Authorizes a user and retrieves an authorization token.
  ```bash
    curl -u user:password -X POST -H "Content-Type: application/json"  -d '{
    "name": "user1",
    "password": "password1"
    }' http://localhost:8080/cms/user/auth
  ```

### Charging Sessions
- **Get Charging Sessions**: Retrieves information about charging Sessions.
    ```bash
    curl -u user:password -X GET http://localhost:8080/cms/getChargingSessions
    ```


### Locations

- **Location as a grouping of charging points**: Retrieves Locations
  ```bash
    curl -u user:password -X GET http://localhost:8080/cms/getAllLocationInfo
    ```
- **Or for each Location:
  ```bash
    curl -u user:password -X GET http://localhost:8080/cms/getLocationInfo/{name}
    ```

### Charging Points

- **Get Charging Points**: Retrieves information about charging points.
    ```bash
    curl -u user:password -X GET http://localhost:8080/cms/getChargingPoints
    ```
- **Get Charging Point**: Retrieves information for a specific charging point by ID.

  ```bash
  curl -u user:password -X GET http://localhost:8080/cms/getChargingPoint/{cpId}
  ```



### Tariffs

- **Get Tariffs**: Retrieves a list of tariffs.
  ```bash
  curl -u user:password -X GET http://localhost:8080/cms/tariffs
  ```
- **Get Tariff**: Retrieves information for a specific tariff by ID.
  ```bash
  curl -u user:password -X GET http://localhost:8080/cms/tariffs/{tariffId}
  ```
  
_(Replace `<password>`, `{cpId}`, `{tariffId}`, `{name}`, and `<value>` with actual values.)_

## Contributing

Please read [CONTRIBUTING.md](link-to-contributing-file) for details on our code of conduct, and the process for submitting pull requests.

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

- **Dave Bowman** - *Initial work* - [daveb8772](https://github.com/daveb8772)

See also the list of [contributors](https://github.com/daveb8772/CMS_App/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
