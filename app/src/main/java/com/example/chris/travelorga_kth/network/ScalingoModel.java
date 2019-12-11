package com.example.chris.travelorga_kth.network;

import org.json.JSONException;
import org.json.JSONObject;

interface ScalingoModel {

    JSONObject jsonify() throws JSONException;

    void constructFromJson(JSONObject jsonObject) throws JSONException;
}
