package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findAll();

    List<Meeting> findAllActive();

    Meeting findOne(Optional<Long> meetingId);

    List<Meeting> findByUserId(Optional<Long> userId);

    List<Meeting> findByUserIdActive(Optional<Long> userId);

    void save(Meeting meeting);

    void update(Meeting meeting);

    void delete(Optional<Long> meetingId);

    void addPlace(Optional<Long> meetingId, MeetingPlace meetingPlace);

    void addPlaces(Optional<Long> meetingId, List<MeetingPlace> meetingPlaces);

    void deletePlace(Optional<Long> meetingId, Optional<Long> placeId);

    void deletePlaces(Optional<Long> meetingId, List<Optional<Long>> placesIds);

    void addUser(Optional<Long> meetingId, MeetingUser meetingUser);

    void addUsers(Optional<Long> meetingId, List<MeetingUser> meetingUsers);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, double x, double y, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, double x, double y, boolean updateLastUpdate);

    @Deprecated
    void removeUser(Meeting meeting, User user);

    @Deprecated
    void removeUsers(Meeting meeting, List<User> users);

    @Deprecated
    void updatePlaces(Meeting meeting);

    @Deprecated
    void removePlaces(Meeting meeting, List<Place> places);

    @Deprecated
    void addPlace(Meeting meeting, Place place);

    @Deprecated
    void addPlaces(Meeting meeting, List<Place> places);

    @Deprecated
    void deletePlace(Meeting meeting, Place place);

    @Deprecated
    void addUser(Meeting meeting, User user);

    @Deprecated
    void addUsers(Meeting meeting, List<User> users);
}