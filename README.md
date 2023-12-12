# OCPI_App
OCPI REST Service Implementation

Below is a comprehensive README with details on the implemented endpoints in your OCPI application project:


# OCPI App

OCPI App is an implementation of the Open Charge Point Interface (OCPI) protocol, providing a framework for communication between Charge Point Operators (CPOs) and Mobility Service Providers (MSPs).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or later
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/daveb8772/OCPI_App.git
   ```
2. Navigate to the project directory and install dependencies:
   ```
   mvn install
   ```
3. Set up the PostgreSQL database as per the application's configuration.
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage

The application supports the following OCPI endpoints:

### Charging Sessions

- **Get Charging Sessions**: Retrieves a list of charging session data.
  ```
  curl -X GET http://localhost:8080/ocpi/getChargingSessions
  ```

### User Authorization

- **Authorize User**: Authorizes a user and retrieves an authorization token.
  ```
  curl -u user:<password> http://localhost:8080/ocpi/authorizeUser
  ```

### Charging Points

- **Get Charging Points**: Retrieves information about charging points.
  ```
  curl -X GET http://localhost:8080/ocpi/getChargingPoints
  ```
- **Get Charging Point**: Retrieves information for a specific charging point by ID.
  ```
  curl -X GET http://localhost:8080/ocpi/getChargingPoint/{cpId}
  ```

### Tariffs

- **Get Tariffs**: Retrieves a list of tariffs.
  ```
  curl -X GET http://localhost:8080/ocpi/getTariffs
  ```
- **Get Tariff**: Retrieves information for a specific tariff by ID.
  ```
  curl -X GET http://localhost:8080/ocpi/getTariff/{tariffId}
  ```

### Commands

- **Send Command**: Sends a command to the charging point and retrieves the response.
  ```
  curl -X POST http://localhost:8080/ocpi/commands/{command} -d '{ "commandParameter": "<value>" }'
  ```

_(Replace `<password>`, `{cpId}`, `{tariffId}`, `{command}`, and `<value>` with actual values.)_

## Contributing

Please read [CONTRIBUTING.md](link-to-your-contributing-file) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

- **Dave Bowman - *Initial work* - [daveb8772](https://github.com/daveb8772)

See also the list of [contributors](https://github.com/daveb8772/OCPI_App/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Open Charge Point Interface (OCPI) specifications: [OCPI](https://github.com/ocpi)

## Additional Information

- For more information about OCPI, visit the [official OCPI documentation]([https://ocpiemobility.org/documentation/](https://github.com/ocpi/ocpi/blob/master/README.md)https://github.com/ocpi/ocpi/blob/master/README.md).

```
