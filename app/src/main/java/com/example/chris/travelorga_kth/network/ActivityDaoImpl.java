package com.example.chris.travelorga_kth.network;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ActivityDaoImpl extends ActivityDao {

    @Override
    void retrieveUserActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    void retrieveTripActivities(Long tripId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + tripId + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    void retrieveFriendsActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void create(ActivityModel newInstance, ScalingoResponse.SuccessListener<ActivityModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        long tripId = newInstance.getTripId();
        String URL = baseURL + tripsEndpoint + slash + tripId + activitiesEndpoint;

        try {
            JSONObject jsonifiedTrip = newInstance.jsonify();
            oneRequest(ActivityModel.class, Request.Method.POST, URL, jsonifiedTrip, successCallback, errorCallback);
        } catch(JSONException e) {
            throw new ScalingoError("Creation of a new activity failed", e);
        }
    }

    @Override
    public void retrieve(Long id, ScalingoResponse.SuccessListener<ActivityModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + activitiesEndpoint + slash + id;

        oneRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void update(ActivityModel entity, ScalingoResponse.SuccessListener<ActivityModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        long id = entity.getId();
        String URL = baseURL + activitiesEndpoint + slash + id;

        try { JSONObject jsonifiedUser = entity.jsonify();
            oneRequest(ActivityModel.class, Request.Method.PUT, URL, jsonifiedUser, successCallback, errorCallback);
        } catch(JSONException e) {
            throw new ScalingoError("Update of the activity " + id + " failed", e);
        }
    }

    @Override
    public void delete(ActivityModel entity, ScalingoResponse.SuccessListener<ActivityModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        long id = entity.getId();
        String URL = baseURL + activitiesEndpoint + slash + id;

        oneRequest(ActivityModel.class, Request.Method.DELETE, URL, successCallback, errorCallback);
    }
}
