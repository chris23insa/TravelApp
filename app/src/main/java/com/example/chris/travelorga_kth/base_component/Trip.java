package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import com.example.chris.travelorga_kth.helper.Coord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 13/11/2019.
 */

//Need to implement Serializable to be shared between activities
public class Trip implements Serializable {

    // Save trip name.
    private String tripName;

    // Save trip image resource id.
    private int tripImageId;

    // Save trip date from.
    private String tripDateFrom;

    // Save trip date to.
    private String tripDateTo;

    // Save trip description.
    private String tripDescription;

    private final ArrayList<TripActivity> listActivity;
    private final ArrayList<Participants> listParticipants;
    private Coord coord;
    private int budget;
    private  Preference preference;


    public Trip(String tripName, int tripImageId, String tripDateFrom,
                String tripDateTo, String tripDescription, ArrayList<TripActivity> _listActivity,
                ArrayList<Participants> _listParticipants,int budget, Preference pref, Activity androidActivity ) {
        Geocoder geocoder = new Geocoder(androidActivity);
        this.tripName = tripName;
        this.tripImageId = tripImageId;
        this.tripDateFrom = tripDateFrom;
        this.budget = budget;
        this.preference = pref;
        this.tripDateTo = tripDateTo;
        this.tripDescription = tripDescription;
        this.listActivity =_listActivity;
        this.listParticipants = _listParticipants;
        for(Participants p : listParticipants){
            p.addTrip(this);
        }
        try {
            List<Address> addresses = geocoder.getFromLocationName(this.tripName, 1);
            if (addresses.size() > 0) {
                this.coord = new Coord(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getTripImageId() {
        return tripImageId;
    }

    public void setTripImageId(int tripImageId) {
        this.tripImageId = tripImageId;
    }

    public String getTripDateFrom () { return tripDateFrom; }

    public void setTripDateFrom(String tripDateFrom) {
        this.tripDateFrom = tripDateFrom;
    }

    public String getTripDateTo () { return tripDateTo; }
    public int getBudget(){return  budget;}
    public void setTripDateTo(String tripDateTo) {
        this.tripDateTo = tripDateTo;
    }

    public String getTripDescription () { return tripDescription; }

    public void setTripDescription (String tripDescription) { this.tripDescription = tripDescription; }

    public Coord getCoord(){return this.coord;}

    public ArrayList<Participants> getListParticipants() {
        return listParticipants;
    }

    public ArrayList<TripActivity> getListActivity() {
        return listActivity;
    }
    public Preference getPreference(){return preference;}
    public void addActivity(TripActivity activity){
        listActivity.add(activity);
    }
    public void removeActivity(TripActivity activity){
        listActivity.remove(activity);
    }
    public void addParticipant(Participants participant){
        listParticipants.add(participant);
    }
    public void removeParticipant(Participants participants){
        listParticipants.remove(participants);
    }
}
