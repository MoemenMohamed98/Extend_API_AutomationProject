package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class E01_Get_fetchUserList {

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
        test = extent.createTest("Fetch User List", "Verify that userList information can be fetched");
    }


    @Test(priority = 1,description="Validate for fetching the use list by GET method")
    public void valid_getTheUserList() {

        // Set the type of the method / endpoint / Status Code
        api
                .get("/users")
                .setTargetStatusCode(200)
                .perform();

        test.log(Status.INFO, "GET request made to /users");

        // Print response body
        System.out.println(api.getResponseBody());


                 //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(2000).perform();
        test.log(Status.PASS, "Response time is within the expected limit.");

        // Assert that Response body is not null
//        api
//                .assertThatResponse()
//                .body().isNotNull()
//                .withCustomReportMessage("The Response body for get profile request is Not Null").perform();

        // Assert specific JSON values in the response body
        api.
                assertThatResponse()
                .body().contains("data")
                .withCustomReportMessage("The Response body for fetching the user list is Not Null")
                .perform();
        test.log(Status.PASS, "Response contains expected fields 'data'.");

        api
                .assertThatResponse()
                .extractedJsonValue("page")
                .isEqualTo("1")
                .perform();
        test.log(Status.PASS, "Response contains expected fields 'page'.");

        api
                .assertThatResponse()
                .extractedJsonValue("per_page")
                .isEqualTo("6")
                .perform();
        test.log(Status.PASS, "Response contains expected fields 'per-page'.");

        api
                .assertThatResponse()
                .extractedJsonValue("total")
                .isEqualTo("12").perform();
        test.log(Status.PASS, "Response contains expected fields 'total'.");
    }


        @Test(priority = 2,description="Validate fetching user list on page 2 by GET method")
        public void valid_fetchTheUserList() {

            // Set the type of the method / endpoint / Status Code
         Response response =
                  given()
                    .baseUri(ObjectModels.BaseURL)
                    .log().all()

                .when()
                    .get("users?page=2")

                 .then()
                    .assertThat().statusCode(200)
                    .extract().response();

            test.log(Status.INFO, "GET request made to /users");

                //********************************Shaft.API Assertions********************************//

            // Assert that Response time is Less than or Equals 2000 MS
            Validations
                    .assertThat().response(response)
                    .time().isLessThanOrEquals(2000).perform();
            test.log(Status.PASS, "Response time is within the expected limit.");

            Validations
                    .assertThat().response(response)
                    .body().contains("data")
                    .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();
            test.log(Status.PASS, "Response contains expected fields 'data'.");

//            Validations
//                    .assertThat().response(response)
//                    .body().isNotNull()
//                    .withCustomReportMessage("The Response body for get profile request is Not Null").perform();

            // Assert specific JSON values in the response body
            Validations
                    .assertThat().response(response)
                    .extractedJsonValue("page")
                    .isEqualTo("2").perform();
            test.log(Status.PASS, "Response contains expected fields 'per-page'.");

            Validations
                    .assertThat().response(response)
                    .extractedJsonValue("total")
                    .isEqualTo("12").perform();
            test.log(Status.PASS, "Response contains expected fields 'total'.");



         // Using Shaft
//            api= new SHAFT.API(ObjectModels.BaseURL);
//            api
//                    .get("/users?page=2")
//                    .setTargetStatusCode(200)
//                    .perform();
//
//            // Print response body
//            System.out.println(api.getResponseBody());
//
//            api
//                    .assertThatResponse().time().isLessThanOrEquals(3000).perform();
//
//            // Assert that Response body is not null
//            api.assertThatResponse()
//                    .body().contains("data")
//                    .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();
//
//            api.assertThatResponse()
//                    .extractedJsonValue("page")
//                    .isEqualTo("2").perform();

        }

    @AfterSuite
    public void tearDownExtentReport() {
        // Flush the Extent Report
        extent.flush();
    }

    }


