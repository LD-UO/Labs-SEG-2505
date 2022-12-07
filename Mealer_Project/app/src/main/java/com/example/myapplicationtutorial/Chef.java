package com.example.myapplicationtutorial;

import java.lang.Math;

public class Chef extends User{

    private Boolean suspended;
    //Ratings are out of 5
    private String  totalRating;
    private String  numberOfRatings;

    public Chef(String id, String username, String password,String fullname) {
        super(id,username,password,fullname);
        this.totalRating="0";
        this.numberOfRatings="0";
        suspended = false; //starts as non suspended
    }

    public Boolean getSuspended(){
        return suspended;
    }

    public void setSuspended(Boolean x){
        suspended = x;
    }

    public String getNumberOfRatings() {
        return numberOfRatings;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setNumberOfRatings(String numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }
}

