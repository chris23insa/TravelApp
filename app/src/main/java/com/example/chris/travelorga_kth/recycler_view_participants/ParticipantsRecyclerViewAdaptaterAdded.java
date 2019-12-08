package com.example.chris.travelorga_kth.recycler_view_participants;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticipantsRecyclerViewAdaptaterAdded extends RecyclerView.Adapter<ParticipantsRecyclerViewHolder> {

    private final List<Participants> participantsList;
    private  List<Participants> participantsALL;
    private  List<Participants> noSelected;
    private ParticipantsRecyclerViewAdaptater otherRecycler;

    public ParticipantsRecyclerViewAdaptaterAdded(List<Participants> participantsList) {
        this.participantsList = participantsList;
    }

    public void setOther(ArrayList<Participants> all, ArrayList<Participants> no){
        participantsALL =all;
        noSelected = no;
    }
    public void setOtherRecycler(ParticipantsRecyclerViewAdaptater r){
        otherRecycler =r;
    }

    @Override
    public ParticipantsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View tripItemView = layoutInflater.inflate(R.layout.card_participant_button, parent, false);

        // Get trip title text view object.
        final TextView tripTitleView = tripItemView.findViewById(R.id.card_view_image_title);
        // Get trip image view object.
        final FrameLayout tripImageView = tripItemView.findViewById(R.id.card_view_image);
        // Get trip description view object.
        final TextView tripDescriptionView = tripItemView.findViewById(R.id.card_view_description);

        // Create and return our custom Trip Recycler View Item Holder object.
        return new ParticipantsRecyclerViewHolder(tripItemView);
    }

    @Override
    public void onBindViewHolder(ParticipantsRecyclerViewHolder holder, int position) {
        if (participantsList != null) {
            // Get trip item dto in list.
            Participants participantItem = participantsList.get(position);
            if (participantItem != null) {
                holder.getParticipantName().setText(participantItem.getUsername());
                holder.getParticipantDescription().setText(participantItem.getDescription());

                participantImage(holder, position);
                buttonSetup(holder, position);
            }
        }
    }

    private void participantImage(ParticipantsRecyclerViewHolder holder, int position) {
        CircleImageView imageProfile = participantsList.get(position).getProfileImage(holder.getParticipantImageView().getContext());
        holder.getParticipantImageView().addView(imageProfile);
        imageProfile.getLayoutParams().height = 150;
        imageProfile.getLayoutParams().width = 150;
    }

    private void buttonSetup(ParticipantsRecyclerViewHolder holder, int position) {
        Button button = holder.getButtonAdd();
        button.setText("Remove");
        button.setOnClickListener(v -> {
            if (participantsList.contains(participantsList.get(position))) {
                noSelected.add(participantsList.get(position));
                participantsList.remove(participantsList.get(position));

                otherRecycler.notifyDataSetChanged();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (participantsList != null) {
            ret = participantsList.size();
        }
        return ret;
    }

    public Participants getTrip(int position) {
        return this.participantsList.get(position);
    }
}