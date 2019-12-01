package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

public class RecyclerViewActivityHolder extends RecyclerView.ViewHolder {

    private TextView activityTitleText = null;
    private TextView activityPlaceText = null;
    private TextView activityDateText = null;
    private TextView activityDescriptionText = null;
    private ImageView activityImageView = null;

    public RecyclerViewActivityHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            activityTitleText = itemView.findViewById(R.id.card_view_map_details_image_title);
            activityPlaceText = itemView.findViewById(R.id.card_view_map_details_activity);
            activityDateText = itemView.findViewById(R.id.card_view_map_details_date);
            activityDescriptionText = itemView.findViewById(R.id.card_view_map_details_description);
            activityImageView = itemView.findViewById(R.id.card_view_image);
        }
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
}
