package pl.dors.radek.followme.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.MeetingUserRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

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

    private static Logger logger = Logger.getLogger(MeetingService.class);

    private MeetingRepository meetingRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private MeetingUserRepository meetingUserRepository;

    public MeetingService(MeetingRepository meetingRepository, PlaceRepository placeRepository, UserRepository userRepository, MeetingUserRepository meetingUserRepository) {
        this.meetingRepository = meetingRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.meetingUserRepository = meetingUserRepository;
    }

    @Override
    public List<Meeting> findAll(String username) {
        //TODO: find user by username
        logger.info("findAll username: " + username);
        return meetingRepository.findAll();
    }

    @Override
    public List<Meeting> findAllActive() {
        return meetingRepository.findByActiveTrue();
    }

    @Override
    public Meeting findOne(Optional<Long> meetingId) {
        return meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
    }

    @Override
    public List<Meeting> findByUserId(Optional<Long> userId) {
        return meetingRepository.findByUserId(userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null")));
    }

    @Override
    public List<Meeting> findByUserIdActive(Optional<Long> userId) {
        return meetingRepository.findByUserIdAndActiveTrue(userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null")));
    }

    @Override
    public void save(Meeting meeting, String ownerUsername) {
        User user = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        MeetingUser ownerMeetingUser = new MeetingUser();
        ownerMeetingUser.setOwner(true);
        ownerMeetingUser.setUser(user);
        meeting.getMeetingUsers().add(ownerMeetingUser);

        meeting.getPlace().setMeeting(meeting);
        meeting.getMeetingUsers().stream().forEach(meetingUser -> {
            userRepository.findById(meetingUser.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            meetingUser.setMeeting(meeting);
        });
        placeRepository.save(meeting.getPlace());

        meeting.setLastUpdate(LocalDateTime.now());
        meeting.setActive(true);
        meetingRepository.save(meeting);
    }

    @Override
    public void update(Optional<Long> meetingId, Meeting meeting) {
        Meeting meetingDb = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingDb.setName(meeting.getName());
        meetingDb.setLastUpdate(LocalDateTime.now());
        meetingDb.setActive(true);
        meetingRepository.save(meetingDb);
    }

    @Override
    public void delete(Optional<Long> meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setLastUpdate(LocalDateTime.now());
        meeting.setActive(false);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUser(Optional<Long> meetingId, MeetingUser meetingUser) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        userRepository.findById(meetingUser.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        meetingUser.setMeeting(meeting);
        meeting.getMeetingUsers().add(meetingUser);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUsers(Optional<Long> meetingId, List<MeetingUser> meetingUsers) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingUsers.stream().forEach(meetingUser -> {
            userRepository.findById(meetingUser.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            meetingUser.setMeeting(meeting);
            meeting.getMeetingUsers().add(meetingUser);
        });
        meetingRepository.save(meeting);
    }

    @Override
    public void deleteUser(Optional<Long> meetingId, Optional<Long> userId) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        userRepository.findById(userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null")))
                .orElseThrow(() -> new RuntimeException("User not found"));
        meeting.getMeetingUsers().stream()
                .filter(meetingUser -> meetingUser.getUser().getId().equals(userId.get()))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void deleteUsers(Optional<Long> meetingId, List<Optional<Long>> usersIds) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        usersIds.stream().forEach(userId -> {
            userRepository.findById(userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null")))
                    .orElseThrow(() -> new RuntimeException("User not found"));
        });
        meeting.getMeetingUsers().stream()
                .filter(meetingUser -> usersIds.stream().anyMatch(placeId -> meetingUser.getUser().getId().equals(placeId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null")))))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void updateUser(Optional<Long> meetingId, Optional<Long> userId, MeetingUser meetingUser) {
        MeetingUser meetingUserDb = meetingUserRepository.findByMeetingIdAndUserId(
                meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")),
                userId.orElseThrow(() -> new IllegalArgumentException("User id can't be null"))
        ).orElseThrow(() -> new RuntimeException("MeetingUser not found"));
        meetingUserDb.setX(meetingUser.getX());
        meetingUserDb.setY(meetingUser.getY());
        meetingUserDb.setLastUpdate(LocalDateTime.now());
        meetingUserRepository.save(meetingUserDb);
    }

}
