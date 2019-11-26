package com.example.chris.travelorga_kth.helper;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Coord implements Serializable {

    private double lat;
    private double lng;

    public Coord(double _lat, double _lng){
        lat =_lat;
        lng=_lng;

    }

    public LatLng getLatLng(){
        return new LatLng(lat,lng);
    }
}
