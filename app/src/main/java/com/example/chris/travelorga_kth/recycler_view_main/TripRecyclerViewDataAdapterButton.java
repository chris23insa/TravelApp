package com.example.chris.travelorga_kth.recycler_view_main;

/*
  Created by Chris on 13/11/2019.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Trip;

import java.util.List;

public class TripRecyclerViewDataAdapterButton extends TripRecyclerViewDataAdapter {

    private final List<Trip> tripItemList;
    private Activity context;

    public TripRecyclerViewDataAdapterButton(List<Trip> tripItemList, Activity context) {
        super(tripItemList);
        this.tripItemList = tripItemList;


    }

    @Override
    public TripRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View tripItemView = layoutInflater.inflate(R.layout.card_trip_button, parent, false);

        // Get trip title text view object.
        final TextView tripTitleView = tripItemView.findViewById(R.id.card_view_image_title);
        // Get trip image view object.
        final ImageView tripImageView = tripItemView.findViewById(R.id.card_view_image);
        // Get trip date from view object.
        final TextView tripDateView = tripItemView.findViewById(R.id.card_view_date);
        // Get trip description view object.
        final TextView tripDescriptionView = tripItemView.findViewById(R.id.card_view_description);

        // When click the image.
        tripImageView.setOnClickListener(v -> {
            // Get trip title text.
            String tripTitle = tripTitleView.getText().toString();
            // Create a snackbar and show it.
            Snackbar snackbar = Snackbar.make(tripImageView, "You click " + tripTitle +" image", Snackbar.LENGTH_LONG);
            snackbar.show();
        });

        // Create and return our custom Trip Recycler View Item Holder object.
        return new TripRecyclerViewItemHolder(tripItemView);
    }

    @Override
    public void onBindViewHolder(TripRecyclerViewItemHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (tripItemList != null) {
            Trip tripItem = tripItemList.get(position);
            if (tripItem != null) {
                Button button = holder.getParticipantsView().findViewById(R.id.buttonAdd);
                button.setOnClickListener(v -> {
                            Intent result = new Intent();
                            result.putExtra("trip", tripItemList.get(position));
                            context.setResult(1, result);
                            context.finish();
                        }
                );
            }
        }
    }
}