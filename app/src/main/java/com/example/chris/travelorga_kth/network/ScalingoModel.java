package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface ScalingoModel {
    JSONObject jsonify() throws JSONException;

    void constructFromJson(JSONObject jsonObject) throws JSONException;

    String getCreateEndpoint();
    String getRetrieveEndpoint(long entityId);
    String getUpdateEndpoint(long entityId);
    String getDeleteEndpoint(long entityId);

}
