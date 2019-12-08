package com.example.chris.travelorga_kth.base_component;

import android.support.annotation.Nullable;

import com.example.chris.travelorga_kth.helper.Coord;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private final Coord coord;

    //todo get image to setImageinView

    public String getImageURL(){
        return  imageURL;
    }

    public long getId(){return  id;}

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
    public String getPlace(){return  place;}


    public Trip(long _id, String tripName,String place, String tripImage, Date _tripDateFrom,
                Date _tripDateTo, String tripDescription, int budget, Preference pref, double lat, double lng, long _owner) {

        this.tripName = tripName;
            imageURL = tripImage;

        dateFrom = _tripDateFrom;
        dateTo = _tripDateTo;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        tripDateFrom = formatter.format(dateFrom);
        tripDateTo = formatter.format(dateTo);
        this.budget = budget;
        this.preference = pref;
        this.tripDescription = tripDescription;

        owner = _owner;
        coord = new Coord(lat,lng);
        id = _id;
        this.place = place;
    }

    public String getTripName() {
        return tripName;
    }
    public String getTripDateFrom () { return tripDateFrom; }
    public String getTripDateTo () { return tripDateTo; }
    public int getBudget(){return  budget;}
    public String getTripDescription () { return tripDescription; }
    public Coord getCoord(){return this.coord;}

    public void getListActivity(Callable.CallableArgActitivy op) {
        ArrayList<TripActivity> listAct = new ArrayList();
       //TODO/
        /*  Scalingo.getInstance().getActivityDao().retrieveTripActivities(id,
                list ->{
            listAct.addAll(list.stream().map(ActivityModel::toActivity).collect(Collectors.toList()));
            op.operationCallableArgActitivyArrayList(listAct);
                });*/

    }

      public TripModel toModel(){
        return new TripModel(owner,tripName,place,imageURL,getTripDescription(),budget,preference,coord.getLatLng().latitude,coord.getLatLng().longitude,dateFrom,dateTo);
    }

    public Preference getPreference(){return preference;}

    public void addActivity(TripActivity activity) throws ScalingoError {
        Scalingo.getInstance().getActivityDao().create(activity.toModel(),null,null);
    }

    public void addParticipant(Participants participant){
        Scalingo.getInstance().getUserDao().createTripParticipant(id,participant.getId(),null,null);
        //listParticipants.add(participant);
    }

    public void getParticipant(Callable.CallableArgParticipant op ){
        ArrayList<Participants> tmp = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id,
                list ->{
                    for(UserModel um : list){
                        tmp.add(um.toUser());
                    }
                    op.operationArgParticipant(tmp);
                },null
                );
    }

    public int getImageBackup() {
        return imageBackup;
    }

    public void getListParticipantsWithoutOwner(Callable.CallableArgParticipant op){
        ArrayList<Participants> participants = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id, list -> {
            for (UserModel el : list){
                participants.add(el.toUser());
            }
            op.operationArgParticipant(participants);
        });
    }

    public void getListParticipants(Callable.CallableArgParticipant op){
        ArrayList<Participants> participants = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieve(owner, u ->  participants.add(u.toUser()),null);
        Scalingo.getInstance().getUserDao().retrieveTripParticipants(id, list -> {
            for (UserModel el : list){
                participants.add(el.toUser());
            }
            op.operationArgParticipant(participants);
        });
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if( obj instanceof  Trip){
            return (((Trip) obj)).id == id;
        }
        return false;
    }

}