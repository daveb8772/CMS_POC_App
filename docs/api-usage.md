
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
