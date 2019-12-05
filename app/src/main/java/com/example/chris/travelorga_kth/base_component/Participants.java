package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.ProfileActivity;
import com.example.chris.travelorga_kth.ProfileActivityOther;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoResponse;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participants implements Serializable {
    private long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private final String image;
    private String firstName;
    private String lastName;
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
        //return friends;
        ArrayList<Participants> f = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveFriends(id, list -> {
                    for (UserModel um : list) {
                        f.add(um.toUser());
                    }
                }
        );
        return f;
    }

    public void getListTrip(ArrayList<Trip> t, ScalingoResponse.SuccessListener<List<Trip>> callback) {
        //return listTrip;
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
            for (TripModel tm : list) {
                t.add(tm.toTrip());
            }
        }, null
        );
    }

    public void getFriendsTrip(ArrayList<Trip> t, ScalingoResponse.SuccessListener<List<Trip>> callback) {
        //return listTrip;
        Scalingo.getInstance().getTripDao().retrieveFriendsTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip());
                    }
                }

        );
    }
    public void addTrip(Trip t){listTrip.add(t);}

    private int imageID;

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

    public Participants(String _username, String _image, String _description, ArrayList<Participants> _friends) {
        image = _image;
        username = _username;
        description = _description;
        friends = _friends;
        if (friends == null) {
            friends = new ArrayList<>();
        }
        listTrip = new ArrayList<>();
    }

    public Participants(long _id, String _username, String _image, String _description) {
        id = _id;
        image = _image;
        username = _username;
        description = _description;
        friends = new ArrayList<>();
        listTrip = new ArrayList<>();
    }

    public void addFriend(Participants p){
        friends.add(p);
    }

    public void addFriend(long friendID) {
        Scalingo.getInstance().getUserDao().createFriend(id, friendID, null);
    }

    public void removeFriends(long friendID) {
        Scalingo.getInstance().getUserDao().deleteFriend(id, friendID, null);
    }

    public void removeFriends(Participants p) {
        friends.remove(p);
    }

    @Override
    public boolean equals(Object p) {
        if (!(p instanceof Participants)) {
            return false;
        } else
            return ((Participants) p).username.equals(this.username);
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

    public CircleImageView getProfileImageFromUrl(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        String url = image;
        Glide.with(context).load(url).into(imageProfile);
        imageProfile.setOnClickListener(v -> {
            Intent intent;
            if (MainActivity.currentUser == Participants.this)
                intent = new Intent(context, ProfileActivity.class);
            else
                intent = new Intent(context, ProfileActivityOther.class);
            intent.putExtra("participant", Participants.this);
            context.startActivity(intent);
        });
        return imageProfile;
    }

}
