package com.pongo.taf.context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Context {

    Response response;
    RequestSpecification request;

}
