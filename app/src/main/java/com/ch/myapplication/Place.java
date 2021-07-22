package com.ch.myapplication;

public class Place implements Comparable<Place>{
    String name;
    int imageID;
    int noOfPhotos;
    String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getHasPhone() {
        return hasPhone;
    }

    public void setHasPhone(char hasPhone) {
        this.hasPhone = hasPhone;
    }

    char hasPhone;
    double lat, longt;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    float distance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getNoOfPhotos() {
        return noOfPhotos;
    }

    public void setNoOfPhotos(int noOfPhotos) {
        this.noOfPhotos = noOfPhotos;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public Place(String name, int imageID, int noOfPhotos, double lat, double longt, float distance)
    {
        this.name = name;
        this.imageID = imageID;
        this.noOfPhotos = noOfPhotos;
        this.lat = lat;
        this.longt = longt;
        this.distance = distance;
    }
    public Place(String name, int imageID, int noOfPhotos, double lat, double longt)
    {
        this.name = name;
        this.imageID = imageID;
        this.noOfPhotos = noOfPhotos;
        this.lat = lat;
        this.longt = longt;
    }

    public Place(String name, int imageID, int noOfPhotos, double lat, double longt, float distance, char hasPhone, String phoneNumber)
    {
        this.name = name;
        this.imageID = imageID;
        this.noOfPhotos = noOfPhotos;
        this.lat = lat;
        this.longt = longt;
        this.distance = distance;
        this.hasPhone = hasPhone;
        this.phoneNumber = phoneNumber;
    }

    public Place(String name, int imageID, int noOfPhotos, double lat, double longt, char hasPhone, String phoneNumber)
    {
        this.name = name;
        this.imageID = imageID;
        this.noOfPhotos = noOfPhotos;
        this.lat = lat;
        this.longt = longt;
        this.hasPhone = hasPhone;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(Place o) {
        return 0;
    }
}
