package com.example.chris.travelorga_kth.network;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserDaoImpl extends UserDao {

    @Override
    public void createFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                             final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint + slash + friendId;

        oneRequest(UserModel.class, Request.Method.POST, URL, successCallback, errorCallback);
    }

    @Override
    public void createTripParticipant(Long tripId, Long participantId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                                      final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + tripId + participantsEndpoint + slash + participantId;

        oneRequest(UserModel.class, Request.Method.POST, URL, successCallback, errorCallback);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                             final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint + slash + friendId;

        oneRequest(UserModel.class, Request.Method.DELETE, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveAll(final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                            final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint;

        listRequest(UserModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveFriends(Long userId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                                final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + userId + friendsEndpoint;

        listRequest(UserModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void retrieveTripParticipants(Long tripId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                                         final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + tripsEndpoint + slash + tripId + participantsEndpoint;

        listRequest(UserModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void create(UserModel newInstance, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                       final ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        String URL = baseURL + usersEndpoint;

        try {
            JSONObject jsonifiedUser = newInstance.jsonify();
            oneRequest(UserModel.class, Request.Method.POST, URL, jsonifiedUser, successCallback, errorCallback);
        } catch (JSONException e) {
            throw new ScalingoError("Creation of a new user failed", e);
        }
    }

    @Override
    public void retrieve(Long id, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                         final ScalingoResponse.ErrorListener errorCallback) {
        String URL = baseURL + usersEndpoint + slash + id;

        oneRequest(UserModel.class, Request.Method.GET, URL, successCallback, errorCallback);
    }

    @Override
    public void update(UserModel entity, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                       final ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {
        Long id = entity.getId();
        String URL = baseURL + usersEndpoint + slash + id;

        try {
            JSONObject jsonifiedUser = entity.jsonify();
            oneRequest(UserModel.class, Request.Method.PUT, URL, jsonifiedUser, successCallback, errorCallback);
        } catch (JSONException e) {
            throw new ScalingoError("Update of a the user " + id + " failed", e);
        }
    }

    @Override
    public void delete(UserModel entity, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                       final ScalingoResponse.ErrorListener errorCallback) {
        Long id = entity.getId();
        String URL = baseURL + usersEndpoint + slash + id;

        oneRequest(UserModel.class, Request.Method.DELETE, URL, successCallback, errorCallback);
    }
}