# regions-directory
[![Language](http://img.shields.io/badge/language-java-brightgreen.svg)](https://www.java.com/)
[![License](http://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/samtools/PolinaBevad/bio_relatives)

REST-API for regions management

## Table of Contents
-   [Used technologies](#used-technologies)
-   [Usage](#usage)
    -   [How to test this API](#how-to-test-this-api)
    -   [Functionality](#functionality)
    -   [Special features](#special-features)
-   [Tests](#tests)
-   [Maintainer](#maintainer)
-   [License](#license)

## Used technologies
-   Java 11
-   Spring Boot 2.3.4
-   Spring Web
-   Spring Cache
-   My Batis
-   H2
-   Lombok
-   Spring Test

## Usage
### How to test this API
You should build and run this project in some IDE. The best way is to run this project in Intellij IDEA with Lombok plugin. 
After running the application, you can test this API using any application that allows you to send HTTP requests. For example, you can use [Postman](https://www.postman.com/downloads/).
(p.s. DB will be created automatically)
### Functionality
-   `Get all regions`: 
    -   Type: GET
    -   URL: `http://localhost:9090/regions/get/all`
    -   Response body (example):
    ```
    [
    {
        "id": 1,
        "name": "Murmansk region",
        "abbreviatedName": "Mur"
    },
    {
        "id": 2,
        "name": "Leningrad region",
        "abbreviatedName": "Len"
    },
    {
        "id": 3,
        "name": "Vologda region",
        "abbreviatedName": "Vol"
    }
    ]
    ```
    -   Statuses: OK(200), NOT FOUND(404)
-   `Get region by id`: 
    -   Type: GET
    -   URL: `http://localhost:9090/regions/get/{id}`
    -   Response body (example):
    ```
    {
        "id": 1,
        "name": "Murmansk region",
        "abbreviatedName": "Mur"
    }
    ```
    -   Statuses: OK(200), NOT FOUND(404)
-   `Save region`:
    -   Type: POST
    -   URL: `http://localhost:9090/regions/save`
    -   Request body (example):
    ```
    {
        "name" : "test",
        "abbreviatedName" : "t"
    }
    ```
    -   Response body (example):
    ```
    {
        "id": 1,
        "name": "test",
        "abbreviatedName": "t"
    }
    ```
    -   Statuses: CREATED(201), INTERNAL SERVER ERROR(500), FORBIDDEN(403)
-   `Get all regions in sort order by field of region and sort order`:
    -   Type: POST
    -   URL: `http://localhost:9090/regions/get/sort`
    -   Request body:
    ```
    {
    "sortedField": "NAME" or "ABBREVIATED_NAME",
    "sortOrder": "DESC" or "ASC"
    }
    ```
    -   Response body (example):
    ```
    [
    {
        "id": 1,
        "name": "Murmansk region",
        "abbreviatedName": "Mur"
    },
    {
        "id": 2,
        "name": "Leningrad region",
        "abbreviatedName": "Len"
    },
    {
        "id": 3,
        "name": "Vologda region",
        "abbreviatedName": "Vol"
    }
    ]
    ```
    -   Statuses: OK(200), NOT FOUND(404)
    
-   `Update region`: 
    -   Type: PUT
    -   URL: `http://localhost:9090/regions/update`
    -   Request body (example):
    ```
    {
        "id": 1
        "name" : "test",
        "abbreviatedName" : "t"
    }
    ```
    -   Response body (example):
    ```
    {
        "id": 1,
        "name": "test",
        "abbreviatedName": "t"
    }
    ```
    -   Statuses: OK(200), NOT MODIFIED(304)

-   `Delete region by id`: 
    -   Type: DELETE
    -   URL: `http://localhost:9090/regions/delete/{id}`
    -   Response body (example):
    ```
    {
        "id": 1,
        "name": "test",
        "abbreviatedName": "t"
    }
    ```
    -   Statuses: OK(200), NOT FOUND(404)
    
### Special features
-   This aplication uses Spring Cache. Entities are written to the cache when fetching by ID. The cache is overwritten when the entities are updated. Entities are removed from the cache when they are removed from the database. All this provides us with faster access to the data that is used.
-   You cannot save 2 records to the database with the same names and abbreviated names (both of these fields must be equal for two records). When you try to add identical information about the region, the Forbidden status is returned and no duplicate entry appears.
-   When adding a new region, extra spaces are removed from its fields.

## Tests
You can run Unit tests located in the directory: `src/test/java/ru/marchenko/regionsdirectory`.

## Maintainer
Vladislav Marchenko

## License
This project is licenced under the terms of the [MIT](LICENSE) license.
