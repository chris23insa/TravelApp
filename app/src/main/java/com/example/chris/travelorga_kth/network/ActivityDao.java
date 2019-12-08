package com.example.chris.travelorga_kth.network;

import java.util.List;

public abstract class ActivityDao extends GenericDao<ActivityModel, Long> {

    abstract public void retrieveUserActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                         final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveUserActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback);
    abstract public void retrieveTripActivities(Long tripId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                         final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveTripActivities(Long tripId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback);
    abstract public void retrieveFriendsActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                            final ScalingoResponse.ErrorListener errorCallback);

    abstract public void retrieveFriendsActivities(Long userId, final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback);


    /**
     * Retrieves all activities
     */
    abstract public void retrieveAll(final ScalingoResponse.SuccessListener<List<ActivityModel>> successCallback,
                                     final ScalingoResponse.ErrorListener errorCallback);

    /**
     * Links an existing activity to an existing trip
     *
     * Needs dateFrom and dateTo in the following format:
     * 2019-12-08T14:01+08 (timezone +8 hours)
     * will become
     * 2019-12-08T06:01:00.000Z in the DB (in UTC 0)
     *
     *
     * yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * 2017-07-01T14:59:55.711'+0000'
     * 2017-07-01T14:59:55.711Z
     */
    abstract public void createTripActivity(Long tripId, Long activityId, String dateFrom, String dateTo, final ScalingoResponse.SuccessListener<ActivityModel> successCallback,
                                   final ScalingoResponse.ErrorListener errorCallback) throws ScalingoError;

    /**
     * Delete a scheduled activity in a trip
     */
    abstract public void deleteTripActivity(Long tripId, Long activityId, final ScalingoResponse.SuccessListener<ActivityModel> successCallback,
                                            final ScalingoResponse.ErrorListener errorCallback);
}
