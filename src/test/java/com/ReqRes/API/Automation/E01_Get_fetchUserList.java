package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class E01_Get_fetchUserList {

    private SHAFT.API api;


    @Test(priority = 1,description="Validate for fetching the use list by GET method")
    public void valid_getTheUserList() {

        // Initiate the Base URL
        api= new SHAFT.API(ObjectModels.BaseURL);

        // Set the type of the method / endpoint / Status Code
        api
                .get("/users")
                .setTargetStatusCode(200)
                .perform();

        // Print response body
        System.out.println(api.getResponseBody());


                 //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(2000).perform();

        // Assert that Response body is not null
//        api
//                .assertThatResponse()
//                .body().isNotNull()
//                .withCustomReportMessage("The Response body for get profile request is Not Null").perform();

        // Assert specific JSON values in the response body
        api.
                assertThatResponse()
                .body().contains("data")
                .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();

        api
                .assertThatResponse()
                .extractedJsonValue("page")
                .isEqualTo("1").perform();

        api
                .assertThatResponse()
                .extractedJsonValue("per_page")
                .isEqualTo("6").perform();

        api
                .assertThatResponse()
                .extractedJsonValue("total")
                .isEqualTo("12").perform();
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

                //********************************Shaft.API Assertions********************************//

            // Assert that Response time is Less than or Equals 2000 MS
            Validations
                    .assertThat().response(response)
                    .time().isLessThanOrEquals(2000).perform();

            Validations
                    .assertThat().response(response)
                    .body().contains("data")
                    .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();

//            Validations
//                    .assertThat().response(response)
//                    .body().isNotNull()
//                    .withCustomReportMessage("The Response body for get profile request is Not Null").perform();

            // Assert specific JSON values in the response body
            Validations
                    .assertThat().response(response)
                    .extractedJsonValue("page")
                    .isEqualTo("2").perform();

            Validations
                    .assertThat().response(response)
                    .extractedJsonValue("total")
                    .isEqualTo("12").perform();




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

    }


