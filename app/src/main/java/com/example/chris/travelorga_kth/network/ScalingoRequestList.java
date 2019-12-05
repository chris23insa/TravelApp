package com.example.chris.travelorga_kth.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScalingoRequestList<T extends ScalingoModel> extends JsonRequest<List<T>> {

    private String jwtToken;

    // The actual class must be known to be able to construct the object on runtime.
    private Class<T> clazz;

    /**
     * Creates a new request, with the goal to return a TripModel
     * @param method - HTTP method to use
     * @param url - URL to send the request
     * @param jsonRequest - A JSON object being the body parameters of the request
     * @param listener - The Listener to receive the TripModel response
     * @param errorListener - Error Listener
     */
    public ScalingoRequestList(Class<T> clazz, String jwtToken, int method, String url, JSONObject jsonRequest,
                               Listener<List<T>> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.jwtToken = jwtToken;
        this.clazz = clazz;
    }

    @Override
    protected Response<List<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject parsedJsonObject = new JSONObject(jsonString);
            JSONArray data = parsedJsonObject.getJSONArray("data");
            List<T> users = new ArrayList<>();
            for (int i = 0 ; i < data.length() ; ++i) {
                try {
                    T entity = null;
                    entity = convertJsonToEntity(data.getJSONObject(i));
                    users.add(entity);
                } catch(ScalingoError e) {
                    e.printStackTrace();
                }
            }
            return Response.success(users,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    /**
     * Override headers to add json token
     * @return
     */
    @Override
    public Map<String, String> getHeaders() {
        Map headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + jwtToken);
        return headers;
    }

    public JSONObject convertEntityToJson(T entity) throws JSONException{
        return entity.jsonify();
    }

    public T convertJsonToEntity(JSONObject json) throws JSONException, ScalingoError {
        T entity = getInstanceOfT();
        if (entity == null) {
            throw new ScalingoError("Impossible to instantiate the object");
        }
        entity.constructFromJson(json);
        return entity;
    }

    public T getInstanceOfT() {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
