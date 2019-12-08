package com.example.chris.travelorga_kth.recycler_view_list_activities;

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

    private  List<TripActivity> activityList;
    private  List<TripActivity> activityUpdate;
    private  ActivityRecycleViewDataAdapterAdded otherRecycler;
    private final List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterButton(List<TripActivity> _noSelected) {
        super(_noSelected);
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

        final TextView activityTitleView = activityItemView.findViewById(R.id.card_view_map_details_image_title);
        final ImageView activityImageView = activityItemView.findViewById(R.id.card_view_activity_details);


        // Create and return our custom Trip Recycler View Item Holder object.
        return new RecyclerViewActivityHolder(activityItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewActivityHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        if(noSelected !=null) {
            if(noSelected.get(position) != null)
                buttonAddSetup(holder, position);

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
