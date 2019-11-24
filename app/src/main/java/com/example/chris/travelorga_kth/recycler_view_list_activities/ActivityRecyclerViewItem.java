package com.example.chris.travelorga_kth.recycler_view_list_activities;

/**
 * Created by Chris on 13/11/2019.
 */

public class ActivityRecyclerViewItem {

    // Save activity name.
    private String activityName;

    // Save activity image
    private int activityImageId;

    // Save activity hours
    private String activityHours;

    public ActivityRecyclerViewItem(String activityName, int activityImageId, String activityHours) {
        this.activityName = activityName;
        this.activityImageId = activityImageId;
        this.activityHours = activityHours;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityImageId() {
        return activityImageId;
    }

    public void setActivityImageId(int activityImageId) {
        this.activityImageId = activityImageId;
    }

    public String getActivityHours() {
        return activityHours;
    }

    public void setActivityHours(String activityHours) {
        this.activityHours = activityHours;
    }
}