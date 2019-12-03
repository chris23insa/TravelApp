package com.example.chris.travelorga_kth.network;

public interface ActivityDao extends GenericDao<ActivityModel, Long> {
    /**
     * Find all activities related to the user identifier with userId
     * @param userId
     */
    void retrieveUserActivities(Long userId);

    /**
     * Find all activities related to the trip identified with tripId
     * @param tripId
     */
    void retrieveTripActivities(Long tripId);

    /**
     * Find all activities of all friends of the user identifier with userId
     * @param userId
     */
    void retrieveFriendsActivities(Long userId);
}
