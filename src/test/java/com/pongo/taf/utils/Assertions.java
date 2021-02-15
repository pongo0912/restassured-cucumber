package com.pongo.taf.utils;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Assertions {

    public static <T> void assertObject(T expected, T actual) throws IOException {
        if (expected != null) {
            assertEquals(JsonDataLoader.convertToJsonObject(expected), JsonDataLoader.convertToJsonObject(actual));
        }
    }

    public static <T> void verifyList(List<T> expectedList, List<T> actualList) throws IOException {
        for (T account : expectedList) {
            assertObject(account, actualList.get(expectedList.indexOf(account)));
        }
    }

    public static Response verifyStatusCode(RequestSpecification requestSpecification,  Response response, int expectedStatusCode) {
        assertEquals(
                String.format("%nRequest: %s%n%nResponse: %s%n%n", getFullRequest(requestSpecification),
                        getFullResponse(response)),
                expectedStatusCode, response.getStatusCode());
        return response;
    }

    public static String verifyResponseValueNotNullReturnValue(RequestSpecification requestSpecification,Response response, String path) {
        verifySuccessfulStatusCode(requestSpecification, response);
        String value = response.then().extract().body().path(path);
        assertNotNull(String.format("Request: %s%n%nResponse: %s%n%n",
                getFullRequest(requestSpecification), getFullResponse(response)), value);
        return value;
    }

    public static String getFullResponse(Response response) {
        return String.format("%nHttp Status Code: %s%nOrigin: %s%nDate: %s%nCorrelation Id: %s%nResponse Body: %s%nTimeTaken: %s%n%n",
                response.statusCode(), response.header("Access-Control-Allow-Origin"), response.getHeader("Date"),
                response.header("pruwealth-api-correlation-id"), response.body().asString(), response.getTimeIn(TimeUnit.SECONDS));
    }

    public static String getFullRequest(RequestSpecification requestSpecification) {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;
        String requestBody, requestQueryParams;
        if (filterableRequestSpecification.getBody() == null) {
            requestBody = "N/A";
        } else {
            requestBody = filterableRequestSpecification.getBody().toString();
        }
        if (filterableRequestSpecification.getQueryParams() == null) {
            requestQueryParams = "N/A";
        } else {
            requestQueryParams = filterableRequestSpecification.getQueryParams().toString();
        }
        return String.format("%nBase URI: %s%nQueryParam: %s%nBody: %s%nHeaders: %s",
                filterableRequestSpecification.getBaseUri(), requestQueryParams, requestBody, filterableRequestSpecification.getHeaders());

    }

    public static void verifySuccessfulStatusCode(RequestSpecification requestSpecification, Response response) {
        int statusCode = response.getStatusCode();
        assertTrue(String.format("%nRequest: %s%nResponse: %s", getFullRequest(requestSpecification), getFullResponse(response)), (statusCode == 200 || statusCode == 201 || statusCode == 204));
    }

}
