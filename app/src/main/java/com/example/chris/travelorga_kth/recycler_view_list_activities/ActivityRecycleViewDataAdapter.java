package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.List;

public class ActivityRecycleViewDataAdapter extends RecyclerView.Adapter<RecyclerViewActivityHolder>{

    private final List<TripActivity> activityList;

    public ActivityRecycleViewDataAdapter(List<TripActivity> activityList) {
        this.activityList = activityList;
        Log.d("list_activity",activityList.toString());
    }


    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity_map_details, parent, false);

        final TextView activityTitleView = activityItemView.findViewById(R.id.title);
        final ImageView activityImageView = activityItemView.findViewById(R.id.image);
        // Create and return our custom Trip Recycler View Item Holder object.
        return new RecyclerViewActivityHolder(activityItemView);
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
                Glide.with(holder.getActivityImageView()).load(activity.getImage()).apply(MainActivity.glideOption).into(holder.getActivityImageView());

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

    public TripActivity getActivity(int position){
        return activityList.get(position);
    }
}
