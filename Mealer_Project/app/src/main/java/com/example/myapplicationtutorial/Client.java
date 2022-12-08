package com.example.myapplicationtutorial;

public class Client extends User{
    private String address;
    private String creditInfo;
    public Client(String id, String username, String password, String fullname){
        super(id,username,password,fullname);
    }

    public String getCreditInfo() {
        return creditInfo;
    }

    public void setCreditInfo(String creditInfo) {
        this.creditInfo = creditInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
