package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class TripActivity {
    String place;
    String image;
    String time;
    String description;
    LatLng coord;


    public TripActivity(String _place, String _image, String _time, String _description, Activity androidActivity){
        Geocoder geocoder = new Geocoder(androidActivity);
        place = _place;
        image =_image;
        time = _time;
        description =_description;

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
}
