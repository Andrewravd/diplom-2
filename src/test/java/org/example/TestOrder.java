package org.example;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;


public class TestOrder {
    private User user;
    private UserClient userClient;
    private String accessToken;

    private Order order;
    private List<String> ingredients;

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
    @Description("Создание заказа авторизованным пользователем с ингридиентами")
    public void makeOrder(){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(200, login.extract().statusCode());
        ValidatableResponse getIngredients = userClient.getIngredients();
        List<String> ingredients = getIngredients.extract().path("data._id");
        Assert.assertNotEquals("", ingredients.toString());
        order = new Order(ingredients);
        ValidatableResponse createOrder = userClient.createOrder(order);
        Assert.assertTrue(createOrder.extract().path("success"));
        Assert.assertThat(createOrder.extract().path("order.number"),notNullValue());
        Assert.assertEquals(200, createOrder.extract().statusCode());
    }

    @Test
    @Description("Создание заказа неавторизованным пользователем с ингридиентами")
    public void makeOrderWithoutLogin(){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        accessToken = response.extract().path("accessToken");
        ValidatableResponse getIngredients = userClient.getIngredients();
        ingredients = getIngredients.extract().path("data._id");
        Assert.assertNotEquals("", ingredients.toString());
        order = new Order(ingredients);
        ValidatableResponse createOrder = userClient.createOrder(order);
        Assert.assertTrue(createOrder.extract().path("success"));
        Assert.assertThat(createOrder.extract().path("order.number"),notNullValue());
        Assert.assertEquals(200, createOrder.extract().statusCode());
    }
    @Test
    @Description("Создание заказа без ингредиентов")
    public void makeOrderWithoutIngredients(){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(200, login.extract().statusCode());
        order = new Order(ingredients);
        ValidatableResponse createOrder = userClient.createOrder(order);
        assertEquals("Ingredient ids must be provided", createOrder.extract().path("message"));
        Assert.assertEquals(400, createOrder.extract().statusCode());
    }
    @Test
    @Description("Создание заказа авторизованным пользователем с невалидным хешем ингредиентов")
    public void makeOrderWithIncorrectData(){
        ValidatableResponse response = userClient.create(user);
        Assert.assertEquals(200, response.extract().statusCode());
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = response.extract().path("accessToken");
        Assert.assertEquals(200, login.extract().statusCode());
        ingredients = new ArrayList<>();
        ingredients.add(DataGenerator.getRandomString());
        ingredients.add(DataGenerator.getRandomString());
        Assert.assertNotEquals("", ingredients.toString());
        order = new Order(ingredients);
        ValidatableResponse createOrder = userClient.createOrder(order);
        Assert.assertEquals(500, createOrder.extract().statusCode());
    }
}
