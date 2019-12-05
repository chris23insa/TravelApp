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
}
