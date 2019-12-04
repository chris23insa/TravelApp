package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel implements ScalingoModel {
    private long id;
    private String mail;
    private String pseudonyme;
    private String firstName;
    private String lastName;
    private String description;
    private String tel;
    private String pictureUrl;
    private String password;
    private double latitude;
    private double longitude;
    private boolean active;

    public UserModel() {

    }

    public UserModel(JSONObject jsonObject) throws JSONException{
        this.constructFromJson(jsonObject);
    }

    @Override
    public JSONObject jsonify() throws JSONException {
        JSONObject jsonified = new JSONObject();
        jsonified.put("id", id);
        jsonified.put("mail", mail);
        jsonified.put("pseudonyme", pseudonyme);
        jsonified.put("firstName".toLowerCase(), firstName);
        jsonified.put("lastName".toLowerCase(), lastName);
        jsonified.put("description", description);
        jsonified.put("tel", tel);
        jsonified.put("pictureUrl".toLowerCase(), pictureUrl);
        jsonified.put("password", password);
        jsonified.put("latitude", latitude);
        jsonified.put("longitude", longitude);
        jsonified.put("active", active);
        return jsonified;
    }

    @Override
    public void constructFromJson(JSONObject json) throws JSONException {
         this.id = json.getLong("id");
         this.mail = json.getString("mail");
         this.pseudonyme = json.getString("pseudonyme");
         this.firstName = json.getString("firstName".toLowerCase());
         this.lastName = json.getString("lastName".toLowerCase());
         this.description = json.getString("description");
         this.tel = json.getString("tel");
         this.pictureUrl = json.getString("pictureUrl".toLowerCase());
//         this.password = json.getString("password");
         this.latitude = json.getDouble("latitude");
         this.longitude = json.getDouble("longitude");
         this.active = json.getBoolean("active");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserModel{" + "\n" +
                "id=" + id + "\n" +
                ", mail='" + mail + '\'' + "\n" +
                ", pseudonyme='" + pseudonyme + '\'' + "\n" +
                ", firstName='" + firstName + '\'' + "\n" +
                ", lastName='" + lastName + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", tel='" + tel + '\'' + "\n" +
                ", pictureUrl='" + pictureUrl + '\'' + "\n" +
                ", password='" + password + '\'' + "\n" +
                ", latitude=" + latitude + "\n" +
                ", longitude=" + longitude + "\n" +
                ", active=" + active + "\n" +
                '}';
    }
}
