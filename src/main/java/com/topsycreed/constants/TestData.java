package com.topsycreed.constants;

import com.topsycreed.models.ErrorMessageModel;
import com.topsycreed.models.SuperheroModel;

import java.net.HttpURLConnection;

public class TestData {

    public static final int NOT_EXISTED_ID = -1;
    public static final int WITH_VALID_DATA1 = 4466;
    public static final SuperheroModel SUPERHERO_VALID_WITHOUT_PHONE = new SuperheroModel(263, "Anton Egorov", "1988-04-08", "Russia, Saint Petersburg", "QA", SuperheroModel.Gender.M.name(), null);
    public static final SuperheroModel SUPERHERO_VALID_WITH_PHONE = new SuperheroModel(265, "Anton Egorov", "1988-04-08", "Russia, Saint Petersburg", "QA", SuperheroModel.Gender.M.name(), "+79184565502");
    public static final SuperheroModel SUPERHERO_INVALID_DATE = new SuperheroModel(3474, "Anton Egorov", "invalid", "Russia, Saint Petersburg", "QA", SuperheroModel.Gender.M.name(), null);
    public static final SuperheroModel SUPERHERO_INVALID_DATE_UPDATE = new SuperheroModel(3977, "Anton Egorov", "invalid", "Russia, Saint Petersburg", "QA", SuperheroModel.Gender.M.name(), null);
    public static final SuperheroModel SUPERHERO_NOT_EXISTED_ID = new SuperheroModel(NOT_EXISTED_ID, "Anton Egorov", "1988-04-08", "Russia, Saint Petersburg", "QA", SuperheroModel.Gender.M.name(), null);
    public static final SuperheroModel SUPERHERO_INVALID_WITHOUT_REQUIRED_FIELD = new SuperheroModel(3882, null, null, null, null, null, null);
    public static final SuperheroModel SUPERHERO_INVALID_WITHOUT_ENUM_GENDER = new SuperheroModel(3886, "Anton Egorov", "1988-04-08", "Russia, Saint Petersburg", "QA", "invalid", null);
    public static final ErrorMessageModel NOT_FOUND_ERROR = new ErrorMessageModel(HttpURLConnection.HTTP_NOT_FOUND, "Not Found");
    public static final ErrorMessageModel BAD_REQUEST_ERROR = new ErrorMessageModel(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
}
