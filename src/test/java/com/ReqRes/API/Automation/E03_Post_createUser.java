package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class E03_Post_createUser {
    private SHAFT.API api;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setupExtentReport() {
        // Set up Extent Report with Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReports.html");
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("Create User API Tests");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Test Engineer");
    }


    @BeforeMethod
    public void setup() {
        // Initialize SHAFT API with the Base URL
        api = new SHAFT.API(ObjectModels.BaseURL);
        test = extent.createTest("Create User", "Verify that a new user can be created successfully");
    }


    @Test
    public void valid_Post_createUser() {

        // Set the type of the method / endpoint / Status Code/ Content Type / Request Body
        api
                .post("/users")
                .setContentType(ContentType.JSON)
                .setRequestBodyFromFile("src/test/resources/testDataFiles/createUser.json")
                .setTargetStatusCode(201)
                .perform();

        test.log(Status.INFO, "POST request made to /users with request body from createUser.json");

        // Log response body
        String responseBody = api.getResponseBody();
        test.log(Status.INFO, "API response: " + responseBody);

        // Print response body
        System.out.println(api.getResponseBody());


        //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(2000).perform();
        test.log(Status.PASS, "Response time is within the expected limit.");


        api.assertThatResponse()
                .body().contains("createdAt")
                .withCustomReportMessage("The user is created successfully")
                .perform();
        test.log(Status.PASS, "The user is created successfully.");

        // Validate that the user ID is correct
        api
                .assertThatResponse()
                .extractedJsonValue("id")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                        .getString("id"))
                .withCustomReportMessage("User ID in response matches the requested ID.")
                .perform();
        test.log(Status.PASS, "User ID is generated successfully.");

        // Assert that specific JSON values in the response body match the expected data from the JSON file
        api
                .assertThatResponse().extractedJsonValue("email")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                        .getString("email"))
                .withCustomReportMessage("Check that user profile info has been completed Successfully")
                .perform();
        test.log(Status.PASS, "User email matches the expected value.");

        api
                .assertThatResponse()
                .extractedJsonValue("first_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                        .getString("first_name"))
                .perform();
        test.log(Status.PASS, "User first name matches the expected value.");

        api
                .assertThatResponse()
                .extractedJsonValue("last_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                        .getString("last_name"))
                .perform();
        test.log(Status.PASS, "User last name matches the expected value.");
    }

    @AfterSuite
    public void tearDownExtentReport() {
        // Flush the Extent Report
        extent.flush();
    }
}