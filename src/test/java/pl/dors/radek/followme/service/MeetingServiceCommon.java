package pl.dors.radek.followme.service;

import org.mockito.Mockito;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

/**
 * Created by rdors on 2016-10-26.
 */
public class MeetingServiceCommon {

    private static long id;

    public static void setUp(MeetingRepository meetingRepository, UserRepository userRepository, PlaceRepository placeRepository) throws Exception {
        Mockito.when(meetingRepository.findAll())
                .thenReturn(Arrays.asList(new Meeting("M1")));
        Mockito.when(placeRepository.save(any(Place.class)))
                .then(i -> {
                    Place place = i.getArgumentAt(0, Place.class);
                    if (place.getId() == null) {
                        place.setId(id++);
                    }
                    return i;
                });
        Mockito.when(userRepository.save(any(User.class)))
                .then(i -> {
                    User user = i.getArgumentAt(0, User.class);
                    if (user.getId() == null) {
                        user.setId(id++);
                    }
                    return i;
                });
        Mockito.when(meetingRepository.save(any(Meeting.class)))
                .then(i -> {
                    Meeting meeting = i.getArgumentAt(0, Meeting.class);
                    if (meeting.getId() == null) {
                        meeting.setId(id++);
                    }
                    return i;
                });
        Meeting m1 = new Meeting("M1");
        m1.setId(44L);
        Mockito.when(meetingRepository.findOne(anyLong()))
                .thenReturn(m1);
    }

    public static void findAll(MeetingService meetingService) throws Exception {
        assertThat(meetingService.findAll()).hasSize(1);
        assertThat(meetingService.findAll().get(0).getName()).isEqualTo("M1");
    }

    public static void save(MeetingService meetingService) throws Exception {
        Meeting meeting = new Meeting("M1");
        User user = new User("U1");
        Place place = new Place("P1", 1, 2);
        List<User> users = Arrays.asList(user);
        List<Place> places = Arrays.asList(place);
        meetingService.save(meeting, users, places);
        assertThat(meeting.getMeetingUsers()).hasSize(1);
        assertThat(meeting.getMeetingPlaces()).hasSize(1);
        assertThat(meeting.getMeetingUsers().get(0).getUser().getName()).isEqualTo("U1");
        assertThat(meeting.getMeetingPlaces().get(0).getPlace().getName()).isEqualTo("P1");
        assertThat(meeting.getMeetingUsers().get(0).getUser().getId()).isNotNull();
        assertThat(meeting.getMeetingPlaces().get(0).getPlace().getId()).isNotNull();
    }

    public static void addPlace(MeetingService meetingService) {
        Meeting meeting = new Meeting("M1");
        meeting.setId(44L);
        Place p1 = new Place("P1", 1, 2);
        meetingService.addPlace(meeting, p1);
        assertThat(meeting.getId()).isEqualTo(44L);
        assertThat(meeting.getMeetingPlaces()).hasSize(1);
        assertThat(meeting.getMeetingPlaces()).extractingResultOf("getPlace").extracting("name").contains("P1");
    }

    public static void addPlaces(MeetingService meetingService) {
        Meeting meeting = new Meeting("M1");
        meeting.setId(44L);
        Place p1 = new Place("P1", 1, 2);
        Place p2 = new Place("P2", 1, 2);
        List<Place> places = Arrays.asList(p1, p2);
        meetingService.addPlaces(meeting, places);
        assertThat(meeting.getId()).isEqualTo(44L);
        assertThat(meeting.getMeetingPlaces()).hasSize(2);
        assertThat(meeting.getMeetingPlaces()).extractingResultOf("getPlace").extracting("name").contains("P1", "P2");
    }

    public static void addUser(MeetingService meetingService) {
        Meeting meeting = new Meeting("M1");
        meeting.setId(44L);
        User u1 = new User("U2");
        meetingService.addUser(meeting, u1);
        assertThat(meeting.getId()).isEqualTo(44L);
        assertThat(meeting.getMeetingUsers()).hasSize(1);
        assertThat(meeting.getMeetingUsers()).extractingResultOf("getUser").extracting("name").contains("U2");
    }

    public static void addUsers(MeetingService meetingService) {
        Meeting meeting = new Meeting("M1");
        meeting.setId(44L);
        User u1 = new User("U1");
        User u2 = new User("U2");
        List<User> users = Arrays.asList(u1, u2);
        meetingService.addUsers(meeting, users);
        assertThat(meeting.getId()).isEqualTo(44L);
        assertThat(meeting.getMeetingUsers()).hasSize(2);
        assertThat(meeting.getMeetingUsers()).extractingResultOf("getUser").extracting("name").contains("U1", "U2");
    }
}
