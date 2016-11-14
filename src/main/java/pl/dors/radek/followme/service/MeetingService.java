package pl.dors.radek.followme.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPerson;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.MeetingUserRepository;
import pl.dors.radek.followme.repository.PersonRepository;
import pl.dors.radek.followme.repository.PlaceRepository;

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
    private PersonRepository personRepository;
    private MeetingUserRepository meetingUserRepository;

    public MeetingService(MeetingRepository meetingRepository, PlaceRepository placeRepository, PersonRepository personRepository, MeetingUserRepository meetingUserRepository) {
        this.meetingRepository = meetingRepository;
        this.placeRepository = placeRepository;
        this.personRepository = personRepository;
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
        return meetingRepository.findByUserId(userId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null")));
    }

    @Override
    public List<Meeting> findByUserIdActive(Optional<Long> userId) {
        return meetingRepository.findByUserIdAndActiveTrue(userId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null")));
    }

    @Override
    public void save(Meeting meeting) {
        meeting.getPlace().setMeeting(meeting);
        meeting.getMeetingPersons().stream().forEach(meetingUser -> meetingUser.setMeeting(meeting));
        placeRepository.save(meeting.getPlace());
        meeting.getMeetingPersons().stream().map(meetingUser -> meetingUser.getUser()).forEach(personRepository::save);

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
    public void addUser(Optional<Long> meetingId, MeetingPerson meetingPerson) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        personRepository.save(meetingPerson.getUser());
        meetingPerson.setMeeting(meeting);
        meeting.getMeetingPersons().add(meetingPerson);
        meetingRepository.save(meeting);
    }

    @Override
    public void addUsers(Optional<Long> meetingId, List<MeetingPerson> meetingPersons) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingPersons.forEach(meetingUser -> {
            personRepository.save(meetingUser.getUser());
            meetingUser.setMeeting(meeting);
            meeting.getMeetingPersons().add(meetingUser);
        });
        meetingRepository.save(meeting);
    }

    @Override
    public void deleteUser(Optional<Long> meetingId, Optional<Long> userId) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.getMeetingPersons().stream()
                .filter(meetingUser -> meetingUser.getUser().getId().equals(userId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null"))))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void deleteUsers(Optional<Long> meetingId, List<Optional<Long>> usersIds) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.getMeetingPersons().stream()
                .filter(meetingUser -> usersIds.stream().anyMatch(placeId -> meetingUser.getUser().getId().equals(placeId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null")))))
                .forEach(meetingUserRepository::delete);
    }

    @Override
    public void updateUser(Optional<Long> meetingId, Optional<Long> userId, MeetingPerson meetingPerson) {
        MeetingPerson meetingPersonDb = meetingUserRepository.findByMeetingIdAndUserId(
                meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")),
                userId.orElseThrow(() -> new IllegalArgumentException("Person id can't be null"))
        ).orElseThrow(() -> new RuntimeException("MeetingPerson not found"));
        meetingPersonDb.setX(meetingPerson.getX());
        meetingPersonDb.setY(meetingPerson.getY());
        meetingPersonDb.setLastUpdate(LocalDateTime.now());
        meetingUserRepository.save(meetingPersonDb);
    }

}
