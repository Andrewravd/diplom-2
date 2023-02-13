package org.example;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPatchUser {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.getUser();
        userClient = new UserClient();
    }

    @After
    @Description("Удаление пользователя")
    public void deleteUser() {
        ValidatableResponse response = userClient.delete(accessToken);
        Assert.assertEquals(202, response.extract().statusCode());
    }

    @Test
    @Description("Изменение пользователя с авторизацией")
    public void patchUser() {
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        Assert.assertEquals(200, login.extract().statusCode());
        user = UserGenerator.getUser();
        ValidatableResponse patch = userClient.patch(user, accessToken);
        Assert.assertNotEquals((patch.extract().path("user.email")),
                response.extract().path("user.email"));
        Assert.assertEquals(200, patch.extract().statusCode());

    }

    @Test
    @Description("Изменение  пользователя без авторизации")
    public void pathUserWithoutAuthorization() {
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        accessToken = "";
        ValidatableResponse patch = userClient.patch(user, accessToken);
        assertEquals("You should be authorised",
                patch.extract().path("message"));
        Assert.assertEquals(401, patch.extract().statusCode());
        accessToken = response.extract().path("accessToken");
    }
}