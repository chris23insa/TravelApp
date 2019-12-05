package com.example.chris.travelorga_kth.network;

import java.util.List;

public abstract class UserDao extends GenericDao<UserModel, Long> {

    abstract public void createFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                      final ScalingoResponse.ErrorListener errorCallback);

    abstract public void createTripParticipant(Long tripId, Long participantId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                               final ScalingoResponse.ErrorListener errorCallback);

    abstract public void deleteFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback,
                      final ScalingoResponse.ErrorListener errorCallback);


    abstract public void createFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback);

    abstract public void createTripParticipant(Long tripId, Long participantId, final ScalingoResponse.SuccessListener<UserModel> successCallback);

    abstract public void deleteFriend(Long userId, Long friendId, final ScalingoResponse.SuccessListener<UserModel> successCallback);

    /**
     * Retrieves all users
     */
    abstract public void retrieveAll(final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                     final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveAll(final ScalingoResponse.SuccessListener<List<UserModel>> successCallback);

    /**
     * Retrieves all friends of the user identified with userId
     * @param userId
     */
    abstract public void retrieveFriends(Long userId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                         final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveFriends(Long userId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback);

    /**
     * Retrieves all participants of a trip identified with tripId
     * @param tripId
     */
    abstract public void retrieveTripParticipants(Long tripId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback,
                                  final ScalingoResponse.ErrorListener errorCallback);
    abstract public void retrieveTripParticipants(Long tripId, final ScalingoResponse.SuccessListener<List<UserModel>> successCallback);


}
