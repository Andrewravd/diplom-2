package org.example;

import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private static final String MAKE_USER = "/api/auth/register";
    private static final String DELETE_USER = "/api/auth/user";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String ORDER = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";

    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(MAKE_USER)
                .then();
    }
    public ValidatableResponse patch(User user, String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(DELETE_USER)
                .then();
    }
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER)
                .then();
    }

    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER)
                .then();
    }
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get(INGREDIENTS)
                .then();
    }
}
