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

public class SuperheroController {

    private RequestSpecification requestSpecification;

    public SuperheroController() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://superhero.qa-test.csssr.com/")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(15000L))
                .build();
        RestAssured.defaultParser = Parser.JSON;
    }

    public Response addNewHero(SuperheroModel superheroModel) {
        Response response = RestAssured.given(requestSpecification)
                .body(superheroModel)
                .post("superheroes/");
        log(response);
        return response;
    }

    public Response addNewHeroWithInvalidPath(SuperheroModel superheroModel) {
        Response response = RestAssured.given(requestSpecification)
                .body(superheroModel)
                .post("invalid/");
        log(response);
        return response;
    }

    public Response updateExistingHero(SuperheroModel superheroModel) {
        Response response = RestAssured.given(requestSpecification)
                .body(superheroModel)
                .put("superheroes/" + superheroModel.getId());
        log(response);
        return response;
    }

    public Response updateExistingHeroWithoutLogging(SuperheroModel superheroModel) {
        Response response = RestAssured.given(requestSpecification)
                .body(superheroModel)
                .put("superheroes/" + superheroModel.getId());
        return response;
    }

    public Response getHero(int id) {
        Response response = RestAssured.given(requestSpecification)
                .get("superheroes/" + id);
        log(response);
        return response;
    }

    public Response getHeroWithInvalidId() {
        Response response = RestAssured.given(requestSpecification)
                .get("superheroes/" + "invalid");
        log(response);
        return response;
    }

    public Response getAllHeroes() {
        Response response = RestAssured.given(requestSpecification)
                .get("superheroes");
        log(response);
        return response;
    }

    public Response getAllHeroesWithInvalidPath() {
        Response response = RestAssured.given(requestSpecification)
                .get("invalid");
        log(response);
        return response;
    }

    public Response deleteHero(int id) {
        Response response = RestAssured.given(requestSpecification)
                .delete("superheroes/" + id);
        log(response);
        return response;
    }

    public Response deleteHeroWithInvalidPath(int id) {
        Response response = RestAssured.given(requestSpecification)
                .delete("invalid/" + id);
        log(response);
        return response;
    }

    /**
     * Procedure to output status code and body of a response
     * @param response
     */
    private void log(Response response) {
        System.out.println("Status code: " + response.statusCode());
        response.getBody().prettyPrint();
    }
}
