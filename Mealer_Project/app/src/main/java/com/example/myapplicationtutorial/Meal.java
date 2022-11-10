package com.example.myapplicationtutorial;

public class Meal {
    private String name;
    private String type;
    private String cuisine;

    public Meal(String name,String type,String cuisine){
        this.name=name;
        this.type=type;
        this.cuisine=cuisine;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCuisine() {
        return cuisine;
    }
}
