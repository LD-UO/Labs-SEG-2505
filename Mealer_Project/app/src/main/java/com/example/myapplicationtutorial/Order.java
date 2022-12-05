package com.example.myapplicationtutorial;

public class Order{

    private String chefUsername;
    private String clientUsername;
    private Meal meal;
    private String id;
    private String status;

    public Order(String clientUsername, Meal meal, String id){
        this.clientUsername = clientUsername;
        this.meal = meal;
        this.id = id;
        this.status = "pending";
    }

    public Meal getMeal() {
        return meal;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getChefUsername(){
        return getMeal().getChefUsername();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if("approved".equals(status)||"cancelled".equals(status)){
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}

