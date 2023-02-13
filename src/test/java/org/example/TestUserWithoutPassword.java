package org.example;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUserWithoutPassword {
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        user = UserGenerator.getUserWithoutPassword();
        userClient = new UserClient();
    }

    @Test
    @Description("Создание и авторизация пользователя с незаполненным password")
    public void makeUserWithoutEmail() {
        ValidatableResponse response = userClient.create(user);
        assertEquals("Email, password and name are required fields",
                response.extract().path("message"));
        assertEquals(403, response.extract().statusCode());
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        assertEquals("email or password are incorrect", loginResponse.extract().path("message"));
        assertEquals(401, loginResponse.extract().statusCode());
    }

}
