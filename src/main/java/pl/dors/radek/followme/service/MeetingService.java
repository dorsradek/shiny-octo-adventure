package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.helper.MeetingHelper;
import pl.dors.radek.followme.model.*;
import pl.dors.radek.followme.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
@Transactional
public class MeetingService implements IMeetingService {

    private MeetingRepository meetingRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private MeetingUserRepository meetingUserRepository;
    private MeetingPlaceRepository meetingPlaceRepository;

    public MeetingService(MeetingRepository meetingRepository, PlaceRepository placeRepository, UserRepository userRepository, MeetingUserRepository meetingUserRepository) {
        this.meetingRepository = meetingRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.meetingUserRepository = meetingUserRepository;
    }

    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @Override
    public Meeting findOne(long id) {
        return Optional.ofNullable(meetingRepository.findOne(id)).orElseThrow(() -> new RuntimeException("Element not found"));
    }

    @Override
    public void save(Meeting meeting) {
        meeting.getMeetingPlaces().stream().forEach(meetingPlace -> meetingPlace.setMeeting(meeting));
        meeting.getMeetingUsers().stream().forEach(meetingUser -> meetingUser.setMeeting(meeting));
        meeting.getMeetingPlaces().stream().map(meetingPlace -> meetingPlace.getPlace()).forEach(placeRepository::save);
        meeting.getMeetingUsers().stream().map(meetingUser -> meetingUser.getUser()).forEach(userRepository::save);

        meetingRepository.save(meeting);
    }

    @Override
    public void addPlace(Meeting meeting, Place place) {
        meeting = meetingRepository.findOne(meeting.getId());
        placeRepository.save(place);
        createMeetingPlace(meeting, place);
        meetingRepository.save(meeting);
    }

    @Override
    public void addPlaces(Meeting meeting, List<Place> places) {
        meeting = meetingRepository.findOne(meeting.getId());
        places.forEach(placeRepository::save);
        createMeetingPlaces(meeting, places);
        meetingRepository.save(meeting);
    }

    @Override
    public void removePlace(Meeting meeting, Place place) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        findedMeeting.getMeetingPlaces().stream()
                .filter(meetingPlace -> meetingPlace.getPlace().getId().equals(place.getId()))
                .forEach(meetingPlaceRepository::delete);
    }

    @Override
    public void removePlaces(Meeting meeting, List<Place> places) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        findedMeeting.getMeetingPlaces().stream()
                .filter(meetingPlace -> places.stream().anyMatch(place -> place.getId().equals(meetingPlace.getPlace().getId())))
                .forEach(meetingPlaceRepository::delete);
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

    @Override
    public void removeUser(Meeting meeting, User user) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        findedMeeting.getMeetingUsers().stream()
                .filter(meetingUser -> meetingUser.getUser().getId().equals(user.getId()))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void removeUsers(Meeting meeting, List<User> users) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        findedMeeting.getMeetingUsers().stream()
                .filter(meetingUser -> users.stream().anyMatch(user -> user.getId().equals(meetingUser.getUser().getId())))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void updateUserData(Meeting meeting, User user, UserStatus userStatus, double x, double y, boolean updateLastUpdate) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        User findedUser = userRepository.findOne(user.getId());
        MeetingUser meetingUser = findedMeeting.getMeetingUsers().stream().filter(m -> m.getUser().getId().equals(findedUser.getId())).findAny().get();
        meetingUser.setUserStatus(userStatus);
        meetingUser.setX(x);
        meetingUser.setY(y);
        if (updateLastUpdate) {
            meetingUser.setLastUpdate(LocalDateTime.now());
        }
        meetingUserRepository.save(meetingUser);
    }

    @Override
    public void updateUserData(Meeting meeting, User user, UserStatus userStatus, boolean updateLastUpdate) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        User findedUser = userRepository.findOne(user.getId());
        MeetingUser meetingUser = findedMeeting.getMeetingUsers().stream().filter(m -> m.getUser().getId().equals(findedUser.getId())).findAny().get();
        meetingUser.setUserStatus(userStatus);
        if (updateLastUpdate) {
            meetingUser.setLastUpdate(LocalDateTime.now());
        }
        meetingUserRepository.save(meetingUser);
    }

    @Override
    public void updateUserData(Meeting meeting, User user, double x, double y, boolean updateLastUpdate) {
        Meeting findedMeeting = meetingRepository.findOne(meeting.getId());
        User findedUser = userRepository.findOne(user.getId());
        MeetingUser meetingUser = findedMeeting.getMeetingUsers().stream().filter(m -> m.getUser().getId().equals(findedUser.getId())).findAny().get();
        meetingUser.setX(x);
        meetingUser.setY(y);
        if (updateLastUpdate) {
            meetingUser.setLastUpdate(LocalDateTime.now());
        }
        meetingUserRepository.save(meetingUser);
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
