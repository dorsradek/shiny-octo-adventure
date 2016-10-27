package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.*;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findAll();

    Meeting findOne(long id);

    List<Meeting> findByUser(User user);

    void save(Meeting meeting);

    void update(Meeting meeting);

    void delete(Meeting meeting);

    void updatePlaces(Meeting meeting);

    void addPlace(long meetingId, MeetingPlace meetingPlace);

    void addPlace(Meeting meeting, Place place);

    void addPlaces(Meeting meeting, List<Place> places);

    void removePlace(Meeting meeting, Place place);

    void removePlaces(Meeting meeting, List<Place> places);

    void addUser(long meetingId, MeetingUser meetingUser);

    void addUser(Meeting meeting, User user);

    void addUsers(Meeting meeting, List<User> users);

    void removeUser(Meeting meeting, User user);

    void removeUsers(Meeting meeting, List<User> users);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, double x, double y, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, double x, double y, boolean updateLastUpdate);
}