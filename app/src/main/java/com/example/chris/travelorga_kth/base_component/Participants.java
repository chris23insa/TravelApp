package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private final String image;
    private  String firstName;
    private  String lastName;
    private final String description;
    private ArrayList<Participants> friends;
    private  transient Activity context;
    private final ArrayList<Trip> listTrip;
    private String username;
    private String password;

    public String getImage() {
        return image;
    }

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Participants(String _firstName, String _lastName, String _image, String _description, ArrayList <Participants> _friends, Activity androidActivity){
        image = _image;
        firstName = _firstName;
        password = "password";
        lastName = _lastName;
        username = firstName + lastName;
        description = _description;
        friends =_friends;
        if(friends == null){
            friends = new ArrayList<>();
        }
        listTrip = new ArrayList<>();
        context = androidActivity;
    }

    public void addFriend(Participants p){
        friends.add(p);
    }
    public void removeFriends(Participants p){
        friends.remove(p);
    }



    public void setContext(Activity c){
        context = c;
}

public CircleImageView getProfileImage(){
    int idParticipantsImage = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
    CircleImageView imageProfile = new CircleImageView(context);
    imageProfile.setImageResource(idParticipantsImage);
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
