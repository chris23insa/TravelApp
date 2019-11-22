package com.example.chris.travelorga_kth;

/**
 * Created by Chris on 13/11/2019.
 */
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TripRecyclerViewDataAdapter extends RecyclerView.Adapter<TripRecyclerViewItemHolder> {

    private List<Trip> tripItemList;

    public TripRecyclerViewDataAdapter(List<Trip> tripItemList) {
        this.tripItemList = tripItemList;
    }

    @Override
    public TripRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View tripItemView = layoutInflater.inflate(R.layout.card_trip, parent, false);

        // Get trip title text view object.
        final TextView tripTitleView = (TextView)tripItemView.findViewById(R.id.card_view_image_title);
        // Get trip image view object.
        final ImageView tripImageView = (ImageView)tripItemView.findViewById(R.id.card_view_image);
        // Get trip date from view object.
        final TextView tripDateView = (TextView) tripItemView.findViewById(R.id.card_view_date);
        // Get trip description view object.
        final TextView tripDescriptionView = (TextView) tripItemView.findViewById(R.id.card_view_description);

        // When click the image.
        tripImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get trip title text.
                String tripTitle = tripTitleView.getText().toString();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make(tripImageView, "You click " + tripTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // Create and return our custom Trip Recycler View Item Holder object.
        TripRecyclerViewItemHolder ret = new TripRecyclerViewItemHolder(tripItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(TripRecyclerViewItemHolder holder, int position) {
        if(tripItemList!=null) {
            // Get trip item dto in list.
            Trip tripItem = tripItemList.get(position);

            if(tripItem != null) {
                // Set trip item title.
                holder.getTripTitleText().setText(tripItem.getTripName());
                // Set trip item date.
                holder.getTripDateText().setText(tripItem.getTripDateFrom() + " - " + tripItem.getTripDateTo());
                // Set trip item description
                holder.getTripDescriptionText().setText(tripItem.getTripDescription());
                // Set trip image resource id.
                holder.getTripImageView().setImageResource(tripItem.getTripImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(tripItemList!=null)
        {
            ret = tripItemList.size();
        }
        return ret;
    }
}