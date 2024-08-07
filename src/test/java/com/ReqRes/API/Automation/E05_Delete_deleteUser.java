package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

public class E05_Delete_deleteUser {

    private SHAFT.API api;

    @Test
    public void valid_Delete_User_byID() {

        // Initiate the Base URL
        api = new SHAFT.API(ObjectModels.BaseURL);

        // Set the type of the method / endpoint / Status Code
        api
                .delete("/users/2")
                .setTargetStatusCode(204)
                .perform();

              //******************************** Assertions ********************************//

        // Assert that Response time is Less than or Equals 2000 MS
        api
                .assertThatResponse().time().isLessThanOrEquals(2000).perform();

        // Assert that the Response body is null and the user is deleted successfully
        api
                .assertThatResponse()
                .body().isNotNull()
                .withCustomReportMessage("The user info is deleted successfully")
                .perform();

    }
}
