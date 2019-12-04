package com.example.chris.travelorga_kth.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents the scalingo server !
 * This is a singleton, encapsulating the RequestQueue as it is explained at:
 * https://developer.android.com/training/volley/requestqueue
 *
 */
public class Scalingo {
    private static Scalingo instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private static final String baseURL = "https://travelapp-backend.osc-fr1.scalingo.io";
    private static final String authenticationEndpoint = "/authentication";
    private static final String dataParam = "data";

    private static final int REQUEST_TIMEOUT = 10; // seconds

    private final UserDao userDao;
    // TODO
//    public static final TripDao tripDao = new TripDaoImpl();
//    public static final ActivityDao activityDao = new ActivityDaoImpl();

    private String jwtToken;

    private Scalingo(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
        this.userDao = new UserDaoImpl();
    }

    public static void init(Context context) {
        instance = new Scalingo(context);
    }

    /** Should be initialized before */
    public static Scalingo getInstance() {
        return instance;
    }

//    public static synchronized Scalingo getInstance(Context context) {
//        if (instance == null) {
//            instance = new Scalingo(context);
//        }
//        return instance;
//    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    // TODO
//    public TripDao getTripDao() {
//        return tripDao;
//    }
//    public ActivityDao getActivityDao() {
//        return activityDao;
//    }

    private void putJwtTokenInDaos() {
        this.userDao.putJwtToken(jwtToken);
        // TODO
//        this.tripDao.putJwtToken(jwtToken);
//        this.activityDao.putJwtToken(jwtToken);
    }


    public void authenticate(String username, String password,
                               final ScalingoResponse.SuccessListener<JSONObject> successCallback,
                               final ScalingoResponse.ErrorListener errorCallback) {
        JSONObject authJsonBody = new JSONObject();
        try{
            authJsonBody.put("username", username);
            authJsonBody.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                baseURL + authenticationEndpoint,
                authJsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check for errors
                        // errors / dbErrors

                        Log.e("Response: ", response.toString());
                        try {
                            jwtToken = (String) response.get("token");
                            putJwtTokenInDaos();
                        } catch(JSONException e) {
                            Log.e("Error", e.getMessage());
                        }

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

//    public JSONObject authenticate(String username, String password, final Response.ErrorListener errorListener) {
//
//        JSONObject authJsonBody = new JSONObject();
//        try{
//            authJsonBody.put("username", username);
//            authJsonBody.put("password", password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//
//        JsonObjectRequest authRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                (baseURL + authenticationEndpoint),
//                authJsonBody,
//                future,
////                new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        // Check for errors
////                        // errors / dbErrors
////
////                        Log.e("Response: ", response.toString());
////                        try {
////                            jwtToken = (String) response.get("token");
////                            putJwtTokenInDaos();
////                        } catch(JSONException e) {
////                            Log.e("Error", e.getMessage());
////                        }
////                    }
////                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Response Error: ", error.toString());
//                    }
//                }
//        );
//
//        this.addToRequestQueue(authRequest);
//
//        try {
//            return future.get(REQUEST_TIMEOUT, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            Log.e("api call interrupted.", e.getMessage());
//            errorListener.onErrorResponse(new VolleyError(e));
//        } catch (ExecutionException e) {
//            Log.e("api call failed.", e.getMessage());
//            errorListener.onErrorResponse(new VolleyError(e));
//        } catch (TimeoutException e) {
////            Log.e("api call timed out.", e.getMessage());
//            errorListener.onErrorResponse(new VolleyError(e));
//        }
//        return null;
//    }

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
