package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
public interface IMeetingService {

    List<Meeting> findByUsername(String username);

    List<Meeting> findAllActive();

    Meeting findById(long meetingId);

    List<Meeting> findByUserId(long userId);

    List<Meeting> findByUserIdActive(long userId);

    Meeting saveWithUsernameAsOwner(Meeting meeting, String ownerUsername);

    Meeting update(long meetingId, Meeting meeting);

    Meeting delete(long meetingId);

    Meeting addUser(long meetingId, MeetingUser meetingPerson);

    Meeting addUsers(long meetingId, List<MeetingUser> meetingPersons);

    void deleteUser(long meetingId, long userId);

    void deleteUsers(long meetingId, List<Long> usersIds);

}