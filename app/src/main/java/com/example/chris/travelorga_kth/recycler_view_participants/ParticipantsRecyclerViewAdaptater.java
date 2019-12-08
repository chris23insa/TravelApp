package com.example.chris.travelorga_kth.recycler_view_participants;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticipantsRecyclerViewAdaptater extends RecyclerView.Adapter<ParticipantsRecyclerViewHolder> {

    private final List<Participants> participantsList;
    private  List<Participants> participantsUpdate;
    private  ParticipantsRecyclerViewAdaptaterAdded otherRecycler;

    public ParticipantsRecyclerViewAdaptater(List<Participants> list) {
        this.participantsList = list;

    }

    public void addRecycler(ParticipantsRecyclerViewAdaptaterAdded other){
        otherRecycler = other;
    }

    public void addList(ArrayList<Participants> all, ArrayList<Participants> current){
        participantsUpdate = current;
    }

    @Override
    public ParticipantsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View tripItemView = layoutInflater.inflate(R.layout.card_participant_button, parent, false);

        // Get trip title text view object.
        final TextView tripTitleView = tripItemView.findViewById(R.id.title);
        // Get trip image view object.
        final FrameLayout tripImageView = tripItemView.findViewById(R.id.image);
        // Get trip description view object.
        final TextView tripDescriptionView = tripItemView.findViewById(R.id.description);

        // Create and return our custom Trip Recycler View Item Holder object.
        return new ParticipantsRecyclerViewHolder(tripItemView);
    }

    @Override
    public void onBindViewHolder(ParticipantsRecyclerViewHolder holder, int position) {
        if(participantsList!=null) {
            // Get trip item dto in list.
            Participants participantItem = participantsList.get(position);
            if(participantItem != null) {
                  holder.getParticipantName().setText(participantItem.getUsername());
                holder.getParticipantDescription().setText(participantItem.getDescription());

                participantImage(holder,position);
                buttonSetup(holder,position);
            }
        }
    }

    private void participantImage(ParticipantsRecyclerViewHolder holder, int position){
        CircleImageView imageProfile = participantsList.get(position).getProfileImage(holder.getParticipantImageView().getContext());
        holder.getParticipantImageView().addView(imageProfile);
        imageProfile.getLayoutParams().height = 150;
        imageProfile.getLayoutParams().width = 150;
    }

    private void buttonSetup(ParticipantsRecyclerViewHolder holder, int position){
        Button button =   holder.getButtonAdd();
        button.setText("Add");

        button.setOnClickListener(v -> {
                Participants el = participantsList.get(position);
                participantsUpdate.add(el);
                participantsList.remove(el);
                notifyDataSetChanged();
                otherRecycler.notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(participantsList!=null)
        {
            ret = participantsList.size();
        }
        return ret;
    }

    public Participants getTrip(int position){
        return this.participantsList.get(position);
    }
}