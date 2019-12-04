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

public class TripModelListRequest extends JsonRequest<List<TripModel>> {

    private String jwtToken;

    /**
     * Creates a new request, with the goal to return a TripModel
     * @param method - HTTP method to use
     * @param url - URL to send the request
     * @param jsonRequest - A JSON object being the body parameters of the request
     * @param listener - The Listener to receive the TripModel response
     * @param errorListener - Error Listener
     */
    public TripModelListRequest(String jwtToken, int method, String url, JSONObject jsonRequest,
                                Listener<List<TripModel>> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.jwtToken = jwtToken;
    }

    @Override
    protected Response<List<TripModel>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject parsedJsonObject = new JSONObject(jsonString);
            JSONArray data = parsedJsonObject.getJSONArray("data");
            List<TripModel> trips = new ArrayList<>();
            for (int i = 0 ; i < data.length() ; ++i) {
                trips.add(new TripModel(data.getJSONObject(i)));
            }
            return Response.success(trips,
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
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + jwtToken);
        return headers;
    }
}
