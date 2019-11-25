package com.example.chris.travelorga_kth;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.recycler_view_list_activities.RecyclerViewActivityHolder;

import java.util.List;

public class ActivityRecycleViewDataAdapter extends RecyclerView.Adapter<RecyclerViewActivityHolder>{

    private List<TripActivity> activityList;

    public ActivityRecycleViewDataAdapter(List<TripActivity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity, parent, false);

        final TextView activityTitleView = (TextView)activityItemView.findViewById(R.id.card_view_image_title);
        final ImageView activityImageView = (ImageView)activityItemView.findViewById(R.id.card_view_image);

        // When click the image.
        activityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String activityTitle = activityTitleView.getText().toString();
                Snackbar snackbar = Snackbar.make(activityImageView, "You click " + activityTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // Create and return our custom Trip Recycler View Item Holder object.
        RecyclerViewActivityHolder ret = new RecyclerViewActivityHolder(activityItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerViewActivityHolder holder, int position) {
        if(activityList !=null) {
             TripActivity activity = activityList.get(position);

            if(activity != null) {
                holder.getActivityTitleText().setText(activity.getPlace());
                holder.getActivityDateText().setText(activity.getDateFrom() + " - " + activity.getDateTo());
                holder.getActivityPlaceText().setText(activity.getName());
                holder.getActivityDescriptionText().setText(activity.getDescription());
                holder.getActivityImageView().setImageResource(activity.getImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(activityList !=null)
        {
            ret = activityList.size();
        }
        return ret;
    }
}
