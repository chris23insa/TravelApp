package com.example.chris.travelorga_kth.network;

import java.util.List;

public abstract class ActivityDao extends GenericDao<ActivityModel, Long> {
    /**
     * Find all activities related to the user identifier with userId
     * @param userId
     */
    abstract public void retrieveUserActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                         final ScalingoResponse.ErrorListener errorCallback);

    /**
     * Find all activities related to the trip identified with tripId
     * @param tripId
     */
    abstract public void retrieveTripActivities(Long tripId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                         final ScalingoResponse.ErrorListener errorCallback);

    /**
     * Find all activities of all friends of the user identifier with userId
     * @param userId
     */
    abstract public void retrieveFriendsActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                            final ScalingoResponse.ErrorListener errorCallback);

    /**
     * Retrieves all activities
     */
    abstract public void retrieveAll(final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                     final ScalingoResponse.ErrorListener errorCallback);
}
