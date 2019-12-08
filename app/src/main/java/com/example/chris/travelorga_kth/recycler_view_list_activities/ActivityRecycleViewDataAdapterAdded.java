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

public class ActivityRecycleViewDataAdapterAdded extends ActivityRecycleViewDataAdapter{

    private final List<TripActivity> activityList;
    private ActivityRecycleViewDataAdapterButton otherRecycler;
    private  List<TripActivity> noSelected;

    public ActivityRecycleViewDataAdapterAdded(List<TripActivity> activityList) {
        super(activityList);
        this.activityList = activityList;

    }
    public void setOtherRecycler(ActivityRecycleViewDataAdapterButton r){
        otherRecycler =r;
    }
public void addList( List<TripActivity> all, List<TripActivity> _noSelected ){
    noSelected = _noSelected;
}
    @Override
    public RecyclerViewActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View activityItemView = layoutInflater.inflate(R.layout.card_activity_button, parent, false);

        final TextView activityTitleView = activityItemView.findViewById(R.id.card_view_map_details_image_title);
        final ImageView activityImageView = activityItemView.findViewById(R.id.card_view_image);

        // Create and return our custom Trip Recycler View Item Holder object.
        return new RecyclerViewActivityHolder(activityItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewActivityHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        if(activityList !=null) {
            if(activityList.get(position) != null)
                buttonRemoveSetup(holder,position);
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

    public TripActivity getActivity(int position){
        return activityList.get(position);
    }
}
