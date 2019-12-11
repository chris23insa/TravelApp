package com.example.chris.travelorga_kth.network;

import android.util.Log;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ActivityDaoImpl extends ActivityDao {

    @Override
    public void retrieveUserActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveUserActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback) {
        retrieveUserActivities(userId,successCallback, Throwable::printStackTrace);
    }


    @Override
    public void retrieveTripActivities(Long tripId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + tripId + activitiesEndpoint;
        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }


    @Override
    public void retrieveTripActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback) {
        retrieveTripActivities(userId,successCallback,u -> Log.d("aa","Error : " +u.toString()));
    }

    @Override
    public void retrieveFriendsActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback, ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveFriendsActivities(Long userId, ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback) {
        retrieveFriendsActivities(userId,successCallback,u -> Log.d("aa","Error : " +u.toString()));
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

    @Override
    public void retrieveAll(final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + activitiesEndpoint;

        listRequest(ActivityModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }


    @Override
    public void createTripActivity(Long tripId, Long activityId, String dateFrom, String dateTo, final ScalingoResponse.SuccessListener<ActivityModel> successCallback,
                                            final ScalingoResponse.ErrorListener errorCallback) throws ScalingoError{
        String URL = baseURL + tripsEndpoint + slash + tripId + activitiesEndpoint + slash + activityId;

        try {
            JSONObject dateParams = new JSONObject();
            dateParams.put("dateFrom", dateFrom);
            dateParams.put("dateTo", dateTo);
            oneRequest(ActivityModel.class, Request.Method.POST, URL, dateParams, successCallback, errorCallback);
        } catch(JSONException e) {
            throw new ScalingoError("Creation of a scheduled trip activity failed.", e);
        }
    }


    @Override public void deleteTripActivity(Long tripId, Long activityId, final ScalingoResponse.SuccessListener<ActivityModel> successCallback,
                                            final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + tripId + activitiesEndpoint + slash + activityId;

        oneRequest(ActivityModel.class, Request.Method.DELETE, URL, successCallback, errorCallback);
    }
}
