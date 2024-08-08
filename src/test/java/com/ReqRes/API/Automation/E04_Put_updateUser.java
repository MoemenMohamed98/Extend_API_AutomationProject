package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.HashMap;



public class E04_Put_updateUser {

    private SHAFT.API api;
    private ExtentReports extent;
    private ExtentTest test;


    @BeforeSuite
    public void setupExtentReport() {
        // Set up Extent Report with Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-reports/extentReports.html");
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("Update User API Tests");
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
        test = extent.createTest("Update User Info", "Verify that user information can be updated successfully");
    }

    @Test
    public void valid_Put_updateUserInfo(){

        //Initiate The request body using HashMAp
        HashMap<String,String> body = new HashMap<>();
        body.put("email","test12@gmail.com");
        body.put("first_name","Kim");
        body.put("last_name","Kim");
        body.put("phoneNumber","01033333333");


    // Set the type of the method / endpoint / Status Code/ Content Type / Request Body
    api
            .put("/users/3")
            .setRequestBody(body)
            .setContentType(ContentType.JSON)
            .setTargetStatusCode(200)
            .perform();

        test.log(Status.INFO, "PUT request made to /users/3 with body: " + body.toString());

        // Log response body
        String responseBody = api.getResponseBody();
        test.log(Status.INFO, "API response: " + responseBody);

        // Print response body
        System.out.println(api.getResponseBody());

         //******************************** Assertions ********************************//

    api
            .assertThatResponse().time().isLessThanOrEquals(2000).perform();
    test.log(Status.PASS, "Response time is within the expected limit.");

    api
                .assertThatResponse()
                .body().contains("updatedAt")
                .withCustomReportMessage("The user is updated successfully").perform();
    test.log(Status.PASS, "The user is updated successfully.");

        api
                .assertThatResponse().extractedJsonValue("email")
                .isEqualTo("test12@gmail.com")
                .withCustomReportMessage("Check that user email has been updated successfully.")
                .perform();
        test.log(Status.PASS, "User email has been updated successfully.");

        api
                .assertThatResponse()
                .extractedJsonValue("phoneNumber")
                .isEqualTo("01033333333")
                .perform();
        test.log(Status.PASS, "User phone number has been updated successfully.");

        api
                .assertThatResponse()
                .extractedJsonValue("first_name")
                .isEqualTo("Kim")
                .perform();
        test.log(Status.PASS, "User first name has been updated successfully.");

        api
                .assertThatResponse()
                .extractedJsonValue("last_name")
                .isEqualTo("Kim")
                .perform();
        test.log(Status.PASS, "User last name has been updated successfully.");




        // Using RestAssured
//        File body = new File("src/test/resources/testDataFiles/createUser.json");
//
//        given()
//                .baseUri("https://reqres.in/api")
//                .contentType(ContentType.JSON)
//                .body(body)
//                .log().all()
//
//        .when()
//                .post("/users")
//
//        .then()
//                .log().all()
//                .assertThat().statusCode(201)
////                .assertThat().body("id", hasValue(13))
//                .assertThat().body("email", contains("test123@gmail.com"));

    }

    @AfterSuite
    public void tearDownExtentReport() {
        extent.flush();
    }
}
