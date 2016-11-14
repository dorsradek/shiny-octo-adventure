package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPerson;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findAll(String username);

    List<Meeting> findAllActive();

    Meeting findOne(Optional<Long> meetingId);

    List<Meeting> findByUserId(Optional<Long> userId);

    List<Meeting> findByUserIdActive(Optional<Long> userId);

    void save(Meeting meeting);

    void update(Optional<Long> meetingId, Meeting meeting);

    void delete(Optional<Long> meetingId);

    void addUser(Optional<Long> meetingId, MeetingPerson meetingPerson);

    void addUsers(Optional<Long> meetingId, List<MeetingPerson> meetingPersons);

    void deleteUser(Optional<Long> meetingId, Optional<Long> userId);

    void deleteUsers(Optional<Long> meetingId, List<Optional<Long>> usersIds);

    void updateUser(Optional<Long> meetingId, Optional<Long> userId, MeetingPerson meetingPerson);

}