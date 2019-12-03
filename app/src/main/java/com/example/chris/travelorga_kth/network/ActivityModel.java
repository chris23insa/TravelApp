package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ActivityModel implements ScalingoModel {
    private long id;
    private long tripId;
    private long tripOwnerId;

    private String name;
    private String description;
    private String webpageUrl;
    private String pictureUrl;
    private String pricing;
    private String openingTime;

    private double latitude;
    private double longitude;
    private Date dateFrom;
    private Date dateTo;
    private Date created;

    public static final String endpoint = "/users/:userId/trips/:tripId/activities";

    public ActivityModel() {

    }

    @Override
    public JSONObject jsonify() throws JSONException {
        JSONObject jsonified = new JSONObject();
        jsonified.put("id", id);
        jsonified.put("tripId", tripId);
        jsonified.put("tripOwnerId", tripOwnerId);

        jsonified.put("name", name);
        jsonified.put("webpageUrl", webpageUrl);
        jsonified.put("pictureUrl", pictureUrl);
        jsonified.put("description", description);
        jsonified.put("pricing", pricing);
        jsonified.put("openingTime", openingTime);

        jsonified.put("latitude", latitude);
        jsonified.put("longitude", longitude);
        jsonified.put("dateFrom", dateFrom);
        jsonified.put("dateTo", dateTo);
        jsonified.put("created", created);
        return jsonified;
    }

    @Override
    public void constructFromJson(JSONObject json) throws JSONException {
        this.id = json.getLong("id");
        this.tripId = json.getLong("tripId");
        this.tripOwnerId  = json.getLong("tripOwnerId");

        this.name = json.getString("name");
        this.pictureUrl = json.getString("pictureUrl");
        this.description = json.getString("description");
        this.webpageUrl = json.getString("webpageUrl");
        this.pricing = json.getString("pricing");
        this.openingTime = json.getString("openingTime");

        this.latitude = json.getDouble("latitude");
        this.longitude = json.getDouble("longitude");
        this.dateFrom =  new Date(json.getString("dateFrom"));
        this.dateTo = new Date(json.getString("dateFrom"));
        this.created = new Date(json.getString("created"));
    }

    @Override
    public String getCreateEndpoint() {
        return generateEndpoint();
    }

    @Override
    public String getRetrieveEndpoint(long entityId) {
        return generateEndpoint() + "/" + entityId;
    }

    @Override
    public String getUpdateEndpoint(long entityId) {
        return generateEndpoint() + "/" + entityId;
    }

    @Override
    public String getDeleteEndpoint(long entityId) {
        return generateEndpoint() + "/" + entityId;
    }

    /** Helper for replacing :userId  and :tripId */
    private String generateEndpoint() {
        return endpoint.replace(":userId", Long.toString(this.tripOwnerId))
                .replace(":tripId", Long.toString(this.tripId));
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
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
}