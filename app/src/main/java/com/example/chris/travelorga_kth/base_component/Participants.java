package com.example.chris.travelorga_kth.base_component;

import android.content.Context;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.ProfileActivity;
import com.example.chris.travelorga_kth.ProfileActivityOther;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;
import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participants implements Serializable {
    private final long id;
    private final String image;
    private final String description;
    private String username;
    public long getId() {
        return id;
    }
    private String getImage()
    {
            return image;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getDescription() {
        return description;
    }

    public ArrayList<Participants> getFriends(Callable.CallableArgParticipant op) {
        ArrayList<Participants> f = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveFriends(id, list -> {
                    for (UserModel um : list) {
                        f.add(um.toUser());
                    }
                    op.operationArgParticipant(f);
                }
        );
        return f;
    }

    //remove list var
    public void getListTrip(ArrayList<Trip> t,  Callable op) {
        //return listTrip;
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
            for (TripModel tm : list) {
                t.add(tm.toTrip());
            }
            op.operation();
        }, null
        );
    }

    public void getListTrip(ArrayList<Trip> t,  Callable.CallableArgTrip op) {
        //return listTrip;
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip());
                    }
                    op.operationArgTrip(t);
                }, null
        );
    }

    public void getListTrip(Callable op) {
        ArrayList<Trip> t = new ArrayList<>();
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip());
                    }
                    op.operation();
                }, null
        );
    }

    public void getListTrip(Callable.CallableArgTrip op) {
        //return listTrip;
        ArrayList<Trip> t = new ArrayList<>();
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip());
                    }
                    op.operationArgTrip(t);
                }, null
        );
    }

    public void getFriendsTrip(ArrayList<Trip> t, Callable op) {
        Scalingo.getInstance().getTripDao().retrieveFriendsTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip());
                    }
                    op.operation();
                }
        );
    }

    public void addTrip(Trip t) throws ScalingoError {
        Scalingo.getInstance().getTripDao().create(new TripModel(id,t.getTripName(),t.getImageURL(),t.getTripDescription(),t.getBudget(),
                t.getPreference(),t.getCoord().getLatLng().latitude,t.getCoord().getLatLng().longitude,t.getDateFrom(),t.getDateTo()),null,null);
      }

  public Participants(long _id, String _username, String _image, String _description) {
      id = _id;
      if(_image.equals("") || _image == null)
          image = MainActivity.placeHolder;
      else{
          image = _image;
        //  image = MainActivity.placeHolder;
      }

      username = _username;
      description = _description;
  }

    public void addFriend(Participants p) {
        Scalingo.getInstance().getUserDao().createFriend(id, p.id, null);
    }

    public void removeFriends(Participants p) {
        Scalingo.getInstance().getUserDao().deleteFriend(id, p.id, null);
    }

    @Override
    public boolean equals(Object p) {
        if (!(p instanceof Participants)) {
            return false;
        } else
            return ((Participants)p).id == id;
    }


    public CircleImageView getProfileImage(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        Glide.with(context).load(getImage()).into(imageProfile);
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