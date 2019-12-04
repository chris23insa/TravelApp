package com.example.chris.travelorga_kth.network;

import com.android.volley.VolleyError;

public class ScalingoError extends Exception {

    public ScalingoError(VolleyError error) {
        this(error.getMessage(), error);
    }
    public ScalingoError(String exceptionMessage) {
        super(exceptionMessage);
    }

    public ScalingoError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
    }
}
