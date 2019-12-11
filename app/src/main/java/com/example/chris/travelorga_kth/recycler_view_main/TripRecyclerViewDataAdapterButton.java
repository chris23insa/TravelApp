package com.example.chris.travelorga_kth.recycler_view_main;

/*
  Created by Chris on 13/11/2019.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.CreateActivity.CreateNewTripActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.TripDetails;
import com.example.chris.travelorga_kth.base_component.Trip;

import java.util.List;

public class TripRecyclerViewDataAdapterButton extends TripRecyclerViewDataAdapter {

    private final List<Trip> tripItemList;
    private Activity context;

    public TripRecyclerViewDataAdapterButton(List<Trip> tripItemList) {
        super(tripItemList);
        this.tripItemList = tripItemList;
    }

    @Override
    public TripRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View tripItemView = layoutInflater.inflate(R.layout.card_trip_button, parent, false);

        final TextView tripTitleView = tripItemView.findViewById(R.id.title);
        final ImageView tripImageView = tripItemView.findViewById(R.id.image);
        final TextView tripDateView = tripItemView.findViewById(R.id.date);
        final TextView tripDescriptionView = tripItemView.findViewById(R.id.description);

        // Create and return our custom Trip Recycler View Item Holder object.
        return new TripRecyclerViewItemHolder(tripItemView);
    }

    @Override
    public void onBindViewHolder(TripRecyclerViewItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (tripItemList != null) {
            Trip tripItem = tripItemList.get(position);
            if (tripItem != null) {
                Button button = holder.getButton();
                button.setOnClickListener(v -> {
                            Intent intent = new Intent(holder.itemView.getContext(), CreateNewTripActivity.class);
                            intent.putExtra("trip", tripItemList.get(position));
                            holder.itemView.getContext().startActivity(intent);
                            ((Activity) holder.itemView.getContext()).finish();
                        }
                );
                holder.getTripImageView().setOnClickListener(v ->{
                    Intent intent = new Intent(holder.itemView.getContext(), TripDetails.class);
                    intent.putExtra("trip",tripItem);
                    holder.itemView.getContext().startActivity(intent);
                });
            }
        }
    }
}