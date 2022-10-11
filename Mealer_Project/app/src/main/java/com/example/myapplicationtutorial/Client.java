package com.example.myapplicationtutorial;

public class Client {
    private String id;
    private String username;

    public Client(String id, String username){
        this.id = id;
        this.username = username;
    }

    public String getId(){
        return this.id;
    }

    public  String getUsername(){
        return this.username;
    }
}
