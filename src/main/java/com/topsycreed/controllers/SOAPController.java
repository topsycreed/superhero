package com.topsycreed.controllers;

import com.topsycreed.models.SuperheroModel;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class SOAPController {

    private RequestSpecification requestSpecification;

    public SOAPController() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://soap.qa-test.csssr.com/ws/")
//                .setContentType(ContentType.XML)
//                .addHeader()
                .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.XML)
                .expectResponseTime(Matchers.lessThan(15000L))
                .build();
        RestAssured.defaultParser = Parser.XML;
    }

    public Response get(String body) {
        Response response = RestAssured.given(requestSpecification)
                .contentType("text/xml")
                .body(body)
                .post();
        return response;
    }
}
