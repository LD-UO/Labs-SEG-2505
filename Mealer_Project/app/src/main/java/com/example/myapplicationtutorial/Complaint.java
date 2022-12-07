package com.example.myapplicationtutorial;

public class Complaint {
    private String description;
   private String chefUsername;
    private String endDate;
    private String id;
    boolean addressed;

    public Complaint(String description, String chefUsername,
                     String endDate, String id){
        this.description = description;
        this.chefUsername = chefUsername;
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
    public String getDescription(){
        return description;
    }
    public void approve(){
        addressed=true;
    }
}
