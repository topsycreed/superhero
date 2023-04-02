package com.topsycreed.extenstions;

import com.topsycreed.models.Client;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;

public class ClientExtension implements BeforeTestExecutionCallback, ParameterResolver {

    private Client client;

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        client = new Client();
        client.setName("tset");
        client.setSurname("gdgsg");
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Client.class)
                || parameterContext.getParameter().getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().getType().equals(Client.class)) {
            return client;
        } else if (parameterContext.getParameter().getType().equals(String.class)) {
            return "USD";
        } else {
            throw new ParameterResolutionException("Unsupported parameter type: " + parameterContext.getParameter().getType());
        }
    }
}
