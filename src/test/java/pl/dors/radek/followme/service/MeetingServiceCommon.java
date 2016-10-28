package pl.dors.radek.followme.service;

import org.mockito.Mockito;
import pl.dors.radek.followme.model.*;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

import java.util.Arrays;

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

    public static void findAll(IMeetingService meetingService) throws Exception {
        assertThat(meetingService.findAll()).hasSize(1);
        assertThat(meetingService.findAll().get(0).getName()).isEqualTo("M1");
    }

    public static void save(IMeetingService meetingService) throws Exception {
        Meeting meeting = new Meeting("M1");
        User user = new User("U1");
        Place place = new Place("P1", 1, 2);
        MeetingPlace meetingPlace = new MeetingPlace();
        meetingPlace.setPlace(place);
        MeetingUser meetingUser = new MeetingUser();
        meetingUser.setUser(user);
        meeting.getMeetingPlaces().add(meetingPlace);
        meeting.getMeetingUsers().add(meetingUser);
        meetingService.save(meeting);
        assertThat(meeting.getMeetingUsers()).hasSize(1);
        assertThat(meeting.getMeetingPlaces()).hasSize(1);
        assertThat(meeting.getMeetingUsers().get(0).getUser().getName()).isEqualTo("U1");
        assertThat(meeting.getMeetingPlaces().get(0).getPlace().getName()).isEqualTo("P1");
        assertThat(meeting.getMeetingUsers().get(0).getUser().getId()).isNotNull();
        assertThat(meeting.getMeetingPlaces().get(0).getPlace().getId()).isNotNull();
    }

}
