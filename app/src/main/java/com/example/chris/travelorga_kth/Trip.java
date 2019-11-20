package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    String place;
    String image;
    String to;
    String from;
    String description;
    ArrayList<TripActivity> listActivity;
    LatLng coord;


public Trip(String _place, String _image, String _from, String _to, String _description, ArrayList<TripActivity> _listActivity, Activity androidActivity){
    Geocoder geocoder = new Geocoder(androidActivity);
     place = _place;
     image =_image;
     to =_to;
     from =_from;
     description =_description;
     listActivity =_listActivity;

    try {
        List<Address> addresses = geocoder.getFromLocationName(place, 1);
        LatLng coord;
        if (addresses.size() > 0) {
            coord = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        }
    }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addActivity(TripActivity activity){
        listActivity.add(activity);
    }

}
