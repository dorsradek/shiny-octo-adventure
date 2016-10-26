package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findAll();

    void save(Meeting meeting, List<User> users, List<Place> places);

    void addPlace(Meeting meeting, Place place);

    void addPlaces(Meeting meeting, List<Place> places);

    void addUser(Meeting meeting, User user);

    void addUsers(Meeting meeting, List<User> users);
}
