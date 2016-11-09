package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPlace;
import pl.dors.radek.followme.model.MeetingPerson;
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
    private PersonRepository personRepository;
    private MeetingUserRepository meetingUserRepository;
    private MeetingPlaceRepository meetingPlaceRepository;

    public MeetingService(MeetingRepository meetingRepository, PlaceRepository placeRepository, PersonRepository personRepository, MeetingUserRepository meetingUserRepository, MeetingPlaceRepository meetingPlaceRepository) {
        this.meetingRepository = meetingRepository;
        this.placeRepository = placeRepository;
        this.personRepository = personRepository;
        this.meetingUserRepository = meetingUserRepository;
        this.meetingPlaceRepository = meetingPlaceRepository;
    }

    @Override
    public List<Meeting> findAll() {
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
        meeting.getMeetingPlaces().stream().forEach(meetingPlace -> meetingPlace.setMeeting(meeting));
        meeting.getMeetingPersons().stream().forEach(meetingUser -> meetingUser.setMeeting(meeting));
        meeting.getMeetingPlaces().stream().map(meetingPlace -> meetingPlace.getPlace()).forEach(placeRepository::save);
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
    public void addPlace(Optional<Long> meetingId, MeetingPlace meetingPlace) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        placeRepository.save(meetingPlace.getPlace());
        meetingPlace.setMeeting(meeting);
        meeting.getMeetingPlaces().add(meetingPlace);
        meetingRepository.save(meeting);
    }

    @Override
    public void addPlaces(Optional<Long> meetingId, List<MeetingPlace> meetingPlaces) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meetingPlaces.forEach(meetingPlace -> {
            placeRepository.save(meetingPlace.getPlace());
            meetingPlace.setMeeting(meeting);
            meeting.getMeetingPlaces().add(meetingPlace);
        });
        meetingRepository.save(meeting);
    }

    @Override
    public void updatePlace(Optional<Long> meetingId, Optional<Long> placeId, MeetingPlace meetingPlace) {
        MeetingPlace meetingPlaceDb = meetingPlaceRepository.findByMeetingIdAndPlaceId(
                meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")),
                placeId.orElseThrow(() -> new IllegalArgumentException("Place id can't be null"))
        ).orElseThrow(() -> new RuntimeException("MeetingPlace not found"));
        meetingPlaceDb.setOwner(meetingPlace.isOwner());
        meetingPlaceRepository.save(meetingPlaceDb);
    }

    @Override
    public void deletePlace(Optional<Long> meetingId, Optional<Long> placeId) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.getMeetingPlaces().stream()
                .filter(meetingPlace -> meetingPlace.getPlace().getId().equals(placeId.orElseThrow(() -> new IllegalArgumentException("Place id can't be null"))))
                .forEach(meetingPlaceRepository::delete);
    }

    @Override
    public void deletePlaces(Optional<Long> meetingId, List<Optional<Long>> placesIds) {
        Meeting meeting = meetingRepository.findById(meetingId.orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.getMeetingPlaces().stream()
                .filter(meetingPlace -> placesIds.stream().anyMatch(placeId -> meetingPlace.getPlace().getId().equals(placeId.orElseThrow(() -> new IllegalArgumentException("Place id can't be null")))))
                .forEach(meetingPlaceRepository::delete);
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

    @Override
    public void updatePlaces(Meeting meeting) {
        Meeting meetingDb = meetingRepository.findById(Optional.ofNullable(meeting.getId()).orElseThrow(() -> new IllegalArgumentException("Meeting id can't be null")))
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        //new places
        meeting.getMeetingPlaces().stream()
                .filter(meetingPlace -> meetingDb.getMeetingPlaces().stream()
                        .noneMatch(meetingPlace1 -> meetingPlace.getPlace().getId().equals(meetingPlace1.getPlace().getId())))
                .forEach(meetingPlace -> {
                    placeRepository.save(meetingPlace.getPlace());
                    meetingDb.getMeetingPlaces().add(meetingPlace);
                });
        //remove places
        meetingDb.getMeetingPlaces().stream()
                .filter(meetingPlace -> meeting.getMeetingPlaces().stream()
                        .noneMatch(meetingPlace1 -> meetingPlace.getPlace().getId().equals(meetingPlace1.getPlace().getId())))
                .forEach(meetingPlace -> {
                    meetingPlaceRepository.delete(meetingPlace);
                    meetingDb.getMeetingPlaces().remove(meetingPlace);
                });
        meetingRepository.save(meeting);
    }

}
