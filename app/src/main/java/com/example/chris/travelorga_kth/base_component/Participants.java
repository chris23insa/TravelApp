package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participants implements Serializable {
    private int id;
    private String image;
    private String firstName;
    private String lastName;
    private String description;
    private ArrayList<Participants> friends;
    private  transient Activity context;

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

    public Participants(String _firstName, String _lastName, String _image, String _description, ArrayList <Participants> _friends, Activity androidActivity){
        image = _image;
        firstName = _firstName;
        lastName = _lastName;
        description = _description;
        friends =_friends;
        if(friends == null){
            friends = new ArrayList<>();
        }

        context = androidActivity;
    }

    public void addFriend(Participants p){
        friends.add(p);
    }

public CircleImageView getProfileImage(){
    int idParticipantsImage = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
    CircleImageView imageProfile = new CircleImageView(context);
    imageProfile.setImageResource(idParticipantsImage);

    return imageProfile;
}

}
