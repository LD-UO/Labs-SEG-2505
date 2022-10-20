package com.example.myapplicationtutorial;

public class User {
    protected String username;
    protected String id;
    protected String password;
    protected String fullname;

    public User(String id, String username, String password, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public String getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){return password;}
    public String getFullname(){return fullname;}

}
