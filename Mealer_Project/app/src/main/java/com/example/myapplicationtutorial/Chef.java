package com.example.myapplicationtutorial;

import java.lang.Math;

public class Chef extends User{

    private Boolean suspended;
    //Ratings are out of 5
    private int totalRating;
    private int numberOfRatings;

    public Chef(String id, String username, String password,String fullname) {
        super(id,username,password,fullname);
        this.totalRating=0;
        this.numberOfRatings=0;
        suspended = false; //starts as non suspended
    }

    public Boolean getSuspended(){
        return suspended;
    }

    public void setSuspended(Boolean x){
        suspended = x;
    }

    public Float rateChef(int clientsRating){
        if(clientsRating<1||clientsRating>5){
            throw new IllegalArgumentException();
        }
        totalRating+=clientsRating;
        numberOfRatings++;
        return getRating();
    }

    public Float getRating() {
        return (float)totalRating/(float)numberOfRatings;
    }
}

