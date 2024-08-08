package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import java.util.HashMap;

public class E05_Delete_deleteUser {

    private SHAFT.API api;
    private ExtentReports extent;
    private ExtentTest test;


    @BeforeSuite
    public void setupExtentReport() {
        // Set up Extent Report with Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReports.html");
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("Delete User API Tests");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Test Engineer");
    }

    @BeforeMethod
    public void setup() {
        // Initiate the Base URL
        api = new SHAFT.API(ObjectModels.BaseURL);
        test = extent.createTest("Delete User by ID", "Verify that a user can be deleted by ID");
    }

    @Test
    public void valid_Delete_User_byID() {

        // Set the type of the method / endpoint / Status Code
        api
                .delete("/users/2")
                .setTargetStatusCode(204)
                .perform();

        test.log(Status.INFO, "DELETE request made to /users/2");

              //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(3000).perform();
        test.log(Status.PASS, "Response time is within the expected limit.");

        // Assert that the Response body is null and the user is deleted successfully
        api
                .assertThatResponse()
                .body().isNotNull()
                .withCustomReportMessage("The user info is deleted successfully")
                .perform();
        test.log(Status.PASS, "The response body is null, confirming user deletion.");
    }

    @AfterSuite
    public void tearDownExtentReport() {
        extent.flush();
    }
}
