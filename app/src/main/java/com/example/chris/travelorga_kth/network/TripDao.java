package com.example.chris.travelorga_kth.network;

public interface TripDao extends GenericDao<TripModel, Long> {
    /**
     * Find all trips owned by the user identified with userId
     * @param userId
     */
    void retrieveOrganizedTrips(Long userId);

    /**
     * Find all trips owned by the friends of the user identified with userId
     * @param tripId
     */
    void retrieveFriendsTrips(Long tripId);
}
