package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;

import com.example.chris.travelorga_kth.helper.Coord;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.TripModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chris on 13/11/2019.
 */

//Need to implement Serializable to be shared between activities
public class Trip implements Serializable {

    private final String tripName;
    private final String tripId;
    private  int tripImageId;
    private final String tripDateFrom;
    private Date dateFrom;
    private Date dateTo;
    private final String tripDateTo;
    private final String tripDescription;
    private  ArrayList<TripActivity> listActivity;
    private  List<Participants> listParticipants;
    private Coord coord;
    private final int budget;
    private final Preference preference;
    private String imageURL;
    long owner;
    long id;
    TripModel model;

    /*public Trip(String tripName, int tripImageId, String tripDateFrom,
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
    */



    public Trip(long _id,String tripName, String tripImage, Date _tripDateFrom,
                Date _tripDateTo, String tripDescription,int budget, Preference pref, double lat, double lng,long _owner,TripModel mod) {

        this.tripName = tripName;
        tripId = tripName;
        this.imageURL = tripImage;
        dateFrom = _tripDateFrom;
        dateTo = _tripDateTo;
        tripDateFrom = dateFrom.toString();
        tripDateTo = dateTo.toString();
        this.budget = budget;
        this.preference = pref;
        this.tripDescription = tripDescription;
        owner = _owner;
        coord = new Coord(lat,lng);
        id = _id;
        model = mod;
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

    public List<Participants> getListParticipants() {
        //TODO need list participant
        return listParticipants;
    }

    public ArrayList<TripActivity> getListActivity() {
        ArrayList<TripActivity> listAct = new ArrayList();
        Scalingo.getInstance().getActivityDao().retrieveTripActivities(id, list ->{
            listAct.addAll(list.stream().map(i -> i.toActivity()).collect(Collectors.toList()));
        });
        return listAct;
    }

    public Preference getPreference(){return preference;}
    public void addActivity(TripActivity activity){
        listActivity.add(activity);
    }
    public void removeActivity(TripActivity activity){
        //TODO remove activity from trip
        //listActivity.remove(activity);
    }
    public void addParticipant(Participants participant){
        //TODO Remove user from trip
        //listParticipants.add(participant);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if( obj instanceof  Trip){
            return (((Trip) obj).tripId).equals(tripId);
        }
        return false;
    }
}