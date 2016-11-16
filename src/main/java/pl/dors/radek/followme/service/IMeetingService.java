package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findByUsername(String username);

    List<Meeting> findAllActive();

    Meeting findOne(Optional<Long> meetingId);

    List<Meeting> findByUserId(Optional<Long> userId);

    List<Meeting> findByUserIdActive(Optional<Long> userId);

    void saveWithUsernameAsOwner(Meeting meeting, String ownerUsername);

    void update(Optional<Long> meetingId, Meeting meeting);

    void delete(Optional<Long> meetingId);

    void addUser(Optional<Long> meetingId, MeetingUser meetingPerson);

    void addUsers(Optional<Long> meetingId, List<MeetingUser> meetingPersons);

    void deleteUser(Optional<Long> meetingId, Optional<Long> userId);

    void deleteUsers(Optional<Long> meetingId, List<Optional<Long>> usersIds);

}