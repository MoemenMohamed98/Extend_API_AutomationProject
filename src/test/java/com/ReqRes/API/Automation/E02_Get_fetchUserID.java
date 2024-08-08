package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class E02_Get_fetchUserID {

    private SHAFT.API api;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setupExtentReport() {

        // Set up Extent Report with Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReports.html");
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("Fetch User API Tests");
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
        test = extent.createTest("Fetch User by ID", "Verify that user information can be fetched by ID");
    }

    @Test
    public void valid_Get_fetchUserInfoByID() {

        // Set the type of the method / endpoint / Status Code
        Response response =
                given()
                        .baseUri(ObjectModels.BaseURL)
                        .log().all()

                        .when()
                        .get("/users/1")

                        .then()
                        .assertThat().statusCode(200)
                        .extract().response();

        test.log(Status.INFO, "GET request made to /users/1");


        //********************************Shaft.API Assertions********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        Validations
                .assertThat().response(response)
                .time().isLessThanOrEquals(2000)
                .withCustomReportMessage("Response time is within the expected limit.")
                .perform();
        test.log(Status.PASS, "Response time is within the expected limit.");

        // Assert that response matches the predefined JSON schema
        Validations
                .assertThat().response(response)
                .matchesSchema("src/test/resources/testDataFiles/userInfo.json")
                .withCustomReportMessage("Response matches the expected JSON schema.")
                .perform();
        test.log(Status.PASS, "Response matches the expected JSON schema.");

        // Assert specific JSON values in the response body
        Validations
                .assertThat().response(response)
                .body().contains("data")
                .withCustomReportMessage("Response contains expected fields 'data'")
                .perform();
        test.log(Status.PASS, "Response contains expected fields 'data'.");

        // Assert specific JSON values in the response body
        Validations
                .assertThat().response(response)
                .body().contains("support")
                .withCustomReportMessage("The Response body for fetching the user list is Not Null")
                .perform();
        test.log(Status.PASS, "Response contains expected fields 'support'.");

        // Validate that the user ID is correct
        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.id")
                .isEqualTo("1")
                .withCustomReportMessage("User ID in response matches the requested ID.")
                .perform();
        test.log(Status.PASS, "User ID in response matches the requested ID.");

        // Assert that specific JSON values in the response body match the expected data from the JSON file
        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.email")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                        .getString("data.email"))
                .perform();
        test.log(Status.PASS, "User email matches the expected value.");

        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.last_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                        .getString("data.last_name"))
                .perform();
        test.log(Status.PASS, "User last name matches the expected value.");

        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.first_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                        .getString("data.first_name"))
                .perform();
        test.log(Status.PASS, "User first name matches the expected value.");

    }

    @AfterSuite
    public void tearDownExtentReport() {
        // Flush the Extent Report
        extent.flush();
    }

}