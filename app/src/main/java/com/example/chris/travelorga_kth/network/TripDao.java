package com.example.chris.travelorga_kth.network;

import java.util.List;

public abstract class TripDao extends GenericDao<TripModel, Long> {
    /**
     * Find all trips owned by the user identified with userId
     * @param userId
     */
    abstract public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                                final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveOrganizedTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback);

    /**
     * Find all trips owned by the friends of the user identified with userId
     * @param userId
     */
    abstract public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback,
                                              final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveFriendsTrips(Long userId, final ScalingoResponse.SuccessListener<List<TripModel>> successCallback);
}
