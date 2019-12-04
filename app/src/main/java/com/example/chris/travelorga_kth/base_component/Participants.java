package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.ProfileActivity;
import com.example.chris.travelorga_kth.ProfileActivityOther;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participants implements Serializable {
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private final String image;
    private final String firstName;
    private final String lastName;
    private final String description;
    private ArrayList<Participants> friends;
    private final ArrayList<Trip> listTrip;
    private String username;
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<Participants> getFriends() {
        return friends;
    }
    public ArrayList<Trip>  getListTrip(){return listTrip;}
    public void addTrip(Trip t){listTrip.add(t);}

    private final int imageID;

    public Participants(String _firstName, String _lastName, String _image, String _description, ArrayList <Participants> _friends, Activity androidActivity){
        image = _image;
        firstName = _firstName;
        lastName = _lastName;
        username = firstName + lastName;
        description = _description;
        friends =_friends;
        if(friends == null){
            friends = new ArrayList<>();
        }
        listTrip = new ArrayList<>();
        imageID = androidActivity.getResources().getIdentifier(image, "drawable", androidActivity.getPackageName());
    }

    public void addFriend(Participants p){
        friends.add(p);
    }
    public void removeFriends(Participants p){
        friends.remove(p);
    }

    @Override
    public boolean equals(Object p) {
        if (!(p instanceof Participants)) {
            return false;
        } else
            return ((Participants) p).getUsername().equals(this.getUsername());
    }

    public CircleImageView getProfileImage(Context context) {
    CircleImageView imageProfile = new CircleImageView(context);

        imageProfile.setImageResource(imageID);
    imageProfile.setOnClickListener(v -> {
        Intent intent;
        if(MainActivity.currentUser == Participants.this)
            intent = new Intent(context, ProfileActivity.class);
        else
            intent = new Intent(context, ProfileActivityOther.class);
        intent.putExtra("participant",Participants.this);
        context.startActivity(intent);
    });
    return imageProfile;
}

}
