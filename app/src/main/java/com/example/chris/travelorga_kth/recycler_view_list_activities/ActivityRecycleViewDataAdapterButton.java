package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.ActivityDetails;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.TripDetails;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.List;

public class ActivityRecycleViewDataAdapterButton extends RecyclerView.Adapter<RecyclerViewActivityHolder>{

    private  List<TripActivity> activityList;
    private  List<TripActivity> activityUpdate;
    private  ActivityRecycleViewDataAdapterAdded otherRecycler;
    private final List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterButton(List<TripActivity> _noSelected) {
        noSelected = _noSelected;
    }

    public void addRecyler(ActivityRecycleViewDataAdapterAdded r){
        otherRecycler = r;
    }

    public void addList(List<TripActivity> activityAll, List<TripActivity> current){
        activityList = activityAll;
        activityUpdate =current;
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
        if(noSelected !=null) {
            TripActivity activity  = noSelected.get(position);
            if( activity != null) {
                buttonAddSetup(holder, position);
                holder.getActivityTitleText().setText(activity.getName());
                holder.getActivityDescriptionText().setText(activity.getDescription());
                Log.d("ACTIVITYTEXT",activity.toModel().toString() + "  " + activity.getName());
                Glide.with(holder.getActivityImageView()).load(activity.getImage()).apply(MainActivity.glideOption).into(holder.getActivityImageView());
                holder.getActivityImageView().setOnClickListener(v -> {
                    Intent intent = new Intent(holder.itemView.getContext(), ActivityDetails.class);
                    intent.putExtra("id",activity.getId());
                    holder.itemView.getContext().startActivity(intent);

            });
            }

        }
    }

    private void buttonAddSetup(RecyclerViewActivityHolder holder, int position) {
        Button button = holder.getButtonAdd();
        button.setText("ADD");
        button.setOnClickListener(v -> {
                if(!activityUpdate.contains(activityList.get(position))) {
                    TripActivity el =activityList.get(position);
                    activityUpdate.add(el);
                    noSelected.remove(el);
                    notifyDataSetChanged();
                    otherRecycler.notifyDataSetChanged();
                }
        });
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(noSelected !=null)
        {
            ret = noSelected.size();
        }
        return ret;
    }

    public TripActivity getActivity(int position){
        return noSelected.get(position);
    }
}
