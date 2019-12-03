package com.example.chris.travelorga_kth.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.URL;

/**
 * Represents the scalingo server !
 * This is a singleton, encapsulating the RequestQueue as it is explained at:
 * https://developer.android.com/training/volley/requestqueue
 *
 */
public class Scalingo {
    private static Scalingo instance;

    private static final String baseURL = "https://travelapp-backend.osc-fr1.scalingo.io";
    private static final String authenticationEndpoint = "/authentication";
    private static final String apiEndpoint = "/api";
    private static final String dataParam = "data";

    private String jwtToken;

    private Scalingo(Context context) {

    }

    public void authenticate(String username, String password) {
        JSONObject authJsonBody = new JSONObject();
        try{
            authJsonBody.put("username", username);
            authJsonBody.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest authRequest = new JsonObjectRequest(
                Request.Method.POST,
                (baseURL + authenticationEndpoint),
                authJsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check for errors
                        // errors / dbErrors

                        Log.e("Response: ", response.toString());
//                        this.jwtToken = (String) response.get("token");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response Error: ", error.toString());
                    }
                }
        );


    }

    /**
     * Send a request without body params
     * @param method
     * @param endpoint
     */
//    public void sendRequest(ScalingoMethod method, String endpoint) {
////        sendRequest(method, endpoint, new JSONObject());
//    }

    public <T> void sendRequest(ScalingoMethod method, String endpoint, JSONObject bodyParams,
                            final ScalingoResponse.SuccessListener<T> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(
                toVolleyMethod(method),
                URL,
                bodyParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        T truc = new UserModel();
//                        successCallback.onResponse(truc);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorCallback.onError(new ScalingoError(error.getMessage()));
                    }
                }
        );
    }

    /**
     * Converts ScalingoMethod (POST, GET, PUT, DELETE)
     * to a VolleyMethod
     * @param method
     * @return
     */
    private int toVolleyMethod(ScalingoMethod method) {
        int volleyMethod;
        switch(method) {
            case GET:
                volleyMethod = Request.Method.GET;
            break;
            case POST:
                volleyMethod = Request.Method.POST;
            break;
            case PUT:
                volleyMethod = Request.Method.PUT;
            break;
            case DELETE:
                volleyMethod = Request.Method.DELETE;
            break;
            default:
                volleyMethod = Request.Method.GET;
                break;
        }
        return volleyMethod;
    }
}
