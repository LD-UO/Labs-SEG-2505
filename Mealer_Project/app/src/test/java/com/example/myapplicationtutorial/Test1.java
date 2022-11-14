package com.example.myapplicationtutorial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.Context;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.mockito.Mock;

public class Test1 {
    private static final String IS_VALID_MEAL = "Meal is valid";
    private static final String IS_NOT_VALID_MEAL = "Meal is not valid";

    @Test
    public void mealValidator_CorrectMealSample_ReturnsTrue(){
        //Meal Test = new Meal("1","2","3","4",false,"6","7","8","9","10")
        String name = "pizza";
        String type = "lunch";
        String cuisine = "italian";
        String allergens = "eggs";
        String chefUsername = "321";
        String description = "Yummy";
        String ingredients = "flour, sauce";
        String id = "209";
        String price = "100$";
        boolean onMenu = true;
        assertThat(name, isA(String.class));
        assertThat(type, isA(String.class));
        assertThat(cuisine, isA(String.class));
        assertThat(allergens, isA(String.class));
        assertThat(chefUsername, isA(String.class));
        assertThat(description, isA(String.class));
        assertThat(ingredients, isA(String.class));
        assertThat(id, isA(String.class));
        assertThat(price, isA(String.class));
        assertThat(onMenu, isA(boolean.class));
    }


}
