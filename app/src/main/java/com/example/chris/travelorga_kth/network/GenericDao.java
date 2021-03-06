package com.example.chris.travelorga_kth.network;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T extends ScalingoModel, PK extends Serializable> {

    private static final String domain = "https://travelapp-backend.osc-fr1.scalingo.io";
    private static final String apiEndpoint = "/api/v2";
    static final String baseURL = domain + apiEndpoint;

    public static final String authenticationEndpoint = "/authentication";
    static final String usersEndpoint = "/users";
    static final String tripsEndpoint = "/trips";
    static final String activitiesEndpoint = "/activities";
    static final String friendsEndpoint = "/friends";
    static final String participantsEndpoint = "/participants";

    static final String slash = "/";

    public static final String dataParam = "data";

    private String jwtToken;

    /** The server should return the newly created entity */
    abstract public void create(T newInstance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback)
    throws ScalingoError;

    /** Retrieve the object thanks to its primary key */
    abstract public void retrieve(PK id, final ScalingoResponse.SuccessListener<T> successCallback,
                  final ScalingoResponse.ErrorListener errorCallback);

    /** The server should return the updated entity */
    abstract public void update(T instance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback)
    throws ScalingoError;

    /** The server should return the old deleted entity */
    abstract public void delete(T instance, final ScalingoResponse.SuccessListener<T> successCallback,
                final ScalingoResponse.ErrorListener errorCallback);

    // -------------------------------------

    // Network Methods

    public void putJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    void oneRequest(Class<T> clazz, int method, String endpoint,
                    final ScalingoResponse.SuccessListener<T> successCallback,
                    final ScalingoResponse.ErrorListener errorCallback) {
        oneRequest(clazz, method, endpoint, null, successCallback, errorCallback);
    }

    void listRequest(Class<T> clazz, int method, String endpoint,
                     final ScalingoResponse.SuccessListener<List<T>> successCallback,
                     final ScalingoResponse.ErrorListener errorCallback) {
        listRequest(clazz, method, endpoint, null, successCallback, errorCallback);
    }

    void oneRequest(Class<T> clazz, int method, String endpoint, JSONObject bodyParams,
                    final ScalingoResponse.SuccessListener<T> successCallback,
                    final ScalingoResponse.ErrorListener errorCallback) {
        ScalingoRequest<T> request = new ScalingoRequest<>(
                clazz,
                jwtToken,
                method,
                endpoint,
                bodyParams,
                successCallback::onResponse,
                error -> errorCallback.onError(new ScalingoError(error))
        );

        Scalingo.getInstance().addToRequestQueue(request);
    }

    private void listRequest(Class<T> clazz, int method, String endpoint, JSONObject bodyParams,
                             final ScalingoResponse.SuccessListener<List<T>> successCallback,
                             final ScalingoResponse.ErrorListener errorCallback) {
        ScalingoRequestList<T> request = new ScalingoRequestList<>(
                clazz,
                jwtToken,
                method,
                endpoint,
                bodyParams,
                successCallback::onResponse,
                error -> errorCallback.onError(new ScalingoError(error))
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
