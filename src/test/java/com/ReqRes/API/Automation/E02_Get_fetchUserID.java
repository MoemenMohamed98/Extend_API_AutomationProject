package com.ReqRes.API.Automation;

import Utilities.ObjectModels;
import com.shaft.driver.SHAFT;
import org.testng.annotations.Test;

public class E02_Get_fetchUserID {
    private SHAFT.API api;




    @Test
    public void valid_Get_UserDataByID(){
        api=new SHAFT.API(ObjectModels.BaseURL);

        api
                .get("/users/1")
                .setTargetStatusCode(200)
                .perform();

        System.out.println(api.getResponseBody());

        api
                .assertThatResponse()
                .body().contains("data")
                .perform();

        api
                .assertThatResponse()
                .extractedJsonValue("data.id")
                .isEqualTo("3").perform();

        api
                .assertThatResponse()
                .body()
                .contains("first_name")
                .perform();

        api
                .assertThatResponse()
                .body()
                .contains("email")
                .perform();

        api
                .assertThatResponse().time()
                .isLessThanOrEquals(3000)
                .perform();


    }

}
