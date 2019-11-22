package com.example.chris.travelorga_kth.recycler_view_list_activities;

/**
 * Created by Chris on 13/11/2019.
 */
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

import java.util.List;

public class ActivityRecyclerViewDataAdapter extends RecyclerView.Adapter<ActivityRecyclerViewItemHolder> {

    private List<ActivityRecyclerViewItem> activityItemList;

    public ActivityRecyclerViewDataAdapter(List<ActivityRecyclerViewItem> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public ActivityRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View activityItemView = layoutInflater.inflate(R.layout.card_activity, parent, false);

        // Get activity title text view object.
        final TextView activityTitleView = (TextView)activityItemView.findViewById(R.id.card_view_title_activity_details);
        // Get activity image view object.
        final ImageView activityImageView = (ImageView)activityItemView.findViewById(R.id.card_view_activity_details);
        // Get activity hours from view object.
        final TextView activityHoursView = (TextView) activityItemView.findViewById(R.id.card_view_hours_activity_details);

        // When click the image.
        activityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get activity title text.
                String activityTitle = activityTitleView.getText().toString();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make(activityImageView, "You click " + activityTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // Create and return our custom activity Recycler View Item Holder object.
        ActivityRecyclerViewItemHolder ret = new ActivityRecyclerViewItemHolder(activityItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(ActivityRecyclerViewItemHolder holder, int position) {
        if(activityItemList!=null) {
            // Get activity item dto in list.
            ActivityRecyclerViewItem activityItem = activityItemList.get(position);

            if(activityItem != null) {
                // Set activity item title.
                holder.getActivityTitleText().setText(activityItem.getActivityName());
                // Set activity item hours.
                holder.getActivityHoursText().setText(activityItem.getActivityHours());
                // Set activity image resource id
                holder.getActivityImageView().setImageResource(activityItem.getActivityImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(activityItemList!=null)
        {
            ret = activityItemList.size();
        }
        return ret;
    }

    public ActivityRecyclerViewItem getActivity(int position){
        return this.activityItemList.get(position);
    }
}