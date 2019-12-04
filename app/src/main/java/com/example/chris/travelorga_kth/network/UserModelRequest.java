package com.example.chris.travelorga_kth.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserModelRequest extends JsonRequest<UserModel> {

    private String jwtToken;

    /**
     * Creates a new request, with the goal to return a UserModel
     * @param method - HTTP method to use
     * @param url - URL to send the request
     * @param jsonRequest - A JSON object being the body parameters of the request
     * @param listener - The Listener to receive the UserModel response
     * @param errorListener - Error Listener
     */
    public UserModelRequest(String jwtToken, int method, String url, JSONObject jsonRequest,
                             Listener<UserModel> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.jwtToken = jwtToken;
    }

    @Override
    protected Response<UserModel> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject parsedJsonObject = new JSONObject(jsonString);
            JSONObject data = parsedJsonObject.getJSONObject("data");
            return Response.success(new UserModel(data),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * Override headers to add json token
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + jwtToken);
        // DEBUG
//        headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYWlsIjoibW91c3RpY0BtYWlsLmNvbSIsImlkIjo0MCwiaWF0IjoxNTc1NDYzMDUyLCJleHAiOjE1NzU1NDk0NTJ9.9VgPHayyS8pv-9QC2kYh-eeuhNP-MaSxPK6hY-gEakc");
        return headers;
    }
}
