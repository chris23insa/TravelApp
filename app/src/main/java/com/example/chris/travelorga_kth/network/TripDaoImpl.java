package com.example.chris.travelorga_kth.network;

public class TripDaoImpl extends TripDao {

    public static String jwtToken;

    @Override
    public void retrieveOrganizedTrips(Long userId) {

    }

    @Override
    public void retrieveFriendsTrips(Long tripId) {

    }

    @Override
    public void create(TripModel newInstance, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {

    }

    @Override
    public void retrieve(Long id, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {

    }

    @Override
    public void update(TripModel instance, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) throws ScalingoError {

    }

    @Override
    public void delete(TripModel instance, ScalingoResponse.SuccessListener<TripModel> successCallback, ScalingoResponse.ErrorListener errorCallback) {

    }

    @Override
    public void putJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
