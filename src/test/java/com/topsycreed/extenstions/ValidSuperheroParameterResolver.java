package com.topsycreed.extenstions;

import com.topsycreed.constants.TestData;
import com.topsycreed.models.SuperheroModel;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

public class ValidSuperheroParameterResolver implements ParameterResolver {

    //https://www.baeldung.com/junit-5-parameters
    public static SuperheroModel[] VALID_SUPERHEROES = {
            TestData.SUPERHERO_VALID_WITH_PHONE,
            TestData.SUPERHERO_VALID_WITHOUT_PHONE
    };

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == SuperheroModel.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Object obj = null;
        if (parameterContext.getParameter().getType() == SuperheroModel.class) {
            obj = VALID_SUPERHEROES[new Random().nextInt(VALID_SUPERHEROES.length)];
        }
        return obj;
    }
}
