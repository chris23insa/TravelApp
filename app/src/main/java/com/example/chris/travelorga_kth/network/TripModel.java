package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

    public TripModel() {

    }

    public TripModel(JSONObject jsonObject) throws JSONException{
        this.constructFromJson(jsonObject);
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
        jsonified.put("dateFrom".toLowerCase(), dateFrom);
        jsonified.put("dateTo".toLowerCase(), dateTo);
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
        this.budget = json.getDouble("budget");
        this.preferences = json.getString("preferences");
        this.latitude = json.getDouble("latitude");
        this.longitude = json.getDouble("longitude");
        this.dateFrom =  new Date(json.getString("dateFrom".toLowerCase()));
        this.dateTo = new Date(json.getString("dateFrom".toLowerCase()));
        this.created = new Date(json.getString("created"));
    }

//    @Override
//    public String getCreateEndpoint() {
//        return generateEndpoint();
//    }
//
//    @Override
//    public String getRetrieveEndpoint(long entityId) {
//        return generateEndpoint() + "/" + entityId;
//    }
//
//    @Override
//    public String getUpdateEndpoint(long entityId) {
//        return generateEndpoint() + "/" + entityId;
//    }
//
//    @Override
//    public String getDeleteEndpoint(long entityId) {
//        return generateEndpoint() + "/" + entityId;
//    }
//
//    /** Helper for replacing :userId */
//    private String generateEndpoint() {
//        return endpoint.replace(":userId", Long.toString(this.ownerId));
//    }

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
}
