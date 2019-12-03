package com.example.chris.travelorga_kth.network;

public class ScalingoResponse {

    public interface SuccessListener<T> {
        void onResponse(T response);
    }

    public interface ErrorListener {
        void onError(ScalingoError error);
    }
}
