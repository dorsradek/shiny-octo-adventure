package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.helper.MeetingHelper;
import pl.dors.radek.followme.model.*;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
@Transactional
public class MeetingService implements IMeetingService {

    private MeetingRepository meetingRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;

    public MeetingService(MeetingRepository meetingRepository, PlaceRepository placeRepository, UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @Override
    public void save(Meeting meeting, List<User> users, List<Place> places) {
        places.forEach(placeRepository::save);
        users.forEach(userRepository::save);

        createMeetingPlaces(meeting, places);
        createMeetingUsers(meeting, users);

        meetingRepository.save(meeting);
    }

    @Override
    public void addPlace(Meeting meeting, Place place) {
        MeetingHelper.copyWithoutIdAndCollections(meetingRepository.findOne(meeting.getId()), meeting);
        placeRepository.save(place);
        createMeetingPlace(meeting, place);
        meetingRepository.save(meeting);
    }

    @Override
    public void addPlaces(Meeting meeting, List<Place> places) {
        MeetingHelper.copyWithoutIdAndCollections(meetingRepository.findOne(meeting.getId()), meeting);
        places.forEach(placeRepository::save);
        createMeetingPlaces(meeting, places);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUser(Meeting meeting, User user) {
        MeetingHelper.copyWithoutIdAndCollections(meetingRepository.findOne(meeting.getId()), meeting);
        userRepository.save(user);
        createMeetingUser(meeting, user);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUsers(Meeting meeting, List<User> users) {
        MeetingHelper.copyWithoutIdAndCollections(meetingRepository.findOne(meeting.getId()), meeting);
        users.forEach(userRepository::save);
        createMeetingUsers(meeting, users);
        meetingRepository.save(meeting);
    }

    private void createMeetingUsers(Meeting meeting, List<User> users) {
        users.forEach(u -> {
            createMeetingUser(meeting, u);
        });
    }

    private MeetingUser createMeetingUser(Meeting meeting, User user) {
        MeetingUser meetingUser = new MeetingUser();
        meetingUser.setMeeting(meeting);
        meetingUser.setUser(user);
        meetingUser.setOwner(false);
        meeting.getMeetingUsers().add(meetingUser);
        return meetingUser;
    }

    private void createMeetingPlaces(Meeting meeting, List<Place> places) {
        places.forEach(p -> {
            createMeetingPlace(meeting, p);
        });
    }

    private MeetingPlace createMeetingPlace(Meeting meeting, Place place) {
        MeetingPlace meetingPlace = new MeetingPlace();
        meetingPlace.setMeeting(meeting);
        meetingPlace.setPlace(place);
        meetingPlace.setOwner(false);
        meeting.getMeetingPlaces().add(meetingPlace);
        return meetingPlace;
    }

}
