package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

public class RecyclerViewActivityHolder extends RecyclerView.ViewHolder {

    private final TextView activityTitleText;
    private final TextView activityPlaceText;
    private final TextView activityDateText;
    private final TextView activityDescriptionText;
    private final ImageView activityImageView;
    private final Button buttonAdd;

    public Button getButtonRemove() {
        return buttonRemove;
    }

    private  Button buttonRemove;
    private  final ImageButton buttonUp;
    private  final ImageButton buttonDown;

    public ImageButton getButtonUp() {
        return buttonUp;
    }

    public ImageButton getButtonDown() {
        return buttonDown;
    }

    public RecyclerViewActivityHolder(View itemView) {
        super(itemView);

        activityTitleText = itemView.findViewById(R.id.card_view_title_activity_details);
        activityPlaceText = itemView.findViewById(R.id.card_view_hours_activity_details);
        activityDateText = itemView.findViewById(R.id.card_view_map_details_date);
        activityDescriptionText = itemView.findViewById(R.id.card_view_hours_activity_details);
        activityImageView = itemView.findViewById(R.id.card_view_activity_details);
        buttonAdd = itemView.findViewById(R.id.buttonAdd);
//        buttonRemove = itemView.findViewById(R.id.buttonRemove);
        buttonUp = itemView.findViewById(R.id.buttonUp);
        buttonDown = itemView.findViewById(R.id.buttonDown);
    }

    public TextView getActivityTitleText() {
        return activityTitleText;
    }
    public TextView getActivityDateText() { return activityDateText; }
    public TextView getActivityDescriptionText() {
        return activityDescriptionText;
    }
    public ImageView getActivityImageView() {
        return activityImageView;
    }
    public TextView getActivityPlaceText(){return  activityPlaceText;}
    public Button getButtonAdd(){return  buttonAdd;}
}

