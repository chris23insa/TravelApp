package com.example.chris.travelorga_kth;

/**
 * Created by Chris on 13/11/2019.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

public class ActivityRecyclerViewItemHolder extends RecyclerView.ViewHolder {

    private TextView activityTitleText = null;

    private TextView activityHoursText = null;

    private ImageView activityImageView = null;

    public ActivityRecyclerViewItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            activityTitleText = (TextView)itemView.findViewById(R.id.card_view_title_activity_details);

            activityHoursText = (TextView)itemView.findViewById(R.id.card_view_hours_activity_details);

            activityImageView = (ImageView)itemView.findViewById(R.id.card_view_activity_details);
        }
    }

    public TextView getActivityTitleText() {
        return activityTitleText;
    }

    public TextView getActivityHoursText() { return activityHoursText; }

    public ImageView getActivityImageView() {
        return activityImageView;
    }
}