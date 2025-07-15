# Assignment
A simple Spring Boot REST API for searching and filtering courses using Elasticsearch. Supports full-text search, filtering by age, price, category, type, and sorting by course start dates.


**Project Structure **

UndoSchool/
├── .gitignore
├── README.md
├── docker-compose.yml
├── pom.xml (or build.gradle)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/assignment/undoschool/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── document/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── UndoSchoolApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── sample-courses.json
│   └── test/
│       └── java/...

##  Features

###  Assignment A
- Dockerized Elasticsearch (7.x/8.x) setup
- Index 50+ course documents on startup from `sample-courses.json`
- REST API to:
  - Search courses by keyword
  - Filter by age, price, category, type, and date
  - Sort by session date or price
  - Support pagination

 ### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

---
### Tech Stack

Java 17
Spring Boot
Spring Data Elasticsearch
Docker + Docker Compose
Jackson (for JSON handling)

##  Start Elasticsearch 

```bash
docker-compose up -d

## Run Application

./mvnw spring-boot:run

Sample data is stored in:src/main/resources/sample-courses.json

## Search Endpoint

GET /api/search

Query Parameters:

Name	                 Type	                             Description
q	                    string	               Full-text keyword (title & description)
minAge	                int	                      Minimum age filter
maxAge	                int	                      Maximum age filter
category	             string	                 Course category (e.g., Math, Art)
type	                 string	                 Course type (ONE_TIME, COURSE, CLUB)
minPrice	             double	                      Minimum price filter
maxPrice	             double	                      Maximum price filter
startDate	            ISO-8601	              Filter future courses (e.g. 2025-06-01)
sort	                 string	                upcoming (default), priceAsc, priceDesc
page	                  int	                        Page number (default: 0)
size	                  int	                        Page size (default: 10)

Example: curl "http://localhost:8080/api/search?q=science&minAge=6&maxAge=10&sort=priceAsc&page=0&size=5"





