package com.example.chris.travelorga_kth.network;

public abstract class ActivityDao extends GenericDao<ActivityModel, Long> {
    /**
     * Find all activities related to the user identifier with userId
     * @param userId
     */
    abstract void retrieveUserActivities(Long userId);

    /**
     * Find all activities related to the trip identified with tripId
     * @param tripId
     */
    abstract void retrieveTripActivities(Long tripId);

    /**
     * Find all activities of all friends of the user identifier with userId
     * @param userId
     */
    abstract void retrieveFriendsActivities(Long userId);
}
