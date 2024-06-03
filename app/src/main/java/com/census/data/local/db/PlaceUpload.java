package com.census.data.local.db;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaceUpload implements Parcelable {

    public static final Creator<PlaceUpload> CREATOR = new Creator<PlaceUpload>() {
        @Override
        public PlaceUpload createFromParcel(Parcel in) {
            return new PlaceUpload(in);
        }

        @Override
        public PlaceUpload[] newArray(int size) {
            return new PlaceUpload[size];
        }
    };
    private String placeId;
    private String name;
    private String address;
    private String city;
    private String country;
    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PlaceUpload() {
    }

    protected PlaceUpload(Parcel in) {
        placeId = in.readString();
        name = in.readString();
        address = in.readString();
        country = in.readString();
        city = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(placeId);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(lat);
        dest.writeString(lng);
    }
}