package com.topsycreed.superhero;

import com.topsycreed.constants.TestData;
import com.topsycreed.controllers.SuperheroController;
import com.topsycreed.models.ErrorMessageModel;
import com.topsycreed.models.SuperheroModel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSuperheroes {

    @Test(groups = {"get", "smoke"}, description = "Get all superheroes and check status code")
    public void getAllSuperheroesTestImprovedOk() {
        Response response = new SuperheroController().getAllHeroes();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test(groups = {"get"}, description = "Get all superheroes and check data")
    public void getAllSuperheroesTestImproved() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        Response response = new SuperheroController().getAllHeroes();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel[] actualSuperheroModel = response.as(SuperheroModel[].class);
        assertThat(getModelById(actualSuperheroModel, expectedSuperheroModel.getId())).isEqualTo(expectedSuperheroModel);
    }

    @Test(groups = {"get", "negative"}, description = "Try to get all superheroes with invalid path")
    public void getAllSuperheroesTestWithInvalidPath() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().getAllHeroesWithInvalidPath();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"get", "smoke"}, description = "Get superhero by an id and check status code and data")
    public void getSuperheroByIdTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        Response response = new SuperheroController().getHero(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @Test(groups = {"get", "negative"}, description = "Try to get superhero by invalid id")
    public void getSuperheroByInvalidIdTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().getHeroWithInvalidId(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"get", "negative"}, description = "Try to get superhero by not existed id")
    public void getSuperheroByNotExistedIdTest() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().getHero(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"post", "smoke"}, description = "Add superhero with null phone and check status code, data")
    public void postSuperheroTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @Test(groups = {"post"}, description = "Add superhero with null phone and check status code, data and get")
    public void postSuperheroWithGetCheckTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
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

    @Test(groups = {"post", "smoke"}, description = "Add superhero with not null phone and check status code and data")
    public void postSuperheroWithPhoneTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITH_PHONE;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = response.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
    }

    @Test(groups = {"post"}, description = "Add superhero with not null phone and check status code, data and get check")
    public void postSuperheroWithPhoneWithGetCheckTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITH_PHONE;
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

    @Test(groups = {"post", "negative"}, description = "Try to add superhero with not valid date")
    public void postSuperheroWithNotValidDateTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_DATE;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"post", "negative"}, description = "Try to add superhero using invalid path")
    public void postSuperheroWithNotValidPathTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().addNewHeroWithInvalidPath(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"post", "negative"}, description = "Try to add superhero without required field")
    public void postSuperheroWithoutRequiredFieldTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_WITHOUT_REQUIRED_FIELD;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"post", "negative"}, description = "Try to add superhero with gender not from enum")
    public void postSuperheroWithoutEnumGenderTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_WITHOUT_ENUM_GENDER;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"put", "smoke"}, description = "Update superhero and check response")
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

    @Test(groups = {"put"}, description = "Update superhero and check status code, that values changed")
    public void putSuperheroWithGetCheckTest() {
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

    @Test(groups = {"put", "negative"}, description = "Try to update superhero with invalid date")
    public void putSuperheroWithInvalidDateTest() {
      SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_DATE_UPDATE;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"put", "negative"}, description = "Try to update superhero with not existed id")
    public void putSuperheroWithNotExistedIdTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_NOT_EXISTED_ID;
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"put", "negative"}, description = "Try to update superhero without required fields")
    public void putSuperheroWithoutRequiredFieldsTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_WITHOUT_REQUIRED_FIELD;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"put", "negative"}, description = "Try to update superhero with gender not from enum")
    public void putSuperheroWithoutEnumGenderTest() {
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_INVALID_WITHOUT_ENUM_GENDER;
        ErrorMessageModel expectedError = TestData.BAD_REQUEST_ERROR;
        Response response = new SuperheroController().updateExistingHero(expectedSuperheroModel);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_BAD_REQUEST);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test(groups = {"delete", "smoke"}, description = "Delete created superhero and check status")
    public void deleteSuperheroTest() {
        //post
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
        Response postResponse = new SuperheroController().addNewHero(expectedSuperheroModel);
        assertThat(postResponse.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        SuperheroModel actualSuperheroModel = postResponse.as(SuperheroModel.class);
        expectedSuperheroModel.setId(actualSuperheroModel.getId());
        assertThat(actualSuperheroModel).isEqualTo(expectedSuperheroModel);
        //delete
        Response response = new SuperheroController().deleteHero(expectedSuperheroModel.getId());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test(groups = {"delete"}, description = "Delete created superhero and check status and get")
    public void deleteSuperheroWithGetCheckTest() {
        //post
        SuperheroModel expectedSuperheroModel = TestData.SUPERHERO_VALID_WITHOUT_PHONE;
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

    @Test(groups = {"delete", "negative"}, description = "Try to delete superhero with not existed id")
    public void deleteSuperheroWithNotExistedIdTest() {
        Response response = new SuperheroController().deleteHero(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @Test(groups = {"delete", "negative"}, description = "Try to delete superhero with invalid path")
    public void deleteSuperheroWithInvalidPathTest() {
        ErrorMessageModel expectedError = TestData.NOT_FOUND_ERROR;
        Response response = new SuperheroController().deleteHeroWithInvalidPath(TestData.NOT_EXISTED_ID);
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND);
        ErrorMessageModel actualError = response.as(ErrorMessageModel.class);
        assertThat(actualError).isEqualTo(expectedError);
    }

    /**
     * Return a model by id, if not found - throws an exception
     * @param model
     * @param id
     * @return
     */
    private SuperheroModel getModelById(SuperheroModel[] model, int id) {
        for (int i = 0; i < model.length; i++) {
            if (model[i].getId() == id) {
                return model[i];
            }
        }
        throw new RuntimeException("Not found object in SuperheroModel[] with an id = " + id);
    }
}
