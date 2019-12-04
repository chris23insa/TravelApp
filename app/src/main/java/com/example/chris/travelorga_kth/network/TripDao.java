package com.example.chris.travelorga_kth.network;

public abstract class TripDao extends GenericDao<TripModel, Long> {
    /**
     * Find all trips owned by the user identified with userId
     * @param userId
     */
    abstract void retrieveOrganizedTrips(Long userId);

    /**
     * Find all trips owned by the friends of the user identified with userId
     * @param tripId
     */
    abstract void retrieveFriendsTrips(Long tripId);
}
