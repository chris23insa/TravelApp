package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;

import com.example.chris.travelorga_kth.helper.Coord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 13/11/2019.
 */

//Need to implement Serializable to be shared between activities
public class Trip implements Serializable {

    private final String tripName;
    private final String tripId;
    private final int tripImageId;
    private final String tripDateFrom;
    private final String tripDateTo;
    private final String tripDescription;
    private final ArrayList<TripActivity> listActivity;
    private final ArrayList<Participants> listParticipants;
    private Coord coord;
    private final int budget;
    private final Preference preference;

    public Trip(String tripName, int tripImageId, String tripDateFrom,
                String tripDateTo, String tripDescription, ArrayList<TripActivity> _listActivity,
                ArrayList<Participants> _listParticipants,int budget, Preference pref, Activity androidActivity ) {
        Geocoder geocoder = new Geocoder(androidActivity);

        this.tripName = tripName;
        tripId = tripName;
        this.tripImageId = tripImageId;
        this.tripDateFrom = tripDateFrom;
        this.budget = budget;
        this.preference = pref;
        this.tripDateTo = tripDateTo;
        this.tripDescription = tripDescription;
        this.listActivity =_listActivity;
        this.listParticipants = _listParticipants;
        if (listParticipants != null) {
            for(Participants p : listParticipants){
                p.addTrip(this);
            }
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
    public int getTripImageId() {
        return tripImageId;
    }
    public String getTripDateFrom () { return tripDateFrom; }
    public String getTripDateTo () { return tripDateTo; }
    public int getBudget(){return  budget;}
    public String getTripDescription () { return tripDescription; }
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if( obj instanceof  Trip){
            return (((Trip) obj).tripId).equals(tripId);
        }
        return false;
    }

}