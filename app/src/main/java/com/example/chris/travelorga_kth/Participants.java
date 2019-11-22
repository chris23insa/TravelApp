package com.example.chris.travelorga_kth;

import java.io.Serializable;
import java.util.ArrayList;

public class Participants implements Serializable {
    public int id;
    public String image;
    public String firstName;
    public String lastName;
    public String description;
    public ArrayList<Participants> friends;

    public Participants(  String _firstName, String _lastName,String _image,String _description, ArrayList <Participants> _friends){
        image = _image;
        firstName = _firstName;
        lastName = _lastName;
        description = _description;
        friends =_friends;
    }

    public void addFriend(Participants p){
        friends.add(p);
    }


}
