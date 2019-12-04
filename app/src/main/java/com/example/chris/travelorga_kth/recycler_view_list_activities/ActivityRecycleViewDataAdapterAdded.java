package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecycleViewDataAdapterButton extends ActivityRecycleViewDataAdapter{

    private final List<TripActivity> activityList;
    private final ArrayList<TripActivity> activityUpdate;

    public ActivityRecycleViewDataAdapterButton(List<TripActivity> activityList, ArrayList<TripActivity> update) {
        super(activityList);
        this.activityList = activityList;
        activityUpdate =update;
    }

    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity_button, parent, false);

        final TextView activityTitleView = activityItemView.findViewById(R.id.card_view_map_details_image_title);
        final ImageView activityImageView = activityItemView.findViewById(R.id.card_view_image);
        activityImageView.setOnClickListener(v -> {
            String activityTitle = activityTitleView.getText().toString();
            Snackbar snackbar = Snackbar.make(activityImageView, "You click " + activityTitle +" image", Snackbar.LENGTH_LONG);
            snackbar.show();
        });

        // Create and return our custom Trip Recycler View Item Holder object.
        return new RecyclerViewActivityHolder(activityItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewActivityHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        if(activityList !=null) {
            if(activityList.get(position) != null)
                buttonAddSetup(holder,position);

        }
    }


    private void buttonMoveSetup(RecyclerViewActivityHolder holder, int position){
        ImageButton buttonUp =   holder.getButtonUp();
        ImageButton buttonDown = holder.getButtonDown();

        if(position == 1){
            buttonUp.setVisibility(View.INVISIBLE);
        }
        if(position == getItemCount()){
            buttonDown.setVisibility(View.INVISIBLE);
        }
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = activityUpdate.indexOf(activityList.get(position));
                TripActivity toMove = activityList.get(position);
                activityUpdate.remove(toMove);
                activityUpdate.add(index-1,toMove);
                notifyDataSetChanged();
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = activityUpdate.indexOf(activityList.get(position));
                TripActivity toMove = activityList.get(position);
                activityUpdate.remove(toMove);
                activityUpdate.add(index+1,toMove);
                notifyDataSetChanged();
            }
        });
    }

    private void buttonAddSetup(RecyclerViewActivityHolder holder, int position){
        ToggleButton button =   holder.getButtonAdd();

        if (activityUpdate.contains(activityList.get(position))){
            button.setChecked(true);
        }else{
            button.setChecked(false);
        }
        button.setOnClickListener(v -> {
            if(button.isChecked()){
                activityUpdate.add(activityList.get(position));
            }else{
                if(activityUpdate.contains(activityList.get(position)))
                    for(TripActivity p : activityUpdate){
                        if(p.equals(activityList.get(position))) {
                            activityUpdate.remove(p);
                        }
                    }
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

    public TripActivity getActivity(int position){
        return activityList.get(position);
    }
}
