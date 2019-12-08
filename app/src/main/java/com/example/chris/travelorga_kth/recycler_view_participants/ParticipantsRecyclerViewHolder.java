package com.example.chris.travelorga_kth.recycler_view_participants;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.R;

public class ParticipantsRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final TextView partcipantNameText;

    private final TextView partcipantDescriptionText;

    private final FrameLayout partcipantImageView;

    private final Button buttonAdd;

    public ParticipantsRecyclerViewHolder(View itemView) {
        super(itemView);

        partcipantNameText = itemView.findViewById(R.id.card_view_name);

        partcipantDescriptionText = itemView.findViewById(R.id.card_view_description);

        partcipantImageView = itemView.findViewById(R.id.card_view_image);

        buttonAdd = itemView.findViewById(R.id.buttonAdd);
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