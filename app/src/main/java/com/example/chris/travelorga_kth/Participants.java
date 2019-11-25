package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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

    public Participants(  String _firstName, String _lastName,String _image,String _description, ArrayList <Participants> _friends, Activity androidActivity){
        image = _image;
        firstName = _firstName;
        lastName = _lastName;
        description = _description;
        friends =_friends;
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
