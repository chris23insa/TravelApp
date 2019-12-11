package com.example.chris.travelorga_kth.network;

import android.content.Context;

import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

public class TripModel implements ScalingoModel {
    private long id;
    private long ownerId;
    private String name;
    private String pictureUrl;
    private String description;
    private double budget;
    private String preferences;
    private double latitude;
    private double longitude;
    private Date dateFrom;
    private Date dateTo;
    private Date created;
    private String place;

    public TripModel() {

    }

    public TripModel(long ownerId, String name,String place, String pictureUrl, String description,
                     double budget, Preference preferences, double latitude, double longitude, Date dateFrom, Date dateTo) {
        this.id = new Random().nextLong();
        this.ownerId = ownerId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.description = description;
        this.budget = budget;
        this.preferences = preferences.toString();
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.place = place;
        created = new Date();
    }

    public TripModel(JSONObject jsonObject) throws JSONException{
        this.constructFromJson(jsonObject);
    }

    public Trip toTrip(Context c){
        //TODO manage enum
        return new Trip(id,name,place,pictureUrl,dateFrom,dateTo,description,(int)budget, Preference.BAR,latitude,longitude,ownerId,created,c);
    }

    public String getPlace() {
        return place;
    }

    @Override
    public JSONObject jsonify() throws JSONException {
        JSONObject jsonified = new JSONObject();
        jsonified.put("id", id);
        jsonified.put("ownerId".toLowerCase(), ownerId);
        jsonified.put("name", name);
        jsonified.put("pictureUrl".toLowerCase(), pictureUrl);
        jsonified.put("description", description);
        jsonified.put("budget", budget);
        jsonified.put("preferences", preferences);
        jsonified.put("latitude", latitude);
        jsonified.put("longitude", longitude);
        jsonified.put("dateFrom".toLowerCase(), DateUtil.fromDateToString(dateFrom));
        jsonified.put("dateTo".toLowerCase(), DateUtil.fromDateToString(dateTo));
        jsonified.put("created", created);
        return jsonified;
    }

    @Override
    public void constructFromJson(JSONObject json) throws JSONException {
        this.id = json.getLong("id");
        this.ownerId = json.getLong("ownerId".toLowerCase());
        this.name = json.getString("name");
        this.pictureUrl = json.getString("pictureUrl".toLowerCase());
        this.description = json.getString("description");
        this.preferences = json.getString("preferences");
        try {
            this.latitude = json.getDouble("latitude");
            this.longitude = json.getDouble("longitude");
            this.budget = json.getDouble("budget");
        }
        catch(JSONException e) {
            this.latitude = 0;
            this.longitude = 0;
        }
        try {
//            this.dateFrom =  new Date(json.getString("dateFrom".toLowerCase()));
//            this.dateTo = new Date(json.getString("dateFrom".toLowerCase()));
//            this.created = new Date(json.getString("created"));
            this.dateFrom = DateUtil.fromStringToDate(json.getString("dateFrom".toLowerCase()));
            this.dateTo = DateUtil.fromStringToDate(json.getString("dateTo".toLowerCase()));
            this.created = DateUtil.fromStringToDate(json.getString("created".toLowerCase()));
        }
        catch(IllegalArgumentException | ParseException e) {
            this.dateFrom =  new Date();
            this.dateTo = new Date();
            this.created = new Date();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "TripModel{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", preferences='" + preferences + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", created=" + created +
                '}';
    }

    public String toStringDeluxe() {
        return "TripModel{" +"\n" +
                "id=" + id +"\n" +
                ", ownerId=" + ownerId +"\n" +
                ", name='" + name + '\'' +"\n" +
                ", pictureUrl='" + pictureUrl + '\'' +"\n" +
                ", description='" + description + '\'' +"\n" +
                ", budget=" + budget +"\n" +
                ", preferences='" + preferences + '\'' +"\n" +
                ", latitude=" + latitude +"\n" +
                ", longitude=" + longitude +"\n" +
                ", dateFrom=" + dateFrom +"\n" +
                ", dateTo=" + dateTo +"\n" +
                ", created=" + created +"\n" +
                '}';
    }
}
