package com.example.myapplicationtutorial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

public class Testing_Deliverable_3 {

    @Test
    //Tests if the inputted variables are the appropriate values when creating a meal.
    public void mealValidator_CorrectMealSample(){
        Meal meal = new Meal("pizza","lunch","italian", "eggs", true, "100$", "321",
                "Yummy", "flour, sauce", "321");
        boolean onMenu = true;
        assertThat(meal.getName(), instanceOf(String.class));
        assertThat(meal.getType(), instanceOf(String.class));
        assertThat(meal.getCuisine(), instanceOf(String.class));
        assertThat(meal.getAllergens(), instanceOf(String.class));
        assertThat(meal.getChefUsername(), instanceOf(String.class));
        assertThat(meal.getOnMenu(), instanceOf(boolean.class));
        assertThat(meal.getPrice(), instanceOf(String.class));
        assertThat(meal.getId(), instanceOf(String.class));
        assertThat(meal.getDescription(), instanceOf(String.class));
        assertThat(meal.getIngredients(), instanceOf(String.class));
    }
    @Test
    //Tests if the inputted variables are the inappropriate values when creating a meal.
    public void mealValidator_InCorrectMealSample(){
        Meal meals = new Meal(null,"lunch","italian", null, false, "300$", "321",
                "Yummy", "flour, sauce", "321");
        boolean onMenu = true;
        assertThat(meals.getName(), is(nullValue()));
        assertThat(meals.getType(), instanceOf(String.class));
        assertThat(meals.getCuisine(), instanceOf(String.class));
        assertThat(meals.getAllergens(), is(nullValue()));
        assertThat(meals.getChefUsername(), instanceOf(String.class));
        assertThat(meals.getOnMenu(), instanceOf(boolean.class));
        assertThat(meals.getPrice(), instanceOf(String.class));
        assertThat(meals.getId(), instanceOf(String.class));
        assertThat(meals.getDescription(), instanceOf(String.class));
        assertThat(meals.getIngredients(), instanceOf(String.class));
    }
    @Test
    //Tests if the inputted variables are the appropriate values when creating a new Chef.
    public void validNewChef(){
        Chef Paul = new Chef("24", "Paul123", "Paul321", "Paul Jetski");
        assertThat(Paul.getId(), instanceOf(String.class));
        assertThat(Paul.getUsername(), instanceOf(String.class));
        assertThat(Paul.getPassword(), instanceOf(String.class));
        assertThat(Paul.getFullname(), instanceOf(String.class));
    }
    @Test
    //Tests if the inputted variables are the inappropriate values when creating a new Chef.
    public void invalidNewChef(){
        Chef Max = new Chef("25", "Max456", null, "Max Seadoo");
        assertThat(Max.getId(), instanceOf(String.class));
        assertThat(Max.getUsername(), instanceOf(String.class));
        assertThat(Max.getPassword(), is(nullValue()));
        assertThat(Max.getFullname(), instanceOf(String.class));
    }


}
