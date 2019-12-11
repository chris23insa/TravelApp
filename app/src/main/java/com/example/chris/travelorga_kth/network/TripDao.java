package com.example.chris.travelorga_kth.network;

import java.util.List;

public abstract class TripDao extends GenericDao<TripModel, Long> {

    abstract public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                                final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback);

    abstract public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                              final ScalingoResponse.ErrorListener errorCallback);

    abstract public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback);


    /**
     * Retrieves all tripes
     */
    abstract public void retrieveAll(final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                     final ScalingoResponse.ErrorListener errorCallback);
}
