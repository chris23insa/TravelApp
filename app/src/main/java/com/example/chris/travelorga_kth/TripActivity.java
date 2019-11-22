package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TripActivity implements Serializable {
    public String place;
    public String name;
    public String image;
    public String address;
    public int imageID;
    public String from;
    public String to;
    public String description;
    public String longDescription;
    public Coord coord;
    public ArrayList<String> bulletPoint;
    public ArrayList<String> openingHour;
    public ArrayList<String> price;
    public transient Activity context;

    public String getName() {
        return name;
    }
    public String getPlace(){return place;}
    public String getAddress(){return address;}


    public String getDateFrom() {
        return from;
    }

    public String getDateTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageID;
    }


    public TripActivity(String _place, String _name, String _address, String _image, String _from, String _to, String _description,
                        String _longDescription, ArrayList<String> _bulletPoint,
                        ArrayList<String> _openingHour, ArrayList<String> _price, Activity androidActivity) {
        Geocoder geocoder = new Geocoder(androidActivity);
        place = _place;
        image =_image;
        address = _address;
        context = androidActivity;
        imageID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        from = _from;
        to = _to;
        description =_description;
        name = _name;
        longDescription = _longDescription;
        bulletPoint = _bulletPoint;
        openingHour = _openingHour;
        price = _price;


        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                coord = new Coord(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            } else {
                coord = new Coord(0, 0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
