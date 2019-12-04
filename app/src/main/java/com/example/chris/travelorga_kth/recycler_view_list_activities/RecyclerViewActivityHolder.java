package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.R;

public class RecyclerViewActivityHolder extends RecyclerView.ViewHolder {

    private final TextView activityTitleText;
    private final TextView activityPlaceText;
    private final TextView activityDateText;
    private final TextView activityDescriptionText;
    private final ImageView activityImageView;
    private final ToggleButton buttonAdd;

    public RecyclerViewActivityHolder(View itemView) {
        super(itemView);

        activityTitleText = itemView.findViewById(R.id.card_view_map_details_image_title);
        activityPlaceText = itemView.findViewById(R.id.card_view_map_details_activity);
        activityDateText = itemView.findViewById(R.id.card_view_map_details_date);
        activityDescriptionText = itemView.findViewById(R.id.card_view_map_details_description);
        activityImageView = itemView.findViewById(R.id.card_view_image);
        buttonAdd = itemView.findViewById(R.id.buttonAdd);
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
    public ToggleButton getButtonAdd(){return  buttonAdd;}
}

