package com.example.myapplicationtutorial;

public class Order{

    private String chefUsername;
    private String clientUsername;
    private Meal meal;
    private String id;

    public Order(String clientUsername, Meal meal, String id){
        this.clientUsername = clientUsername;
        this.meal = meal;
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getChef(){
        return getMeal().getChefUsername();
    }

    public String getId() {
        return id;
    }
}
