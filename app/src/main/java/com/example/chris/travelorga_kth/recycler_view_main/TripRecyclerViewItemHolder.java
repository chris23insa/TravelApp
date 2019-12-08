package com.example.chris.travelorga_kth.recycler_view_main;

/*
  Created by Chris on 13/11/2019.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;

public class TripRecyclerViewItemHolder extends RecyclerView.ViewHolder {

    private final TextView tripTitleText;

    private final TextView tripDateText;

    private final TextView tripDescriptionText;

    private final ImageView tripImageView;

    private final LinearLayout tripParticipantsView;
    private  final Button button;

    public TripRecyclerViewItemHolder(View itemView) {
        super(itemView);

        tripTitleText = itemView.findViewById(R.id.card_view_image_title);

        tripDateText = itemView.findViewById(R.id.card_view_date);

        tripDescriptionText = itemView.findViewById(R.id.card_view_description);

        tripImageView = itemView.findViewById(R.id.card_view_image);

        tripParticipantsView = itemView.findViewById(R.id.card_view_participants);

        button = itemView.findViewById(R.id.buttonAdd);
    }

    public TextView getTripTitleText() {
        return tripTitleText;
    }

    public TextView getTripDateText() { return tripDateText; }

    public TextView getTripDescriptionText() {
        return tripDescriptionText;
    }

    public ImageView getTripImageView() {
        return tripImageView;
    }

    public LinearLayout getParticipantsView(){return  tripParticipantsView;}

    public Button getButton(){return  button;}
}