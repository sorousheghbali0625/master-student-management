Master Student Management API
A Spring Boot REST API for managing university professors, students, subjects, and course selections. Built as a small, maintainable backend project with a layered architecture.
Features
- List professors, students, and subjects.
- View the subjects offered by a professor.
- View students enrolled in a professor's subjects.
- View a student's selected subjects.
- Register and delete students.
- Create, update, and delete subjects.
- Select and remove subjects for a student (many-to-many enrollment).
- Normalize Persian subject titles before saving (selected Arabic/Persian letter forms, digits, and whitespace).
- Explore and test endpoints with Swagger UI.
- Protect Swagger UI and OpenAPI documentation with HTTP Basic authentication.
Technology Stack
Technology	Purpose
Java 27	Application language
Spring Boot 4.1.1	Application framework
Spring Web MVC	REST endpoints
Spring Data JPA / Hibernate	Persistence and entity relationships
H2 Database	Lightweight file-based development database
Spring Security	HTTP Basic authentication for Swagger
Springdoc OpenAPI	Swagger UI / OpenAPI documentation
Lombok	Reduce boilerplate in entities and Spring components
Maven	Build and dependency management


Architecture
The application follows a layered structure:
src/main/java/org/soroush/masterstudentmanagement/
├── controller/       # HTTP request/response endpoints
├── services/         # Business rules and transactions
├── repository/       # Spring Data JPA repositories
├── model/            # JPA entities
├── dto/              # Request and response models
├── normalization/    # Persian text normalization
├── config/           # OpenAPI and Spring Security configuration
└── MasterStudentManagementApplication.java
Typical request flow:
HTTP Client -> Controller -> Service -> Repository -> H2 Database
Data Model
- Master → Subject: One-to-many. A professor may offer several subjects; each subject belongs to one professor.
- Student ↔ Subject: Many-to-many, stored through the student_subjects join table.
The join table contains student_id and subject_id. Deleting a student's selections should not delete the subjects themselves.
Prerequisites
- JDK 27
- Maven, or the Maven Wrapper if included in the repository
- An IDE such as IntelliJ IDEA (optional)
Configuration
The application uses an H2 database configured in src/main/resources/application.properties:
spring.datasource.url=jdbc:h2:file:./data/universitydb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
The database path is relative to the application's working directory. This configuration is intended for local development; production deployments should use appropriate database credentials and a migration strategy.
Swagger credentials
Swagger access is configured using environment variables; do not commit real credentials.
app.swagger.username=${SWAGGER_USERNAME}
app.swagger.password=${SWAGGER_PASSWORD}
Set both variables in your IDE's Run Configuration or in your shell before starting the application:

PowerShell (Windows):
$env:SWAGGER_USERNAME = "swaggeradmin"
$env:SWAGGER_PASSWORD = "<choose-a-strong-password>"
Bash (Linux/macOS):
export SWAGGER_USERNAME=swaggeradmin
export SWAGGER_PASSWORD='<choose-a-strong-password>'
Run Locally
From the project root, run:
mvn spring-boot:run
On Windows, if Maven Wrapper exists:
.\mvnw.cmd spring-boot:run
By default, the application listens at http://localhost:8080.
URL	Purpose
http://localhost:8080/swagger-ui.html	Interactive API documentation (requires credentials)
http://localhost:8080/v3/api-docs	OpenAPI specification (requires credentials)
http://localhost:8080/h2-console	H2 development console


API Endpoints
Professors
Method	Endpoint	Description
GET	/masters	List professors
GET	/masters/{masterId}/subjects	List a professor's subjects
GET	/masters/{masterId}/students	List students associated with a professor's subjects


Students
Method	Endpoint	Description
GET	/students	List students
POST	/students	Create a student
DELETE	/students/{studentId}	Delete a student
GET	/students/{studentId}/subjects	List a student's subjects
POST	/students/{studentId}/subjects/{subjectId}	Select a subject
DELETE	/students/{studentId}/subjects/{subjectId}	Remove a selected subject


Subjects
Method	Endpoint	Description
GET	/subjects	List subjects
POST	/subjects	Create a subject
PUT	/subjects/{subjectId}	Update a subject
DELETE	/subjects/{subjectId}	Delete a subject


Example Requests
Create a student
POST /students
Content-Type: application/json
{
  "name": "Sara Ahmadi",
  "studentNumber": "STU20260001"
}
Create a subject
POST /subjects
Content-Type: application/json
{
  "title": "رياضي  ١",
  "units": 3,
  "master": {
    "id": 1
  }
}
Subject titles are normalized before saving; in this example, رياضي  ١ becomes ریاضی ۱.
Update a subject
PUT /subjects/1
Content-Type: application/json
{
  "title": "برنامه نويسي پيشرفته 2",
  "units": 4,
  "masterId": 1
}
Select a subject
POST /students/1/subjects/1
The student and subject IDs must already exist.
Expected HTTP Responses
Status	Meaning / typical scenario
200 OK	Successful read, update, or course selection
201 Created	Student or subject created
204 No Content	Student or subject deleted
400 Bad Request	Invalid input or malformed path parameter
404 Not Found	Student, professor, or subject does not exist
409 Conflict	Duplicate student number or an already-selected subject
401 Unauthorized	Swagger documentation accessed without valid credentials


Exact response bodies depend on the controller and exception-handling configuration.
Security Scope
The current security setup restricts Swagger UI and /v3/api-docs using an in-memory user and HTTP Basic authentication. The application APIs (/students, /subjects, /masters) are not authenticated by this configuration and may still be called directly outside Swagger. This is intended for a local demonstration, not production authorization.

Use HTTPS whenever transmitting HTTP Basic credentials outside localhost. Before production use, secure the API endpoints, revisit CSRF rules, restrict the H2 console, and move credentials to a managed secret store.
Tests
Run all configured tests with:
mvn test
Normalization unit tests should cover Persian/Arabic character variants, Persian digits, whitespace, and idempotency. API behavior should be checked for successful CRUD operations, missing IDs, duplicates, and many-to-many relationship cleanup.
Notes
- The H2 database is file-based, so local test data can persist across restarts.
- Existing records are not automatically normalized simply by adding normalization to the subject creation/update path; migrate legacy values separately if necessary.
- This README documents the project design discussed during development. Verify the final request/response contracts and security settings against the latest source before delivery.
