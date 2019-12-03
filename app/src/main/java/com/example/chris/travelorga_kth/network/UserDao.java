package com.example.chris.travelorga_kth.network;

import java.util.List;

public interface UserDao extends GenericDao<UserModel, Long> {

    void createFriend(Long userId, Long friendId);

    void createTripParticipants(Long tripId, List<Long> friendIds);

    void deleteFriend(Long userId, Long friendId);

    /**
     * Retrieves all users
     */
    void retrieveFriends();

    /**
     * Retrieves all friends of the user identified with userId
     * @param userId
     */
    void retrieveFriends(Long userId);

    /**
     * Retrieves all participants of a trip identified with tripId
     * @param tripId
     */
    void retrieveTripParticipants(Long tripId);


}
