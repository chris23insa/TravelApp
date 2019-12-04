package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.example.chris.travelorga_kth.ActivityDetails;
import com.example.chris.travelorga_kth.helper.Coord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripActivity implements Serializable {
    public final String place;
    private final String id;
    private final String name;
    private final String image;
    private final String address;
    private final int imageID;
    private final String from;
    private final String to;
    public final String description;
    private final String longDescription;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getBulletPoint() {
        StringBuilder text = new StringBuilder();
        for (String s : bulletPoint) {
            text.append("• ").append(s).append("\n");
        }
        return text.toString();
    }

    public String getOpeningHour() {
        StringBuilder text = new StringBuilder();
        for (String s : openingHour) {
            text.append("• ").append(s).append("\n");
        }
        return text.toString();
    }

    public String getPrice() {
        StringBuilder text = new StringBuilder();
        for (String s : price) {
            text.append("• ").append(s).append("\n");
        }
        return text.toString();
    }


    public Coord coord;
    private final ArrayList<String> bulletPoint;
    private final ArrayList<String> openingHour;
    private final ArrayList<String> price;
    private final transient Activity context;

    public String getName() {
        return name;
    }
    public String getPlace(){return place;}
    public String getAddress(){return address;}
    public String getDateFrom() {
        return from;
    }
    public String getDateTo() {
        return to;
    }
    public String getDescription() {
        return description;
    }
    public int getImageId() {
        return imageID;
    }

    public TripActivity(String _place, String _name, String _address, String _image, String _from, String _to, String _description,
                        String _longDescription, ArrayList<String> _bulletPoint,
                        ArrayList<String> _openingHour, ArrayList<String> _price, Activity androidActivity) {
        Geocoder geocoder = new Geocoder(androidActivity);
        place = _place;
        image =_image;
        address = _address;
        context = androidActivity;
        imageID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        from = _from;
        to = _to;
        description =_description;
        name = _name;
        id = name;
        longDescription = _longDescription;
        bulletPoint = _bulletPoint;
        openingHour = _openingHour;
        price = _price;


        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                coord = new Coord(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            } else {
                coord = new Coord(0, 0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public CircleImageView getImage(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        imageProfile.setImageResource(imageID);
        imageProfile.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("participant", TripActivity.this);
            context.startActivity(intent);
        });
        return imageProfile;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof  TripActivity){
            return id.equals(((TripActivity) o).id);
        }
        return  false;
    }
}
