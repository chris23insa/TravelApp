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

import java.util.List;

public class ActivityRecycleViewDataAdapterAdded extends ActivityRecycleViewDataAdapter{

    private final List<TripActivity> activityList;
    private final List<TripActivity> activityUpdate;
    private ActivityRecycleViewDataAdapterButton otherRecycler;
    private final List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterAdded(List<TripActivity> activityList, List<TripActivity> update, List<TripActivity> _noSelected ) {
        super(activityList);
        this.activityList = activityList;
        activityUpdate =update;
        noSelected = _noSelected;
    }

    public void setOtherRecycler(ActivityRecycleViewDataAdapterButton r){
        otherRecycler =r;

    }

    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity_button_added, parent, false);

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
                buttonRemoveSetup(holder,position);
                buttonMoveSetup(holder,position);

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
        buttonUp.setOnClickListener(v -> {
            int index = activityUpdate.indexOf(activityList.get(position));
            TripActivity toMove = activityList.get(position);
            activityUpdate.remove(toMove);
            activityUpdate.add(index-1,toMove);
            notifyDataSetChanged();
        });

        buttonDown.setOnClickListener(v -> {
            int index = activityUpdate.indexOf(activityList.get(position));
            TripActivity toMove = activityList.get(position);
            activityUpdate.remove(toMove);
            activityUpdate.add(index+1,toMove);
            notifyDataSetChanged();
        });
    }

    private void buttonRemoveSetup(RecyclerViewActivityHolder holder, int position){
        ToggleButton button =   holder.getButtonRemove();
        button.setOnClickListener(v -> {
                if(activityUpdate.contains(activityList.get(position))) {
                    activityUpdate.remove(activityList.get(position));
                    noSelected.add(activityList.get(position));
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

    public TripActivity getActivity(int position){
        return activityList.get(position);
    }
}
