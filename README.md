
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
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage

The application supports various CMS endpoints:

### Charging Sessions

- **Get Charging Sessions**: Retrieves a list of charging session data.
  ```bash
  curl -X GET http://localhost:8080/cms/sessions
  ```

### User Authorization

- **Authorize User**: Authorizes a user and retrieves an authorization token.
  ```bash
  curl -u user:<password> http://localhost:8080/cms/auth
  ```

### Charging Points

- **Get Charging Points**: Retrieves information about charging points.
  ```bash
  curl -X GET http://localhost:8080/cms/cps
  ```
- **Get Charging Point**: Retrieves information for a specific charging point by ID.
  ```bash
  curl -X GET http://localhost:8080/cms/cps/{cpId}
  ```

### Tariffs

- **Get Tariffs**: Retrieves a list of tariffs.
  ```bash
  curl -X GET http://localhost:8080/cms/tariffs
  ```
- **Get Tariff**: Retrieves information for a specific tariff by ID.
  ```bash
  curl -X GET http://localhost:8080/cms/tariffs/{tariffId}
  ```

### Commands

- **Send Command**: Sends a command to a charging point.
  ```bash
  curl -X POST http://localhost:8080/cms/commands/{command} -d '{ "parameter": "<value>" }'
  ```

_(Replace `<password>`, `{cpId}`, `{tariffId}`, `{command}`, and `<value>` with actual values.)_

## Contributing

Please read [CONTRIBUTING.md](link-to-contributing-file) for details on our code of conduct, and the process for submitting pull requests.

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

- **Dave Bowman** - *Initial work* - [daveb8772](https://github.com/daveb8772)

See also the list of [contributors](https://github.com/daveb8772/CMS_App/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
