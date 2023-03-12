package com.topsycreed.superhero;

import com.topsycreed.CommonConfiguration;
import com.topsycreed.controllers.SuperheroController;
import com.topsycreed.extenstions.RestAssuredExtension;
import com.topsycreed.models.SuperheroModel;
import io.qameta.allure.AllureId;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Superhero")
@Tags({@Tag("smoke"), @Tag("superhero")})
@SpringBootTest(classes = {CommonConfiguration.class})
@ExtendWith(RestAssuredExtension.class) //https://blog.aspiresys.pl/technology/junit-5-rest-assured-using-extension-api/
public class TestSuperheroesWithSpring {

    @Autowired
    private SuperheroController superheroController;

    @Test
    @AllureId("1")
    @DisplayName("Get all superheroes and check status code")
    @Link(name = "JIRA-123", url = "https://jira.project.ru/browse/JIRA-123")
    @Owner("g.chursov")
    public void getAllSuperheroesTest() {
        Response response = superheroController.getAllHeroes();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    static List<Object[]> provideIdAndNamesForSuperheroes() {
        return Arrays.asList(new Object[][] {
                { 1, "Doctor Strange" },
                { 2, "Моряк" }
        });
    }

    @ParameterizedTest
    @AllureId("2")
    @DisplayName("Get superhero by id and check status code")
    @Link(name = "JIRA-123", url = "https://jira.project.ru/browse/JIRA-123")
    @Owner("g.chursov")
    @MethodSource("provideIdAndNamesForSuperheroes")
    public void getSuperheroByIdTest(int id, String name) {
        Response response = superheroController.getHero(id);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        assertThat(actualSuperheroModel.getFullName()).isEqualTo(name);
    }

    @Test
    @AllureId("3")
    @DisplayName("Get all superheroes and check status code")
    @Link(name = "JIRA-123", url = "https://jira.project.ru/browse/JIRA-123")
    @Owner("g.chursov")
    public void getWithExtend() {
        String response = RestAssured.get("/superheroes/1").prettyPrint();
        assertThat(response).contains("Doctor Strange");
    }
}
