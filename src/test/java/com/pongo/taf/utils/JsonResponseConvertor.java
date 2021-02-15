package com.pongo.taf.utils;

import com.pongo.taf.context.Context;
import io.cucumber.messages.internal.com.google.gson.JsonSyntaxException;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;


import com.google.inject.Inject;

import static org.junit.Assert.fail;

public class JsonResponseConvertor {

    @Inject
    private Context context;

    public <T> T convertRestResponseToResponseObject(Response response, Class<T> genericType) {
        try {
            T responseObject = response.as(genericType, ObjectMapperType.GSON);
            return responseObject;
        } catch (JsonSyntaxException exception) {
            fail(String.format("%nRequest: %s%nResponse: %s%nStatusCode: %s", Assertions.getFullRequest(context.getRequest()),
                    Assertions.getFullResponse(response), response.getStatusCode()));
        }
        return null;
    }

}
