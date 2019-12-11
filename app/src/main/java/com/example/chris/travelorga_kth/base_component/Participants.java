package com.example.chris.travelorga_kth.base_component;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.Login;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.Profile.ProfileActivity;
import com.example.chris.travelorga_kth.Profile.ProfileActivityOther;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class Participants implements Serializable {
    private final long id;
    private final String image;
    private final String description;
    private String username;

    public long getId() {
        return id;
    }

    private String getImage() {
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

    public void getFriends(Callable.CallableArgParticipantList op) {
        ArrayList<Participants> f = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveFriends(id, list -> {
                    for (UserModel um : list) {
                        f.add(um.toUser());
                    }
                    op.operationArgParticipant(f);
                }
        );
    }

    public static void getCurrentUser(Callable.CallableArgParticipant op) {
        Scalingo.getInstance().getUserDao().retrieve(Login.currentUserId, um -> op.operationArgParticipant(um.toUser()), Throwable::printStackTrace);
    }
    public static void getAllUser(Callable.CallableArgParticipantList op) {
        Scalingo.getInstance().getUserDao().retrieveAll(tmp -> {
            ArrayList<Participants> list = tmp.stream()
                    .map(UserModel::toUser).collect(Collectors.toCollection(ArrayList::new));
                    op.operationArgParticipant(list);
                },
                Throwable::printStackTrace);
    }

    public void getListTrip(Callable.CallableArgTripList op, Context c) {
        ArrayList<Trip> t = new ArrayList<>();
        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(id, list -> {
                    for (TripModel tm : list) {
                        t.add(tm.toTrip(c));
                    }
            Collections.sort(t);
            Collections.reverse(t);
                    op.operationArgTrip(t);
                }, null
        );
    }


    public void getFriendsTrip(Callable.CallableArgTripList op, Context c) {
        Scalingo.getInstance().getTripDao().retrieveFriendsTrips(id, listF -> {
                    ArrayList<Trip> list = new ArrayList();
                    for (TripModel tm : listF) {
                        list.add(tm.toTrip(c));
                    }
                    getListTrip(listM -> {
                        list.removeAll(listM);
                        Collections.sort(list);
                        Collections.reverse(list);
                        op.operationArgTrip(list);
                    }, c);
                }
        );
}

    public void addTrip(Trip t) throws ScalingoError {
        Scalingo.getInstance().getTripDao().create(new TripModel(id, t.getTripName(), t.getPlace(), t.getImageURL(), t.getTripDescription(), t.getBudget(),
                t.getPreference(), t.getCoord().getLatLng().latitude, t.getCoord().getLatLng().longitude, t.getDateFrom(), t.getDateTo()), null, null);
    }

    public Participants(long _id, String _username, String _image, String _description) {
        id = _id;
        image = "https://travelapp-backend.osc-fr1.scalingo.io/people/" + _image;
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
            return ((Participants) p).id == id;
    }


    public CircleImageView getProfileImage(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        Glide.with(context).load(getImage()).apply(MainActivity.glideOption).into(imageProfile);
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