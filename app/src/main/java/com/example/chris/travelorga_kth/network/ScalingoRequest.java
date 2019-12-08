package com.example.chris.travelorga_kth.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

class ScalingoRequest<T extends ScalingoModel> extends JsonRequest<T> {

    private final String jwtToken;

    // The actual class must be known to be able to construct the object on runtime.
    private final Class<T> clazz;

    /**
     * Creates a new request, with the goal to return a TripModel
     * @param method - HTTP method to use
     * @param url - URL to send the request
     * @param jsonRequest - A JSON object being the body parameters of the request
     * @param listener - The Listener to receive the TripModel response
     * @param errorListener - Error Listener
     */
    public ScalingoRequest(Class<T> clazz, String jwtToken, int method, String url, JSONObject jsonRequest,
                            Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.jwtToken = jwtToken;
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject parsedJsonObject = new JSONObject(jsonString);
            JSONObject data = parsedJsonObject.getJSONObject("data");
            T entity = null;
            try {
                entity = convertJsonToEntity(data);
            } catch(ScalingoError e) {
                e.printStackTrace();
            }
            return Response.success(entity,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }


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

    private T convertJsonToEntity(JSONObject json) throws JSONException, ScalingoError {
        T entity = getInstanceOfT();
        if (entity == null) {
            throw new ScalingoError("Impossible to instantiate the object");
        }
        entity.constructFromJson(json);
        return entity;
    }

    private T getInstanceOfT() {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
