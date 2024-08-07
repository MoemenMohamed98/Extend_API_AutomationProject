package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class E02_Get_fetchUserID {

    private SHAFT.API api;



    @Test
    public void valid_Get_fetchUserInfoByID(){

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


        //********************************Shaft.API Assertions********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        Validations
                .assertThat().response(response)
                .time().isLessThanOrEquals(2000)
                .withCustomReportMessage("Response time is within the expected limit.")
                .perform();

        // Assert that response matches the predefined JSON schema
        Validations
                .assertThat().response(response)
                .matchesSchema("src/test/resources/testDataFiles/userInfo.json")
                .withCustomReportMessage("Response matches the expected JSON schema.")
                .perform();

        // Assert specific JSON values in the response body
        Validations
                .assertThat().response(response)
                .body().contains("data")
                .withCustomReportMessage("Response contains expected fields 'data'")
                .perform();

        // Assert specific JSON values in the response body
        Validations
                .assertThat().response(response)
                .body().contains("support")
                .withCustomReportMessage("The Response body for fetching the user list is Not Null")
                .perform();

        // Validate that the user ID is correct
        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.id")
                .isEqualTo("1")
                .withCustomReportMessage("User ID in response matches the requested ID.")
                .perform();

        // Assert that specific JSON values in the response body match the expected data from the JSON file
        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.email")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                .getString("data.email"))
                .perform();

        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.last_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                .getString("data.last_name"))
                .perform();

        Validations
                .assertThat().response(response)
                .extractedJsonValue("data.first_name")
                .isEqualTo(JsonPath.from(new File("src/test/resources/testDataFiles/userInfo.json"))
                .getString("data.first_name"))
                .perform();

    }

}
