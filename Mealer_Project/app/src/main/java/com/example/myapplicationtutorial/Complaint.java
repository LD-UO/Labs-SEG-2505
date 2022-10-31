package com.example.myapplicationtutorial;

public class Complaint {
    String description;
    String chefUsername;
    String clientUsername;
    String endDate;
    boolean addressed;

    public Complaint(){

    }
    public Complaint(String description, String chefUsername, String clientUsername,
                     String endDate){
        this.description = description;
        this.chefUsername = chefUsername;
        this.clientUsername = clientUsername;
        this.endDate = endDate;
        addressed = false;
    }
    public String getChefUsername(){
        return chefUsername;
    }
    public boolean getAddressed(){
        return addressed;
    }
    public String getEndDate(){
        return endDate;
    }

    public String getClientUsername(){
        return clientUsername;
    }

    public String getDescription(){
        return description;
    }

}
