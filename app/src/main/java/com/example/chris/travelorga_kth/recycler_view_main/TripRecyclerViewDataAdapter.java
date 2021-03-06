package com.example.chris.travelorga_kth.recycler_view_main;

/*
  Created by Chris on 13/11/2019.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripRecyclerViewDataAdapter extends RecyclerView.Adapter<TripRecyclerViewItemHolder> {

    private final List<Trip> tripItemList;

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
        final TextView tripTitleView = tripItemView.findViewById(R.id.title);
        // Get trip image view object.
        final ImageView tripImageView = tripItemView.findViewById(R.id.image);
        // Get trip date from view object.
        final TextView tripDateView = tripItemView.findViewById(R.id.date);
        // Get trip description view object.
        final TextView tripDescriptionView = tripItemView.findViewById(R.id.description);

        // Create and return our custom Trip Recycler View Item Holder object.
        return new TripRecyclerViewItemHolder(tripItemView);
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
                if( holder.getTripDateText() != null)
                  holder.getTripDateText().setText(tripItem.getTripDateFrom() + " - " + tripItem.getTripDateTo());
                // Set trip item description
                holder.getTripDescriptionText().setText(tripItem.getTripDescription());
                // Set trip image resource id.
                Glide.with(holder.getTripImageView()).load(tripItem.getImageURL()).apply(MainActivity.glideOption).into(holder.getTripImageView());
               if(holder.getParticipantsView()!= null) {
                   holder.getParticipantsView().removeAllViews();
                   tripItem.getListParticipants(list -> {
                       Log.d("LISTM", list.toString() + "  " + tripItem.getId());
                       for (Participants participants : list) {
                           CircleImageView imageProfile = participants.getProfileImage(holder.getParticipantsView().getContext());
                           if (imageProfile.getParent() != null)
                               ((ViewGroup) imageProfile.getParent()).removeView(imageProfile);
                           holder.getParticipantsView().addView(imageProfile);
                           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                   LinearLayout.LayoutParams.WRAP_CONTENT);
                           lp.setMargins(10, 40, 10, 20);
                           imageProfile.setLayoutParams(lp);
                           imageProfile.getLayoutParams().height = 150;
                           imageProfile.getLayoutParams().width = 150;
                       }
                   }
               );

               }
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

    public Trip getTrip(int position){
        return this.tripItemList.get(position);
    }
}