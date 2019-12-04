package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface ScalingoModel {
    JSONObject jsonify() throws JSONException;

//    static <T> void constructFromJson(JSONObject jsonObject, T truc) throws JSONException {}
//    <T> void constructFromJson(JSONObject jsonObject, T truc) throws JSONException;
    void constructFromJson(JSONObject jsonObject) throws JSONException;
//
    // TODOCreate Endpoint actually depends on the entity.
    // Cannot be so easily refactored
    // Other option : Changing the routes to put the param inside the body
    // And not inside the URL
//    String getCreateEndpoint();
//
//    String getRetrieveEndpoint(long entityId);
//    String getUpdateEndpoint(long entityId);
//    String getDeleteEndpoint(long entityId);

}
