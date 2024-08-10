# **API Automation Project**

# Overview:
This project is designed to automate the testing of API endpoints using the Rest Assured, SHAFT framework, Extent Reports, and Allure Reports for comprehensive test reporting. The project includes various test cases for performing CRUD operations (Create, Read, Update, Delete) on user resources.

## 1- Key Files and Directories:
src/test/java/com.ReqRes.API.Automation: Contains the test classes for each API endpoint.
src/test/resources/testDataFiles: Stores the JSON files used for request bodies and schema validations.
pom.xml: Maven configuration file that manages project dependencies.
extentReports.html: Generated report file providing detailed test execution results.

## 2-Prerequisites:
Before you begin, ensure you have the following installed on your machine:

Java Development Kit (JDK) 8 or above
Apache Maven for managing dependencies and building the project

## 3- Test Reporting:
The tests generate a detailed HTML report using Extent Reports. This report provides insights into:
Passed, Failed, and Skipped tests
Detailed logs for each test step
Screenshots and error messages for failed tests
Viewing the Report
After running the tests, you can find the report at:

plaintext
Copy code
/src/test/extentReports.html
Open this file in a web browser to view the test results.

## 4- Extending the Framework
Adding New Tests
Create a new class under com.ReqRes.API.Automation.
Write the test methods using the SHAFT framework/RestAssured.
Include validation and reporting steps similar to the existing tests.
Adding New Dependencies
To add new dependencies, modify the pom.xml file and run mvn clean install to ensure the dependencies are correctly added to the project.

### For further assistance, please consult the documentation for the SHAFT and Extent Reports frameworks.
