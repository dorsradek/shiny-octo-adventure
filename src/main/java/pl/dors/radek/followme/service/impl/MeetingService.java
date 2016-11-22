package pl.dors.radek.followme.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.MeetingUserRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.IMeetingService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<Meeting> findByUsername(String username) {
        return meetingRepository.findByUsername(username);
    }

    @Override
    public List<Meeting> findAllActive() {
        return meetingRepository.findByActiveTrue();
    }

    @Override
    public Meeting findById(long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
    }

    @Override
    public List<Meeting> findByUserId(long userId) {
        return meetingRepository.findByUserId(userId);
    }

    @Override
    public List<Meeting> findByUserIdActive(long userId) {
        return meetingRepository.findByUserIdAndActiveTrue(userId);
    }

    @Override
    public void saveWithUsernameAsOwner(Meeting meeting, String ownerUsername) {
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
    public void update(long meetingId, Meeting meeting) {
        Meeting meetingDb = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingDb.setName(meeting.getName());
        meetingDb.setLastUpdate(LocalDateTime.now());
        meetingDb.setActive(true);
        meetingRepository.save(meetingDb);
    }

    @Override
    public void delete(long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setLastUpdate(LocalDateTime.now());
        meeting.setActive(false);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUser(long meetingId, MeetingUser meetingUser) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        userRepository.findById(meetingUser.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        meetingUser.setMeeting(meeting);
        meeting.getMeetingUsers().add(meetingUser);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUsers(long meetingId, List<MeetingUser> meetingUsers) {
        Meeting meeting = meetingRepository.findById(meetingId)
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
    public void deleteUser(long meetingId, long userId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        meeting.getMeetingUsers().stream()
                .filter(meetingUser -> meetingUser.getUser().getId().equals(userId))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void deleteUsers(long meetingId, List<Long> usersIds) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        usersIds.stream().forEach(userId -> {
            userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        });
        meeting.getMeetingUsers().stream()
                .filter(meetingUser -> usersIds.stream().anyMatch(placeId -> meetingUser.getUser().getId().equals(placeId)))
                .forEach(meetingUserRepository::delete);
    }

}
