package com.above.volleysample.volleysample;

import android.os.Parcelable;

import java.io.Serializable;

public class Restaurant implements Serializable{

    public String nameOfRestaurant;
    public String locationOfRestaurant;

    Restaurant(String nameOfRestaurant,String locationOfRestaurant)
    {
        this.nameOfRestaurant = nameOfRestaurant;
        this.locationOfRestaurant = locationOfRestaurant;
    }

    public void setLocationOfRestaurant(String locationOfRestaurant) {
        this.locationOfRestaurant = locationOfRestaurant;
    }

    public void setNameOfRestaurant(String nameOfRestaurant) {
        this.nameOfRestaurant = nameOfRestaurant;
    }

    public String getLocationOfRestaurant() {
        return locationOfRestaurant;
    }

    public String getNameOfRestaurant() {
        return nameOfRestaurant;
    }

    @Override
    public String toString() {
        return String.format(nameOfRestaurant  +"\n"+ locationOfRestaurant);
    }
}
