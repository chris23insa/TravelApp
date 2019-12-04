package com.example.chris.travelorga_kth.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T extends ScalingoModel, PK extends Serializable> {

    public static final String domain = "https://travelapp-backend.osc-fr1.scalingo.io";
    public static final String apiEndpoint = "/api/v2";
    public static final String baseURL = domain + apiEndpoint;

    public static final String authenticationEndpoint = "/authentication";
    public static final String usersEndpoint = "/users";
    public static final String tripsEndpoint = "/trips";
    public static final String activitiesEndpoint = "/activities";
    public static final String friendsEndpoint = "/friends";
    public static final String participantsEndpoint = "/participants";

    public static final String slash = "/";

    public static final String dataParam = "data";

    private String jwtToken;

    /** Save your object */
    /** The server should return the newly created entity */
    abstract public void create(T newInstance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback)
    throws ScalingoError;

    /** Retrieve the object thanks to its primary key */
    abstract public void retrieve(PK id, final ScalingoResponse.SuccessListener<T> successCallback,
                  final ScalingoResponse.ErrorListener errorCallback);

    /** Update an existing object */
    /** The server should return the updated entity */
    abstract public void update(T instance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback)
    throws ScalingoError;

    /** Remove an object */
    /** The server should return the old deleted entity */
    abstract public void delete(T instance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback);

    // -------------------------------------

    // Network Methods

    public void putJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void oneRequest(Class<T> clazz, int method, String endpoint,
                           final ScalingoResponse.SuccessListener<T> successCallback,
                           final ScalingoResponse.ErrorListener errorCallback) {
        oneRequest(clazz, method, endpoint, null, successCallback, errorCallback);
    }

    public void listRequest(Class<T> clazz, int method, String endpoint,
                            final ScalingoResponse.SuccessListener<List<T>> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        listRequest(clazz, method, endpoint, null, successCallback, errorCallback);
    }

    public void oneRequest(Class<T> clazz, int method, String endpoint, JSONObject bodyParams,
                           final ScalingoResponse.SuccessListener<T> successCallback,
                           final ScalingoResponse.ErrorListener errorCallback) {
        ScalingoRequest<T> request = new ScalingoRequest<T>(
                clazz,
                jwtToken,
                method,
                endpoint,
                bodyParams,
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        successCallback.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorCallback.onError(new ScalingoError(error));
                    }
                }
        );

        Scalingo.getInstance().addToRequestQueue(request);
    }

    public void listRequest(Class<T> clazz, int method, String endpoint, JSONObject bodyParams,
                            final ScalingoResponse.SuccessListener<List<T>> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        ScalingoRequestList<T> request = new ScalingoRequestList<T>(
                clazz,
                jwtToken,
                method,
                endpoint,
                bodyParams,
                new Response.Listener<List<T>>() {
                    @Override
                    public void onResponse(List<T> response) {
                        successCallback.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorCallback.onError(new ScalingoError(error));
                    }
                }
        );

        Scalingo.getInstance().addToRequestQueue(request);
    }

//    void getOneRequest(String endpoint, JSONObject bodyParams,
//                       final ScalingoResponse.SuccessListener<T> successCallback,
//                       final ScalingoResponse.ErrorListener errorCallback);
//
//    void getListRequest(String endpoint, JSONObject bodyParams,
//                       final ScalingoResponse.SuccessListener<List<T>> successCallback,
//                       final ScalingoResponse.ErrorListener errorCallback);
//
//    void postOneRequest(String endpoint, JSONObject bodyParams,
//                       final ScalingoResponse.SuccessListener<T> successCallback,
//                       final ScalingoResponse.ErrorListener errorCallback);
}
