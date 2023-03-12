package com.topsycreed.extenstions;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RestAssuredExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.baseURI = "https://superhero.qa-test.csssr.com/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
