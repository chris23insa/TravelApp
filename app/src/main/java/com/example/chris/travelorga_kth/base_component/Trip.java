package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chris.travelorga_kth.helper.Coord;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
    private final String tripDateFrom;
    private final Date dateFrom;
    private final Date dateTo;
    private final String tripDateTo;
    private final String tripDescription;
    private final int budget;
    private final Preference preference;
    private final String imageURL;
    private int imageBackup;
    private final long owner;
    private final long id;
    private final String place;
    private  Coord coord;

    //todo get image to setImageinView

    public String getImageURL() {
        return imageURL;
    }

    public long getId() {
        return id;
    }

    public long getTripId() {
        return id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public long getOwner() {
        return owner;
    }

    public String getPlace() {
        return place;
    }


    public Trip(long _id, String tripName, String place, String tripImage, Date _tripDateFrom,
                Date _tripDateTo, String tripDescription, int budget, Preference pref, double lat, double lng, long _owner,
                Context androidActivity) {

        Geocoder geocoder = new Geocoder(androidActivity);
        this.tripName = tripName;
        imageURL = "https://travelapp-backend.osc-fr1.scalingo.io/trips/" + tripImage;
        Log.d("IMAGE", imageURL);

        dateFrom = _tripDateFrom;
        dateTo = _tripDateTo;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        tripDateFrom = formatter.format(dateFrom);
        tripDateTo = formatter.format(dateTo);
        this.budget = budget;
        this.preference = pref;
        this.tripDescription = tripDescription;

        owner = _owner;
        id = _id;
        this.place = place;
        this.coord = new Coord(lat,lng);
        try {
            List<Address> addresses = geocoder.getFromLocationName(this.tripName, 1);
            if (addresses.size() > 0) {
                this.coord = new Coord(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripDateFrom() {
        return tripDateFrom;
    }

    public String getTripDateTo() {
        return tripDateTo;
    }

    public int getBudget() {
        return budget;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public Coord getCoord() {
        return this.coord;
    }

    public void getListActivity(Callable.CallableArgActitivy op,Context c) {
        ArrayList<TripActivity> listAct = new ArrayList<>();
        Scalingo.getInstance().getActivityDao().retrieveTripActivities(id,
                list -> {
                    listAct.addAll(list.stream().map( u-> u.toActivity(c)).collect(Collectors.toList()));
                    op.operationCallableArgActitivyArrayList(listAct);
                });

    }

    public TripModel toModel() {
        return new TripModel(owner, tripName, place, imageURL, getTripDescription(), budget, preference, coord.getLatLng().latitude, coord.getLatLng().longitude, dateFrom, dateTo);
    }

    public Preference getPreference() {
        return preference;
    }

    public void addActivity(long activityId, String to, String from) throws ScalingoError {
        Scalingo.getInstance().getActivityDao().createTripActivity(id, activityId, to.toString(),
                from.toString(), null, null);
    }

    public void addParticipant(Participants participant) {
        Scalingo.getInstance().getUserDao().createTripParticipant(id, participant.getId(), null, null);
        //listParticipants.add(participant);
    }

    public void getParticipant(Callable.CallableArgParticipant op) {
        ArrayList<Participants> tmp = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id,
                list -> {
                    for (UserModel um : list) {
                        tmp.add(um.toUser());
                    }
                    op.operationArgParticipant(tmp);
                }, null
        );
    }

    public int getImageBackup() {
        return imageBackup;
    }

    public void getListParticipantsWithoutOwner(Callable.CallableArgParticipant op) {
        ArrayList<Participants> participants = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id, list -> {
            for (UserModel el : list) {
                participants.add(el.toUser());
            }
            op.operationArgParticipant(participants);
        });
    }

    public void getListParticipants(Callable.CallableArgParticipant op) {
        ArrayList<Participants> participants = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieve(owner, u -> participants.add(u.toUser()), null);
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id, list -> {
            for (UserModel el : list) {
                participants.add(el.toUser());
            }
            op.operationArgParticipant(participants);
        });
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Trip) {
            return (((Trip) obj)).id == id;
        }
        return false;
    }

}