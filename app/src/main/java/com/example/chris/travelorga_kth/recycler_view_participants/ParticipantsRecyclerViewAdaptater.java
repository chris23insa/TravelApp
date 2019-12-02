package com.example.chris.travelorga_kth.recycler_view_participants;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticipantsRecyclerViewAdaptater extends RecyclerView.Adapter<ParticipantsRecyclerViewHolder> {

    private final List<Participants> participantsList;
    private ArrayList<Participants> participantsUpdate;

    public ParticipantsRecyclerViewAdaptater(List<Participants> participantsList, ArrayList<Participants> list) {
        this.participantsList = participantsList;
        this.participantsUpdate = list;
    }

    @Override
    public ParticipantsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View tripItemView = layoutInflater.inflate(R.layout.card_participant, parent, false);

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
        if(participantsList!=null) {
            // Get trip item dto in list.
            Participants participantItem = participantsList.get(position);
            if(participantItem != null) {
                // Set trip item title.
                holder.getParticipantName().setText(participantItem.getUsername());
                   // Set trip item description
                holder.getParticipantDescription().setText(participantItem.getDescription());
                // Set trip image resource id.
                CircleImageView imageProfile = participantItem.getProfileImage();
                holder.getParticipantImageView().addView(imageProfile);
                imageProfile.getLayoutParams().height = 150;
                imageProfile.getLayoutParams().width = 150;
            }
            ToggleButton button =   holder.getButtonAdd();

            Log.w("DEBUGAPP_Participant",participantsUpdate.toString()+ "  " );
            if(participantsUpdate.size()>0)
                 Log.w("DEBUGAPP_UPDATE",participantsUpdate.get(0).getUsername().toString());
            Log.w("DEBUGAPP_FRIENDS",participantsList.get(position).toString()+ "  "+ participantsList.get(position).getUsername().toString());

            boolean tmp = false;
            for(Participants p : participantsUpdate){
                if(p.getUsername().equals(participantsList.get(position).getUsername())){
                    tmp = true;
                }
            }
            if (tmp){
                button.setChecked(true);
            }else{
                button.setChecked(false);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(button.isChecked()){
                        participantsUpdate.add(participantsList.get(position));
                    }else{
                       // participantsUpdate.remove(participantsList.get(position));
                        Participants tmp = null;
                        for(Participants p : participantsUpdate){
                            if(p.getUsername().equals(participantsList.get(position).getUsername()))
                                tmp = p;
                        }
                        if(tmp!=null)
                            participantsUpdate.remove(tmp);
                    }

                    Log.d("cc",participantsUpdate.toString());

                }
            });
        }
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