package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

public class RecyclerViewActivityHolder extends RecyclerView.ViewHolder {

    private final TextView activityTitleText;
    private final TextView activityPlaceText;
    private final TextView activityHourComputedTrip;
    private final TextView activityDateActivityComputedTrip;
    private final TextView activityDateText;
    private final TextView activityDescriptionText;
    private final ImageView activityImageView;
    private final Button buttonAdd;

    public Button getButtonRemove() {
        return buttonRemove;
    }

    private  Button buttonRemove;


    public RecyclerViewActivityHolder(View itemView) {
        super(itemView);

        activityTitleText = itemView.findViewById(R.id.title);
        if (itemView.findViewById(R.id.hours_activity_itinerary) != null) {
            activityHourComputedTrip = itemView.findViewById(R.id.hours_activity_itinerary);
        } else {
            activityHourComputedTrip = null;
        }
        if (itemView.findViewById(R.id.date_activity) != null) {
            activityDateActivityComputedTrip = itemView.findViewById(R.id.date_activity);
        } else {
            activityDateActivityComputedTrip = null;
        }
        activityPlaceText = itemView.findViewById(R.id.description);
        activityDateText = itemView.findViewById(R.id.date);
        activityDescriptionText = itemView.findViewById(R.id.description);
        activityImageView = itemView.findViewById(R.id.image);
        buttonAdd = itemView.findViewById(R.id.buttonAdd);
//        buttonRemove = itemView.findViewById(R.id.buttonRemove);

    }

    public TextView getActivityTitleText() {
        return activityTitleText;
    }
    public TextView getActivityDateText() { return activityDateText; }
    public TextView getActivityDescriptionText() {
        return activityDescriptionText;
    }
    public TextView getActivityHourComputedTripText() {
        return activityHourComputedTrip;
    }
    public TextView getActivityDateActivityComputedTripText() {
        return activityDateActivityComputedTrip;
    }
    public ImageView getActivityImageView() {
        return activityImageView;
    }
    public TextView getActivityPlaceText(){return  activityPlaceText;}
    public Button getButtonAdd(){return  buttonAdd;}
}

