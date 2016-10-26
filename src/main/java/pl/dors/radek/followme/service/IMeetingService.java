package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.model.UserStatus;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findAll();

    void save(Meeting meeting, List<User> users, List<Place> places);

    void addPlace(Meeting meeting, Place place);

    void addPlaces(Meeting meeting, List<Place> places);

    void removePlace(Meeting meeting, Place place);

    void removePlaces(Meeting meeting, List<Place> places);

    void addUser(Meeting meeting, User user);

    void addUsers(Meeting meeting, List<User> users);

    void removeUser(Meeting meeting, User user);

    void removeUsers(Meeting meeting, List<User> users);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, double x, double y, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, UserStatus userStatus, boolean updateLastUpdate);

    void updateUserData(Meeting meeting, User user, double x, double y, boolean updateLastUpdate);
}