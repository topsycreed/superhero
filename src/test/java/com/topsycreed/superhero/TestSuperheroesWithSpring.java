package com.topsycreed.superhero;

import com.topsycreed.CommonConfiguration;
import com.topsycreed.controllers.SuperheroController;
import com.topsycreed.extenstions.ClientExtension;
import com.topsycreed.extenstions.RestAssuredExtension;
import com.topsycreed.extenstions.ValidSuperheroParameterResolver;
import com.topsycreed.models.Client;
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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
                { EventType.REGISTERED, "rejected" },
                { EventType.REJECTED, "applied" }
        });
    }

    @ParameterizedTest(name = "For superhero with name {1}")
    @AllureId("2")
    @DisplayName("Get superhero by id and check status code")
    @Link(name = "JIRA-123", url = "https://jira.project.ru/browse/JIRA-123")
    @Owner("g.chursov")
    @MethodSource("provideIdAndNamesForSuperheroes")
    public void getSuperheroByIdTest(EventType eventType, String status) {
        System.out.println(eventType);
        System.out.println(status);
    }

    public static Stream eventTypeStream() {
        return Stream.of(EventType.REJECTED, EventType.REGISTERED);
    }

    @ParameterizedTest(name = "For superhero with event {0}")
    @AllureId("2")
    @DisplayName("Get superhero by id and check status code")
    @Link(name = "JIRA-123", url = "https://jira.project.ru/browse/JIRA-123")
    @Owner("g.chursov")
    @ExtendWith(ClientExtension.class)
    @MethodSource("eventTypeStream")
    public void getSuperheroByIdTestWithBothExtensionAndParamTest(EventType eventType, Client client) {
        System.out.println(client);
        System.out.println(eventType);
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
