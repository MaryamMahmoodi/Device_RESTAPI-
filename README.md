# Device Management API

## Overview

This API provides endpoints for managing devices, allowing users to perform operations such as inserting, retrieving, updating, and deleting devices. It also supports searching for devices by brand and restoring soft-deleted devices.

## Installation

To run the API locally, follow these steps:

1. Clone the repository from `https://github.com/MaryamMahmoodi/Device_RESTAPI-.git` to your local machine.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project and install dependencies.
4. Start the application using `mvn spring-boot:run` or deploy it to a servlet container.

## Usage

After starting the application, you can use Postman or any other HTTP client to interact with the API endpoints. Below are the available endpoints:

1. **Insert Device**: POST http://localhost:8080/device/insert
2. **Get All Devices**: GET http://localhost:8080/device/getAll
3. **Get Device by ID**: GET http://localhost:8080/device/getOne/{id}
4. **Update Device by ID**: PUT http://localhost:8080/device/update/{id}
5. **Partially Update Device by ID**: PUT http://localhost:8080/device/updatePartial/{id}
6. **Delete Device by ID**: DELETE http://localhost:8080/device/deleteById/{id}
7. **Search Devices by Brand**: GET http://localhost:8080/device/searchByBrand/{brand}
8. **Restore Device**: POST http://localhost:8080/device/restore/{id}

## Configuration

The application uses default configurations for database connection, logging, and Spring Boot settings. If necessary, you can customize these configurations by modifying the `application.properties` file located in the `src/main/resources` directory. For database-specific configurations, you can modify the `db.properties` file located in the `resource/config/jdbc` directory. Additionally, the `DB` directory in the root of the project contains scripts for creating the Device table for your reference.
## Explanation of Code Modules

- **DeviceController**: Implements REST endpoints for device management.
- **DeviceServiceImp**: Implements business logic for device management.
- **DeviceDaImp**: Implements data access methods for device management.
- **Device**: Represents the device entity with attributes like ID, name, brand, and timestamps.
- **JdbcConfig**: This class provides configuration for JDBC connections.
- **README.md**: Provides documentation about the API, including installation, usage, configuration, and explanation of code modules.
