package com.example.chris.travelorga_kth.base_component;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.ActivityDetails;
import com.example.chris.travelorga_kth.helper.Coord;
import com.example.chris.travelorga_kth.network.ActivityModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripActivity implements Serializable {
    public  String place;
    private final long id;
    private final String name;
    private final String address;
    private final String from;
    private final String to;
    public final String description;
    private String opening;
    private String price;
    private final Date dateFrom;
    private final Date dateTo;

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public String getBulletPoint() {
        StringBuilder text = new StringBuilder();
        for (String s : bulletPoint) {
            text.append("• ").append(s).append("\n");
        }
        return text.toString();
    }

    public String getOpeningHour() {
        return opening;
    }

    public String getPrice() {
        return price;
    }

    //why do activity have tripID
      public ActivityModel toModel(){
        return new ActivityModel(id,0,name,description,image,price,getOpeningHour(),
                coord.getLatLng().latitude,coord.getLatLng().longitude,
                dateFrom,dateTo);
    }

    public final Coord coord;
    private final Iterable<String> bulletPoint;
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
    public int owner;
private final String image;
      public TripActivity(long _id,String _name, String _address, String _image, Date _from, Date _to, String _description, List<String> _bulletPoint,
                        String _openingHour, String _price, double lat, double lng) {

        address = _address;
        dateFrom =_from;
        dateTo = _to;
        from = _from.toString();
        to = _to.toString();
        description =_description;
    image = _image;
        name = _name;
        id = _id;
        bulletPoint = _bulletPoint;

        coord = new Coord(lat,lng);
    }
    public String getImage() {
        return image;
    }
    public CircleImageView getImageCircle(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        Glide.with(context).load(image).into(imageProfile);
        imageProfile.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("activity", TripActivity.this);
            context.startActivity(intent);
        });
        return imageProfile;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof  TripActivity){
            return id == (((TripActivity) o).id);
        }
        return  false;
    }
}
