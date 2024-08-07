package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class E03_Post_createUser {
    private SHAFT.API api;


    @Test
    public void valid_Post_createUser(){

        // Initiate the Base URL
        api = new SHAFT.API(ObjectModels.BaseURL);

        // Set the type of the method / endpoint / Status Code/ Content Type / Request Body
        api
                .post("/users")
                .setContentType(ContentType.JSON)
                .setRequestBodyFromFile("src/test/resources/testDataFiles/createUser.json")
                .setTargetStatusCode(201)
                .perform();

        // Print response body
        System.out.println(api.getResponseBody());




            //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(2000).perform();


        api.assertThatResponse()
                .body().contains("createdAt")
                .withCustomReportMessage("The user is created successfully")
                .perform();

        // Validate that the user ID is correct
        api
                .assertThatResponse()
                .extractedJsonValue("id")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                .getString("id"))
                .withCustomReportMessage("User ID in response matches the requested ID.")
                .perform();

        // Assert that specific JSON values in the response body match the expected data from the JSON file
        api
                .assertThatResponse().extractedJsonValue("email")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                .getString("email"))
                .withCustomReportMessage("Check that user profile info has been completed Successfully")
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("first_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                .getString("first_name"))
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("last_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                .getString("last_name"))
                .perform();


    }
}
