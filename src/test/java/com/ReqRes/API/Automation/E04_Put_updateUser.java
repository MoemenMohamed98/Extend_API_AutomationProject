package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.HashMap;



public class E04_Put_updateUser {

private SHAFT.API api;

    @Test
    public void valid_Put_updateUserInfo(){

        HashMap<String,String> body = new HashMap<>();
        body.put("email","test12@gmail.com");
        body.put("first_name","Kim");
        body.put("last_name","Kim");
        body.put("phoneNumber","01033333333");


    api= new SHAFT.API(ObjectModels.BaseURL);

    api
            .put("/users/3")
            .setRequestBody(body)
            .setContentType(ContentType.JSON)
            .setTargetStatusCode(200)
            .perform();

        // Print response body
        System.out.println(api.getResponseBody());

         //******************************** Assertions ********************************//

    api
            .assertThatResponse().time().isLessThanOrEquals(2000).perform();

    api
                .assertThatResponse()
                .body().contains("updatedAt")
                .withCustomReportMessage("The user is updated successfully").perform();

        api
                .assertThatResponse().extractedJsonValue("email")
                .isEqualTo("test12@gmail.com")
                .withCustomReportMessage("Check that user profile info has been updated Successfully")
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("phoneNumber")
                .isEqualTo("01033333333")
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("first_name")
                .isEqualTo("Kim")
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("last_name")
                .isEqualTo("Kim")
                .perform();




















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
}
