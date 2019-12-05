package com.example.chris.travelorga_kth.recycler_view_list_activities;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.List;

public class ActivityRecycleViewDataAdapterButton extends ActivityRecycleViewDataAdapter{

    private final List<TripActivity> activityList;
    private final List<TripActivity> activityUpdate;
    private final ActivityRecycleViewDataAdapterAdded otherRecycler;
    private final List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterButton(List<TripActivity> _noSelected, List<TripActivity> activityList, List<TripActivity> update,
                                                ActivityRecycleViewDataAdapterAdded r) {
        super(_noSelected);
        this.activityList = _noSelected;
        activityUpdate =update;
        otherRecycler = r;
        noSelected = _noSelected;
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
                buttonAddSetup(holder, position);

        }
    }

    private void buttonAddSetup(RecyclerViewActivityHolder holder, int position) {
        Button button = holder.getButtonAdd();

        button.setOnClickListener(v -> {
                if(!activityUpdate.contains(activityList.get(position))) {
                    activityUpdate.add(activityList.get(position));
                    noSelected.remove(activityList.get(position));
                    notifyDataSetChanged();
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
