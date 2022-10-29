package com.example.myapplicationtutorial;

public class Chef extends User{

    private Boolean suspended;

    public Chef(String id, String username, String password,String fullname) {
        super(id,username,password,fullname);

        suspended = false; //starts as non suspended
    }

    public Boolean getSuspended(){
        return suspended;
    }

    public void setSuspended(Boolean x){
        suspended = x;
    }
}

