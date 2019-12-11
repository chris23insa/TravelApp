package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.List;

public class ActivityRecycleViewDataAdapterAdded extends RecyclerView.Adapter<RecyclerViewActivityHolder>{

    private final List<TripActivity> activityList;
    private ActivityRecycleViewDataAdapterButton otherRecycler;
    private  List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterAdded(List<TripActivity> activityList) {
        this.activityList = activityList;

    }
    public void setOtherRecycler(ActivityRecycleViewDataAdapterButton r){
        otherRecycler =r;
    }
public void addList(List<TripActivity> _noSelected){
    noSelected = _noSelected;
}
    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity_button, parent, false);


        // Create and return our custom Trip Recycler View Item Holder object.
        return new RecyclerViewActivityHolder(activityItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewActivityHolder holder, int position) {
        if(activityList !=null) {
            TripActivity activity = activityList.get(position);
            if(activity!= null) {
                buttonRemoveSetup(holder, position);
                holder.getActivityTitleText().setText(activity.getName());
                holder.getActivityDescriptionText().setText(activity.getDescription());
                Glide.with(holder.getActivityImageView()).load(activity.getImage()).apply(MainActivity.glideOption).into(holder.getActivityImageView());

            }

        }
    }


    private void buttonRemoveSetup(RecyclerViewActivityHolder holder, int position){
        Button button =   holder.getButtonAdd();
        button.setText("Remove");
        button.setOnClickListener(v -> {
                if(activityList.contains(activityList.get(position))) {
                    noSelected.add(activityList.get(position));
                    activityList.remove(activityList.get(position));

                    otherRecycler.notifyDataSetChanged();
                }
        });
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
