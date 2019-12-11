package com.example.chris.travelorga_kth.base_component;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.ActivityDetails;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.helper.Coord;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripActivity implements Serializable, Comparable<TripActivity> {
    public String place;
    private final long id;
    private final String name;
    private final String address;
    private final String from;
    private final String to;
    public final String description;
    private final String opening;
    private final String price;
    private final Date dateFrom;
    private final Date dateTo;
    private int imageBackup;
    private  Coord coord;
    private final Iterable<String> bulletPoint;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getAddress() {
        return address;
    }


    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;

    public String getDateStringFrom() {
        return from;
    }

    public String getDateStringTo() {
        return to;

    }

    public String getDescription() {
        return description;
    }

    private int owner;
    private final String image;

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
    public ActivityModel toModel() {
        return new ActivityModel(id, 0, name, description, image, price, getOpeningHour(),
                coord.getLatLng().latitude, coord.getLatLng().longitude,
                dateFrom, dateTo);
    }


    public long getId() {
        return id;
    }

    public String getOpening() {
        return opening;
    }

    public int getImageBackup() {
        return imageBackup;
    }

    public Coord getCoord() {
        return coord;
    }

    public int getOwner() {
        return owner;
    }

    public TripActivity(long _id, String _name, String _address, String _image, Date _from, Date _to, String _description, List<String> _bulletPoint,
                        String _openingHour, String _price, double lat, double lng, Context androidActivity) {

        Geocoder geocoder = new Geocoder(androidActivity);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        address = _address;
        dateFrom = _from;
        dateTo = _to;
        from = formatter.format(_from);
        to = formatter.format(_to);
        description = _description;
        image = "https://travelapp-backend.osc-fr1.scalingo.io/activities/" + _image;

        opening = _openingHour;
        price = _price;

        name = _name;
        id = _id;
        bulletPoint = _bulletPoint;

        coord = new Coord(lat,lng);
        try {
            List<Address> addresses = geocoder.getFromLocationName(this.address, 1);
            if (addresses.size() > 0) {
                this.coord = new Coord(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImage() {
        return image;
    }

    public CircleImageView getImageCircle(Context context) {
        CircleImageView imageProfile = new CircleImageView(context);
        Glide.with(context).load(getImage()).apply(MainActivity.glideOption).into(imageProfile);
        imageProfile.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("activity", TripActivity.this);
            context.startActivity(intent);
        });
        return imageProfile;
    }

    public static void findBydId(long id, Context c, Callable.CallableArgActitivy op){
        Scalingo.getInstance().getActivityDao().retrieve(id,
                activity -> op.operationCallableArgActitivy(activity.toActivity(c)),
                Throwable::printStackTrace);
    }

    public static void getALL(Context c, Callable.CallableArgActitivyList op){
        Scalingo.getInstance().getActivityDao().retrieveAll(
                listA ->{
                    ArrayList<TripActivity> allActivities = listA.stream().map(u -> u.toActivity(c)).sorted()
                            .collect(Collectors.toCollection(ArrayList::new));
                    op.operationCallableArgActitivyArrayList(allActivities);
                }, Throwable::printStackTrace
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TripActivity) {
            return id == (((TripActivity) o).id);
        }
        return false;
    }

    public Date getDateFrom(){
        return dateFrom;
    }

    public Date getDateTo(){
        return dateTo;
    }


    @Override
    public int compareTo(TripActivity t){
        return  dateFrom.compareTo(t.dateFrom);
    }
}
