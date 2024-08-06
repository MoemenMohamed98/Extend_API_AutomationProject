package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

public class E01_Get_fetchUserList {
   private SHAFT.API api;


    @Test
    public void valid_getTheUserList() {
        api= new SHAFT.API(ObjectModels.BaseURL);
        api
                .get("/users")
                .setTargetStatusCode(200)
                .perform();

        System.out.println(api.getResponseBody());

        api
                .assertThatResponse().time().isLessThanOrEquals(3000).perform();

        // Assert that Response body is not null
        api.assertThatResponse()
                .body().contains("data")
                .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();

        api.assertThatResponse()
                .extractedJsonValue("page")
                .isEqualTo("1").perform();

//        api
//                .assertThatResponse()
//                .body().isNotNull()
//                .withCustomReportMessage("The Response body for get profile request is Not Null").perform();
    }

        @Test
        public void valid_fetchTheUserList() {
            api= new SHAFT.API(ObjectModels.BaseURL);
//            given()
//                    .baseUri(ObjectModels.BaseURL)
//                    .log().all()
//
//            .when()
//                    .get("users?page=2")
//
//            .then()
//                    .log().all()
//                    .assertThat().statusCode(200);

            api
                    .get("/users?page=2")
                    .setTargetStatusCode(200)
                    .perform();

            // Print response body
            System.out.println(api.getResponseBody());

            api
                    .assertThatResponse().time().isLessThanOrEquals(3000).perform();

            // Assert that Response body is not null
            api.assertThatResponse()
                    .body().contains("data")
                    .withCustomReportMessage("The Response body for fetching the user list is Not Null").perform();

            api.assertThatResponse()
                    .extractedJsonValue("page")
                    .isEqualTo("2").perform();


        }

    }


