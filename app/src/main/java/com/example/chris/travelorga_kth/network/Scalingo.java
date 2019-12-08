package com.example.chris.travelorga_kth.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final TripDao tripDao = new TripDaoImpl();
    private static final ActivityDao activityDao = new ActivityDaoImpl();

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

    private RequestQueue getRequestQueue() {
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

    public TripDao getTripDao() {
        return tripDao;
    }
    public ActivityDao getActivityDao() {
        return activityDao;
    }

    private void putJwtTokenInDaos() {
        this.userDao.putJwtToken(jwtToken);
        tripDao.putJwtToken(jwtToken);
        activityDao.putJwtToken(jwtToken);
    }

    public void authenticate(String username, String password,
                               final ScalingoResponse.SuccessListener<JSONObject> successCallback,
                               final ScalingoResponse.ErrorListener errorCallback)  {
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
                response -> {

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
                },
                error -> Log.w("AUTH ERROR","SCALINGO AUTH ERROR")
        );

        Scalingo.getInstance().addToRequestQueue(request);
    }
}
