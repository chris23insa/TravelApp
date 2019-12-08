package com.example.chris.travelorga_kth.recycler_view_participants;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.R;

public class ParticipantsRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final TextView partcipantNameText;

    private final TextView partcipantDescriptionText;

    private final FrameLayout partcipantImageView;

    private final Button buttonAdd;
    private final ToggleButton friendButton;

    public TextView getPartcipantNameText() {
        return partcipantNameText;
    }

    public TextView getPartcipantDescriptionText() {
        return partcipantDescriptionText;
    }

    public FrameLayout getPartcipantImageView() {
        return partcipantImageView;
    }

    public ToggleButton getFriendButton() {
        return friendButton;
    }


    public ParticipantsRecyclerViewHolder(View itemView) {
        super(itemView);

        partcipantNameText = itemView.findViewById(R.id.card_view_name);

        partcipantDescriptionText = itemView.findViewById(R.id.card_view_description);

        partcipantImageView = itemView.findViewById(R.id.card_view_image);

        buttonAdd = itemView.findViewById(R.id.buttonAdd);

         friendButton = itemView.findViewById(R.id.filter_friend);


    }

    public TextView getParticipantName() {
        return partcipantNameText;
    }

    public TextView getParticipantDescription() {
        return partcipantDescriptionText;
    }

    public FrameLayout getParticipantImageView() {
        return partcipantImageView;
    }

    public Button getButtonAdd(){return  buttonAdd;}

}