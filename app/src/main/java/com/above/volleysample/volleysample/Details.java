package com.above.volleysample.volleysample;


import java.io.Serializable;

public class Details implements Serializable {

    private String name;
    private String address;
    private String location;
    private String phone_number;
    private String website;

   public Details(String name,String address,String location,String phone_number,String website)
    {
        this.name = name;
        this.address = address;
        this.location = location;
        this.phone_number = phone_number;
        this.website = website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return String.format(name);
    }
}



