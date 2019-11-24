package com.example.chris.travelorga_kth;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
            activityTitleText = (TextView)itemView.findViewById(R.id.card_view_map_details_image_title);
            activityPlaceText =(TextView)itemView.findViewById(R.id.card_view_map_details_activity);
            activityDateText = (TextView)itemView.findViewById(R.id.card_view_map_details_date);
            activityDescriptionText = (TextView)itemView.findViewById(R.id.card_view_map_details_description);
            activityImageView = (ImageView)itemView.findViewById(R.id.card_view_map_details_image);
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

