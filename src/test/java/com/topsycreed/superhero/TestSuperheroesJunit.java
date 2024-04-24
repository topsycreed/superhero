package com.topsycreed.superhero;

import com.topsycreed.constants.TestData;
import com.topsycreed.controllers.SuperheroController;
import com.topsycreed.models.ErrorMessageModel;
import com.topsycreed.models.SuperheroModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.ExpectedToFail;
import org.junitpioneer.jupiter.Issue;
import org.junitpioneer.jupiter.RetryingTest;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuperheroesJunit {

    public static Stream<Arguments> getHeroes() {
        return Stream.of(
                Arguments.of(TestData.SUPERHERO_VALID_WITHOUT_PHONE),
                Arguments.of(TestData.SUPERHERO_VALID_WITH_PHONE)
        );
    }

    @Test
    @DisplayName("Get all superheroes and check status code")
    public void getAllSuperheroesTest() {
        Response response = new SuperheroController().getAllHeroes();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @RetryingTest(maxAttempts = 5, suspendForMs = 100)
    @DisplayName("Get all superheroes and check data")
    public void getAllSuperheroesAndCheckDataTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response response = new SuperheroController().getAllHeroes();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel[] actualSuperheroModels = response.as(SuperheroModel[].class);
        System.out.println("Seraching hero with id = " + expectedSuperheroModel.getId());
        assertThat(getModelById(actualSuperheroModels, expectedSuperheroModel.getId())).isEqualTo(expectedSuperheroModel);
    }

    @Test
    @DisplayName("Try to get all superheroes with invalid path")
    public void getAllSuperheroesTestWithInvalidPath() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().getAllHeroesWithInvalidPath();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @ParameterizedTest
    @MethodSource("getHeroes")
    @DisplayName("Get superhero by an id and check status code")
    @Issue("https://github.com/junit-pioneer/junit-pioneer/issues/405")
    public void getSuperheroByIdTest(SuperheroModel hero) {
        Response response = new SuperheroController().getHero(hero.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @RetryingTest(3)
    @DisplayName("Get superhero by an id and check status code and data")
    public void getSuperheroByIdWithDataCheckTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response response = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Try to get superhero by invalid id")
    public void getSuperheroByInvalidIdTest() {
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().getHeroWithInvalidId();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @ExpectedToFail("Invalid response status code")
    @DisplayName("Try to get superhero by not existed id")
    public void getSuperheroByNotExistedIdTest() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().getHero(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Add superhero with null phone and check status code")
    public void postSuperheroTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @RetryingTest(3)
    @DisplayName("Add superhero with null phone and check status code, data")
    public void postSuperheroWithDataCheckTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Add superhero with null phone and check status code, data and get")
    public void postSuperheroWithGetCheckE2ETest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
        //Get
        Response getResponce = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(getResponce.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel getSuperheroModel = getResponce.as(SuperheroModel.class);
        assertThat(getSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Add superhero with not null phone and check status code and data")
    public void postSuperheroWithPhoneTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITH_PHONE);
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Add superhero with not null phone and check status code, data and get check")
    public void postSuperheroWithPhoneWithGetCheckE2ETest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITH_PHONE);
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
        //Get
        Response getResponce = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(getResponce.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel getActualSuperheroModel = getResponce.as(SuperheroModel.class);
        assertThat(getActualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Try to add superhero with not valid date")
    public void postSuperheroWithNotValidDateTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_DATE);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to add superhero using invalid path")
    public void postSuperheroWithNotValidPathTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().addNewHeroWithInvalidPath(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to add superhero without required field")
    public void postSuperheroWithoutRequiredFieldTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_WITHOUT_REQUIRED_FIELD);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to add superhero with gender not from enum")
    public void postSuperheroWithoutEnumGenderTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_WITHOUT_ENUM_GENDER);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Update superhero and check response")
    public void putSuperheroTest() {
        int id = TestData.WITH_VALID_DATA1;
        SuperheroModel beforeChangesSuperheroModel = new SuperheroController().getHero(id).as(SuperheroModel.class);
        String gender = Objects.equals(beforeChangesSuperheroModel.getGender(), SuperheroModel.Gender.M.name()) ? SuperheroModel.Gender.F.name() : SuperheroModel.Gender.M.name();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(beforeChangesSuperheroModel.getBirthDate(), formatter).plusDays(1);
        SuperheroModel expectedSuperheroModel = new SuperheroModel(id, beforeChangesSuperheroModel.getFullName() + "1", localDate.toString(), beforeChangesSuperheroModel.getCity() + "1", beforeChangesSuperheroModel.getMainSkill() + "1", gender, beforeChangesSuperheroModel.getPhone() + "1");
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @RetryingTest(3)
    @DisplayName("Update superhero and check status code, that values changed")
    public void putSuperheroWithGetCheckE2ETest() {
        int id = TestData.WITH_VALID_DATA1;
        SuperheroModel beforeChangesSuperheroModel = new SuperheroController().getHero(id).as(SuperheroModel.class);
        String gender = Objects.equals(beforeChangesSuperheroModel.getGender(), SuperheroModel.Gender.M.name()) ? SuperheroModel.Gender.F.name() : SuperheroModel.Gender.M.name();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(beforeChangesSuperheroModel.getBirthDate(), formatter).plusDays(1);
        SuperheroModel expectedSuperheroModel = new SuperheroModel(id, beforeChangesSuperheroModel.getFullName() + "1", localDate.toString(), beforeChangesSuperheroModel.getCity() + "1", beforeChangesSuperheroModel.getMainSkill() + "1", gender, beforeChangesSuperheroModel.getPhone() + "1");
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        //Get
        Response getResponce = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(getResponce.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel getActualSuperheroModel = getResponce.as(SuperheroModel.class);
        assertThat(getActualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @RetryingTest(3)
    @DisplayName("Try to update superhero with invalid date")
    public void putSuperheroWithInvalidDateTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_DATE_UPDATE);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to update superhero with not existed id")
    public void putSuperheroWithNotExistedIdTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_NOT_EXISTED_ID);
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to update superhero without required fields")
    public void putSuperheroWithoutRequiredFieldsTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_WITHOUT_REQUIRED_FIELD);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Try to update superhero with gender not from enum")
    public void putSuperheroWithoutEnumGenderTest() {
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_INVALID_WITHOUT_ENUM_GENDER);
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @RetryingTest(3)
    @DisplayName("Delete created superhero and check status")
    public void deleteSuperheroTest() {
        //post
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response postResponse = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(postResponse.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = postResponse.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
        //delete
        Response response = new SuperheroController().deleteHero(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test
    @ExpectedToFail("Invalid response content-type")
    @DisplayName("Delete created superhero and check status and get")
    public void deleteSuperheroWithGetCheckE2ETest() {
        //post
        SuperheroModel expectedSuperheroModel = new SuperheroModel(TestData.SUPERHERO_VALID_WITHOUT_PHONE);
        Response postResponse = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(postResponse.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = postResponse.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
        System.out.println("Id = " + actualSuperheroModel.getId());
        //delete
        Response response = new SuperheroController().deleteHero(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        //get
        Response getResponse = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(getResponse.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
    }

    @RetryingTest(3)
    @DisplayName("Try to delete superhero with not existed id")
    public void deleteSuperheroWithNotExistedIdTest() {
        Response response = new SuperheroController().deleteHero(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @RetryingTest(3)
    @DisplayName("Try to delete superhero with invalid path")
    public void deleteSuperheroWithInvalidPathTest() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().deleteHeroWithInvalidPath(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    /**
     * Returns a model by an id from all models, if not found - throws an exception
     * @param models
     * @param id
     * @return
     */
    private SuperheroModel getModelById(SuperheroModel[] models, int id) {
        List<SuperheroModel> list = Arrays.asList(models);
        try {
            return list.stream().filter((p) -> p.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Not found model in SuperheroModels with an id = " + id);
        }
    }
}
