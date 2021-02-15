package com.pongo.taf.stepdefs;

import com.pongo.taf.context.Context;
import io.cucumber.java.en.Then;
import com.google.inject.Inject;
import java.util.HashMap;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertEquals;
import static com.pongo.taf.utils.Assertions.verifyStatusCode;
public class GeneralSteps {

    private Context context;

    @Inject
    public GeneralSteps(Context context) {
        this.context = context;
    }

    @Then("^a (\\d+) status code is returned$")
    public void verifyStatusCodeReturned(int expectedStatusCode) {
        System.out.println("REQ:"+context.getRequest().toString() + "  RESP:  " + context.getResponse().toString());
        verifyStatusCode(context.getRequest(),context.getResponse(), expectedStatusCode);
    }

    @Then("^a \"([^\"]*)\" status code is returned$")
    public void aStatusCodeIsReturned(int expectedStatusCode) {
        verifyStatusCode(context.getRequest(),context.getResponse(), expectedStatusCode);
    }

    @Then("^verify response message \"([^\"]*)\"$")
    public void verifyResponseMessage(String expectedMessage) {
        String actualMessage = context.getResponse().then().extract().body().path("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Then("^verify response reason \"([^\"]*)\"$")
    public void verifyResponseReason(String expectedErrorReason) {
        String actualErrorReason = context.getResponse().then().extract().body().path("reason");
        assertEquals(expectedErrorReason, actualErrorReason);
    }

    @Then("verify {string} field has {string} value")
    public void verifyFieldHasValue(String key, String value) {
        String actualErrorReason = context.getResponse().then().extract().body().path(key);
        assertEquals(value, actualErrorReason);
    }

    @Then("^verify empty response body$")
    public void verifyEmptyResponseBody() {
        context.getResponse().then().contentType(isEmptyOrNullString());
    }

    @Then("^verify empty array response body$")
    public void verifyEmptyArrayResponseBody() {
        context.getResponse().then().body("", empty());
    }

    @Then("the validation error for field {string} is {string}")
    public void theValidationErrorForFieldIs(String expectedValidationKey, String expectedValidationErrorValue) {
        if (!(expectedValidationKey.equals("") || expectedValidationErrorValue.equals(""))) {
            HashMap<String, String> actualValidationError = context.getResponse().then().extract().body().path("validationErrors");
            assertEquals(expectedValidationErrorValue, actualValidationError.get(expectedValidationKey));
        }
    }

}
