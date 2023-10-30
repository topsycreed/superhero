Automated tests to cover superhero REST service https://superhero.qa-test.csssr.com/swagger-ui.html#/ using REST Assured library.

# How to run tests:
## Clone a repository
Save project directly from the GitHub or use $ git clone
## Prerequisites
1. Allow a lombok annotation processor
2. Setup Java 11
3. Install Allure

## Executing tests
1. Execute maven command: 
```
mvn test
```
2. Generate allure report:
```
allure serve
```

Note: Some of the tests will fail since the service contains issues intentionally.


