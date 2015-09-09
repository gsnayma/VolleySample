package com.above.volleysample.volleysample;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Restaurant implements Parcelable{

    private String nameOfRestaurant;
    private String locationOfRestaurant;
    private String detailsUrl;

    Restaurant(String nameOfRestaurant,String locationOfRestaurant,String detailsUrl)
    {
        this.nameOfRestaurant = nameOfRestaurant;
        this.locationOfRestaurant = locationOfRestaurant;
        this.detailsUrl=detailsUrl;
    }

    protected Restaurant(Parcel in) {
        nameOfRestaurant = in.readString();
        locationOfRestaurant = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

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

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    @Override
    public String toString() {
        return String.format(nameOfRestaurant  +"\n"+ locationOfRestaurant);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nameOfRestaurant);
        dest.writeString(locationOfRestaurant);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    private Restaurant(Parcel in)
    {
        nameOfRestaurant= in.readString();
        locationOfRestaurant= in.readString();
    }
}
