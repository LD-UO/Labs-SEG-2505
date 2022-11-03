package com.example.myapplicationtutorial;

public class Complaint {
    private String description;
   private String chefUsername;
    private String clientUsername;
    private String endDate;
    private String id;
    boolean addressed;

    public Complaint(){

    }
    public Complaint(String description, String chefUsername, String clientUsername,
                     String endDate, String id){
        this.description = description;
        this.chefUsername = chefUsername;
        this.clientUsername = clientUsername;
        this.endDate = endDate;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public String getClientUsername(){
        return clientUsername;
    }

    public String getDescription(){
        return description;
    }

}
