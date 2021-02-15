package com.pongo.taf.utils;

import io.cucumber.messages.internal.com.google.common.net.HttpHeaders;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        traceRequest(filterableRequestSpecification);
        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        traceResponse(response);
        return response;
    }


    private void traceRequest(FilterableRequestSpecification request) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============================ REQUEST ============================================================================");
        sb.append("\n>> URI         : ");
        sb.append(request.getMethod());
        sb.append(", ");
        sb.append(request.getURI());
        List<Header> headers = request.getHeaders().asList().stream().filter(header -> header.getName().equals(HttpHeaders.AUTHORIZATION) || header.getName().equals(HttpHeaders.COOKIE)).collect(Collectors.toList());
        headers.forEach(header -> {
            sb.append("\n>> " + header.getName() + ": ");
            sb.append(header.getValue());
        });
        if (request.getBody() != null) {
            sb.append("\n>> Request body: ");
            sb.append(request.getBody().toString());
        }
        sb.append("\n=================================================================================================================");
        log.debug(sb.toString());
    }

    private void traceResponse(Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============================ RESPONSE ===========================================================================");
        sb.append("\n<< Status code  : ");
        sb.append(response.getStatusCode());
        sb.append("\n<< Correlation Id : ");
        sb.append(response.getHeader("pruwealth-api-correlation-id"));
        if (response.getBody() != null) {
            sb.append("\n<< Response body: ");
            sb.append(new String(response.getBody().asByteArray()));
        }
        sb.append("\n=================================================================================================================");
        log.debug(sb.toString());
    }
}
