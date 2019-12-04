package com.example.chris.travelorga_kth.network;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
        } catch(JSONException e) {
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

        try { JSONObject jsonifiedUser = entity.jsonify();
            oneRequest(UserModel.class, Request.Method.PUT, URL, jsonifiedUser, successCallback, errorCallback);
        } catch(JSONException e) {
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

//    public void oneRequest(int method, String endpoint,
//                           final ScalingoResponse.SuccessListener<UserModel> successCallback,
//                           final ScalingoResponse.ErrorListener errorCallback) {
//        oneRequest(method, endpoint, null, successCallback, errorCallback);
//    }
//
//    public void listRequest(int method, String endpoint,
//                            final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
//                            final ScalingoResponse.ErrorListener errorCallback) {
//        listRequest(method, endpoint, null, successCallback, errorCallback);
//    }
//
//    public void oneRequest(int method, String endpoint, JSONObject bodyParams,
//                               final ScalingoResponse.SuccessListener<UserModel> successCallback,
//                               final ScalingoResponse.ErrorListener errorCallback) {
//        UserModelRequest request = new UserModelRequest(
//                jwtToken,
//                method,
//                endpoint,
//                bodyParams,
//                new Response.Listener<UserModel>() {
//                    @Override
//                    public void onResponse(UserModel response) {
//                        successCallback.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        errorCallback.onError(new ScalingoError(error));
//                    }
//                }
//        );
//
//        Scalingo.getInstance().addToRequestQueue(request);
//    }
//
//    public void listRequest(int method, String endpoint, JSONObject bodyParams,
//                           final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
//                           final ScalingoResponse.ErrorListener errorCallback) {
//        UserModelListRequest request = new UserModelListRequest(
//                jwtToken,
//                method,
//                endpoint,
//                bodyParams,
//                new Response.Listener<List<UserModel>>() {
//                    @Override
//                    public void onResponse(List<UserModel> response) {
//                        successCallback.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        errorCallback.onError(new ScalingoError(error));
//                    }
//                }
//        );
//
//        Scalingo.getInstance().addToRequestQueue(request);
//    }
}

// Example of call
//        retrieve(1L,
//                new ScalingoResponse.SuccessListener<UserModel>() {
//                    @Override
//                    public void onResponse(UserModel response) {
//                        // Do your business with your User
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                });

//
//    @Override
//    public void getOneRequest(String endpoint, JSONObject bodyParams,
//                              final ScalingoResponse.SuccessListener<UserModel> successCallback,
//                              final ScalingoResponse.ErrorListener errorCallback) {
//        UserModelRequest request = new UserModelRequest(
//                jwtToken,
//                Request.Method.GET,
//                endpoint,
//                bodyParams,
//                new Response.Listener<UserModel>() {
//                    @Override
//                    public void onResponse(UserModel response) {
//                        successCallback.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        errorCallback.onError(new ScalingoError(error));
//                    }
//                }
//        );
//
//        Scalingo.getInstance().addToRequestQueue(request);
//    }
//
//    @Override
//    public void getListRequest(String endpoint, JSONObject bodyParams,
//                               final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
//                               final ScalingoResponse.ErrorListener errorCallback) {
//        UserModelListRequest request = new UserModelListRequest(
//                jwtToken,
//                Request.Method.GET,
//                endpoint,
//                bodyParams,
//                new Response.Listener<List<UserModel>>() {
//                    @Override
//                    public void onResponse(List<UserModel> response) {
//                        successCallback.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        errorCallback.onError(new ScalingoError(error));
//                    }
//                }
//        );
//
//        Scalingo.getInstance().addToRequestQueue(request);
//    }
//
//    @Override
//    public void postOneRequest(String endpoint, JSONObject bodyParams,
//                               final ScalingoResponse.SuccessListener<UserModel> successCallback,
//                               final ScalingoResponse.ErrorListener errorCallback) {
//        UserModelRequest request = new UserModelRequest(
//                jwtToken,
//                Request.Method.POST,
//                endpoint,
//                bodyParams,
//                new Response.Listener<UserModel>() {
//                    @Override
//                    public void onResponse(UserModel response) {
//                        successCallback.onResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        errorCallback.onError(new ScalingoError(error));
//                    }
//                }
//        );
//
//        Scalingo.getInstance().addToRequestQueue(request);
//    }