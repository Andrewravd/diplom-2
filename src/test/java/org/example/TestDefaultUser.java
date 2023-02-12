package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;


public class TestDefaultUser {
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
    public void deleteUser(){
        ValidatableResponse response = userClient.delete(accessToken);
        Assert.assertEquals(202, response.extract().statusCode());

    }
    @Test
    @Description("Создание валидного юзера и авторизация")
    public void makeUserAndLogin (){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(200, login.extract().statusCode());

    }
    @Test
    @Description("Создание пользователя, который уже зарегистрирован")
    public void makeUserWithSameCred(){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        Assert.assertEquals(200, login.extract().statusCode());
        accessToken = response.extract().path("accessToken");
        ValidatableResponse createUserWithSameCred = userClient.create(user);
        Assert.assertEquals(403, createUserWithSameCred.extract().statusCode());


    }
}
