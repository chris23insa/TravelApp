package com.example.chris.travelorga_kth.base_component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.example.chris.travelorga_kth.ActivityDetails;
import com.example.chris.travelorga_kth.helper.Coord;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripActivity implements Serializable {
    public  String place;
    private final String id;
    private final String name;
    private final String image;
    private final String address;
    private  int imageID;
    private final String from;
    private final String to;
    private Date dateFrom;
    private Date dateTo;
    public final String description;
    private  String longDescription;

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
    private final Iterable<String> bulletPoint;
    private  Iterable<String> openingHour;
    private  Iterable<String> price;
    private  transient Activity context;

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
    public int owner;
    String opening;
    String prices;


   /* public TripActivity(String _place, String _name, String _address, String _image, String _from, String _to, String _description,
                        String _longDescription, Iterable<String> _bulletPoint,
                        Iterable<String> _openingHour, Iterable<String> _price, Activity androidActivity) {
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
    }*/

    public TripActivity(String _name, String _address, String _image, Date _from, Date _to, String _description, List<String> _bulletPoint,
                        String _openingHour, String _price, double lat, double lng) {

        image =_image;
        address = _address;
        dateFrom =_from;
        from = dateFrom.toString();
        dateTo = _to;
        to = dateTo.toString();
        description =_description;
        name = _name;
        id = name;
        bulletPoint = _bulletPoint;
        opening = _openingHour;
        prices = _price;

    coord = new Coord(lat,lng);
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
