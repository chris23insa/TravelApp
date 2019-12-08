package com.example.chris.travelorga_kth.network;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TripDaoImpl extends TripDao {

    @Override
    public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                       final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + tripsEndpoint;

        listRequest(TripModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback) {
        retrieveOrganizedTrips(userId,successCallback, error -> Log.w("ERROR", error));
    }


    @Override
    public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                     final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint + tripsEndpoint;

        listRequest(TripModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }


    @Override
    public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback) {
        retrieveFriendsTrips(userId,successCallback,error -> Log.w("ERROR", error));
    }

    @Override
    public void create(TripModel newInstance, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        long ownerId = newInstance.getOwnerId();
        String URL = baseURL + usersEndpoint + slash + ownerId + tripsEndpoint;

        try {
            JSONObject jsonifiedTrip = newInstance.jsonify();
            oneRequest(TripModel.class, Request.Method.POST, URL, jsonifiedTrip, successCallback, errorCallback);
        } catch(JSONException e) {
            throw new ScalingoError("Creation of a new trip failed", e);
        }
    }

    @Override
    public void retrieve(Long id, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + id;

        oneRequest(TripModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void update(TripModel entity, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        long id = entity.getId();
        String URL = baseURL + tripsEndpoint + slash + id;

        try { JSONObject jsonifiedUser = entity.jsonify();
            oneRequest(TripModel.class, Request.Method.PUT, URL, jsonifiedUser, successCallback, errorCallback);
        } catch(JSONException e) {
            throw new ScalingoError("Update of a the user " + id + " failed", e);
        }
    }

    @Override
    public void delete(TripModel entity, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        long id = entity.getId();
        String URL = baseURL + tripsEndpoint + slash + id;

        oneRequest(TripModel.class, Request.Method.DELETE, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveAll(final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint;

        listRequest(TripModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }
}
