package org.example;

import java.util.List;

public class Order {

    private List<String> ingredients;

    public Order() {

    }

    public Order(List ingredients) {
        this.ingredients = ingredients;
    }

    public List getIngredients() {
        return ingredients;
    }

    public void setIngredients(List ingredients) {
        this.ingredients = ingredients;
    }
}