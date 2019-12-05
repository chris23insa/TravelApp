package com.example.chris.travelorga_kth.recycler_view_search;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.recycler_view_list_activities.RecyclerViewActivityHolder;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewItemHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultiViewDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Pair<Integer,Integer>> typeAndIndex; //maps from position to type and index
    private final ArrayList<Trip> tripItemList;
    private final ArrayList<TripActivity> activityList;


    private final int VIEW_TYPE_TRIP = 1;
    private final int VIEW_TYPE_ACTIVITY = 2;
    final int VIEW_TYPE_LOCATION = 3;

    // TODO: add the viewholder for locations
    public MultiViewDataAdapter(ArrayList<Trip> tripItemList, ArrayList<TripActivity> activityList){
        this.typeAndIndex = new ArrayList<>();
        this.tripItemList=tripItemList;
        for (int i = 0; i < tripItemList.size(); ++i) {
            typeAndIndex.add(new Pair(VIEW_TYPE_TRIP, i));
        }
        this.activityList = activityList;
        for (int i = 0; i < activityList.size(); ++i) {
            typeAndIndex.add(new Pair(VIEW_TYPE_ACTIVITY, i));
        }

        //Collections.shuffle(typeAndIndex); //test to make sure this implementation is order-independent
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String classtype = String.valueOf(this.getClass());
        if (viewType == VIEW_TYPE_TRIP){
            // Get LayoutInflater object.
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            // Inflate the RecyclerView item layout xml.
            View tripItemView = layoutInflater.inflate(R.layout.card_trip, parent, false);

            // Get trip title text view object.
            final TextView tripTitleView = tripItemView.findViewById(R.id.card_view_image_title);
            // Get trip image view object.
            final ImageView tripImageView = tripItemView.findViewById(R.id.card_view_image);

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

        } else if (viewType == VIEW_TYPE_ACTIVITY) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View activityItemView = layoutInflater.inflate(R.layout.card_activity_map_details, parent, false);

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
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getClass() == TripRecyclerViewItemHolder.class) {
            if(tripItemList!=null) {
                // Get trip item dto in list.
                Trip tripItem = tripItemList.get(typeAndIndex.get(position).second);
                //cast the holder
                TripRecyclerViewItemHolder holder = (TripRecyclerViewItemHolder) viewHolder;
                if(tripItem != null) {
                    // Set trip item title.
                    holder.getTripTitleText().setText(tripItem.getTripName());
                    // Set trip item date.
                    holder.getTripDateText().setText(tripItem.getTripDateFrom() + " - " + tripItem.getTripDateTo());
                    // Set trip item description
                    holder.getTripDescriptionText().setText(tripItem.getTripDescription());
                    // Set trip image resource id.
                    holder.getTripImageView().setImageResource(tripItem.getTripImageId());
                    for(Participants participants : tripItem.getListParticipants() ) {
                        CircleImageView imageProfile = participants.getProfileImage(holder.getParticipantsView().getContext());
                        holder.getParticipantsView().addView(imageProfile);
                        imageProfile.getLayoutParams().height = 100;
                        imageProfile.getLayoutParams().width = 100;
                    }
                }
            }
        } else if (viewHolder.getClass() == RecyclerViewActivityHolder.class) {
            if(activityList !=null) {
                RecyclerViewActivityHolder holder = (RecyclerViewActivityHolder) viewHolder;
                TripActivity activity = activityList.get(typeAndIndex.get(position).second);

                if(activity != null) {
                    holder.getActivityTitleText().setText(activity.getPlace());
                    holder.getActivityDateText().setText(activity.getDateFrom() + " - " + activity.getDateTo());
                    holder.getActivityPlaceText().setText(activity.getName());
                    holder.getActivityDescriptionText().setText(activity.getDescription());
                    holder.getActivityImageView().setImageResource(activity.getImageId());
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return typeAndIndex.get(position).first;
    }

    @Override
    public int getItemCount() {
        return typeAndIndex.size();
    }

    public Trip getTrip(int position){
        if (typeAndIndex.get(position).first != VIEW_TYPE_TRIP) {
            Log.e("getTrip", "This is not a trip");
            return null;
        }
        return this.tripItemList.get(typeAndIndex.get(position).second);
    }
}
