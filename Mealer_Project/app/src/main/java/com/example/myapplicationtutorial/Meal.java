package com.example.myapplicationtutorial;

public class Meal {
    private String name;
    private String type;
    private String cuisine;

    // Probably won't need the ID if we already have the chef's username
    //private String id;
    private String chefUsername;
    private String allergens;
    private String price;
    private String description;
    private String ingredients;
    private boolean onMenu;
    private String id;


    public Meal(String name,String type,String cuisine, String allergens, boolean onMenu, String price, String chefUsername, String description, String ingredients, String id){
        this.name=name;
        this.type=type;
        this.cuisine=cuisine;
        this.allergens = allergens;
        this.chefUsername = chefUsername;
        this.price = price;
        this.description = description;
        this.ingredients = ingredients;
        this.onMenu = onMenu;
        this.id = id;
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

    public String getChefUsername() {
        return chefUsername;
    }

    public String getAllergens() {
        return allergens;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getId(){return  id;}

    public  Boolean getOnMenu(){return onMenu;}
}
