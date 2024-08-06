package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
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
                .withCustomReportMessage("The user is created successfully").perform();

        api
                .assertThatResponse().extractedJsonValue("email")
                .isEqualTo("test123@gmail.com")
                .withCustomReportMessage("Check that user profile info has been completed Successfully")
                .perform();


        api
                .assertThatResponse().extractedJsonValue("first_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/createUser.json"))
                        .getString("first_name"))
                .perform();


    }
}
