package com.example.myapplicationtutorial;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
public class Testing_Deliverable_4 {

    @Test
    public void OrderTestCorrect(){
        // Checks to make sure that the inputted information will indeed create a valid order
        Meal meal = new Meal("pizza","Main","italian", "eggs", true, "100$", "321",
                "Yummy", "flour, sauce", "321");
        Order order = new Order("123", meal, "lebronjames");
        assertThat(meal.getOnMenu(), instanceOf(boolean.class));
        assertThat(order.getClientUsername(), instanceOf(String.class));
        assertThat(meal, instanceOf(Meal.class));
        assertThat(order.getId(), instanceOf(String.class));
    }
    @Test
    public void OrderTestIncorrect(){
        // Checks to make sure that the inputted information will NOT create a valid order (because the meal parameter is null
        Meal meal = new Meal("pizza","Main","italian", "eggs", false, "100$", "321",
                "Yummy", "flour, sauce", "321");
        Order order = new Order("123", null, "goblinmode");
        assertThat(meal.getOnMenu(), instanceOf(boolean.class));
        assertThat(order.getClientUsername(), instanceOf(String.class));
        assertThat(order.getMeal(), is(nullValue()));
        assertThat(order.getId(), instanceOf(String.class));
    }
    @Test
    public void OrderTestCorrectStatus(){
        // Checks to make sure that status of the order is initialized to "pending" and the meal is on the menu
        Meal meal = new Meal("pizza","Main","italian", "eggs", true, "100$", "321",
                "Yummy", "flour, sauce", "321");
        Order order = new Order("123", meal, "asdf");
        assertTrue("pending".equals(order.getStatus()));
        assertTrue(meal.getOnMenu() == true);
        assertThat(order.getClientUsername(), instanceOf(String.class));
        assertThat(meal, instanceOf(Meal.class));
        assertThat(order.getId(), instanceOf(String.class));
    }
    @Test
    public void OrderTestCorrectStatusWhenChanged(){
        // Checks to make sure that status of the order is initialized to "pending", then verifies that the status was correctly set
        Meal meal = new Meal("pizza","Main","italian", "eggs", true, "100$", "321",
                "Yummy", "flour, sauce", "321");
        Order order = new Order("123", meal, "win");
        assertTrue("pending".equals(order.getStatus()));
        order.setStatus("approved");
        assertFalse("pending".equals(order.getStatus()));
        assertTrue("approved".equals(order.getStatus()));
        assertThat(meal.getOnMenu(), instanceOf(boolean.class));
        assertThat(order.getClientUsername(), instanceOf(String.class));
        assertThat(meal, instanceOf(Meal.class));
        assertThat(order.getId(), instanceOf(String.class));
    }

}
