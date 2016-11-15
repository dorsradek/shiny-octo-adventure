package pl.dors.radek.followme.service;

import org.mockito.Mockito;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;

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
        Mockito.when(meetingRepository.save(any(Meeting.class)))
                .then(i -> {
                    Meeting meeting = i.getArgumentAt(0, Meeting.class);
                    if (meeting.getId() == null) {
                        meeting.setId(id++);
                    }
                    return i;
                });
        Mockito.when(userRepository.findById(anyLong()))
                .then(i -> {
                    Long userId = i.getArgumentAt(0, Long.class);
                    User user = new User();
                    user.setId(userId);
                    user.setUsername("U111");
                    return Optional.of(user);
                });
        Mockito.when(userRepository.findByUsername(anyString()))
                .then(i -> {
                    String username = i.getArgumentAt(0, String.class);
                    User user = new User();
                    user.setId(111L);
                    user.setUsername(username);
                    return Optional.of(user);
                });
        Meeting m1 = new Meeting("M1");
        m1.setId(44L);
        Mockito.when(meetingRepository.findOne(anyLong()))
                .thenReturn(m1);
    }

    public static void findAll(IMeetingService meetingService) throws Exception {
        assertThat(meetingService.findAll("U111")).hasSize(1);
        assertThat(meetingService.findAll("U111").get(0).getName()).isEqualTo("M1");
    }

    public static void save(IMeetingService meetingService) throws Exception {
        Meeting meeting = new Meeting("M1");
//        User user = new User();
//        user.setId(111L);
//        user.setUsername("U111");
//        MeetingUser meetingUser = new MeetingUser();
//        meetingUser.setUser(user);
//        meeting.getMeetingUsers().add(meetingUser);
        Place place = new Place("P1", 1, 2);
        meeting.setPlace(place);
        meetingService.save(meeting, "U111");
        assertThat(meeting.getMeetingUsers()).hasSize(1);
        assertThat(meeting.getMeetingUsers().get(0).getUser().getUsername()).isEqualTo("U111");
        assertThat(meeting.getMeetingUsers().get(0).getUser().getId()).isNotNull();
        assertThat(meeting.getPlace().getId()).isNotNull();
    }

}
