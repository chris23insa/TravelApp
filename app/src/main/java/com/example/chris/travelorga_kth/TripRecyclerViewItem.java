package com.example.chris.travelorga_kth;

/**
 * Created by Chris on 13/11/2019.
 */

public class TripRecyclerViewItem {

    // Save trip name.
    private String tripName;

    // Save trip image resource id.
    private int tripImageId;

    // Save trip date from.
    private String tripDateFrom;

    // Save trip date to.
    private String tripDateTo;

    public TripRecyclerViewItem(String tripName, int tripImageId, String tripDateFrom, String tripDateTo) {
        this.tripName = tripName;
        this.tripImageId = tripImageId;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
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

    public void setTripDateTo(String tripDateTo) {
        this.tripDateTo = tripDateTo;
    }
}
