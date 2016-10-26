package pl.dors.radek.followme.service.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.MeetingService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

/**
 * Created by rdors on 2016-10-26.
 */
@RunWith(SpringRunner.class)
public class MeetingServiceTest {

    @InjectMocks
    private MeetingService meetingService;

    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private UserRepository userRepository;

    private static long id;

    @Before
    public void setUp() throws Exception {
        Mockito.when(this.meetingRepository.findAll())
                .thenReturn(Arrays.asList(new Meeting("M1")));
        Mockito.when(this.placeRepository.save(any(Place.class)))
                .then(i -> {
                    Place place = i.getArgumentAt(0, Place.class);
                    place.setId(id++);
                    return i;
                });
        Mockito.when(this.userRepository.save(any(User.class)))
                .then(i -> {
                    User user = i.getArgumentAt(0, User.class);
                    user.setId(id++);
                    return i;
                });
        Mockito.when(this.meetingRepository.save(any(Meeting.class)))
                .then(i -> {
                    Meeting meeting = i.getArgumentAt(0, Meeting.class);
                    meeting.setId(id++);
                    return i;
                });
    }

    @Test
    public void findAll() throws Exception {
        assertThat(meetingService.findAll()).hasSize(1);
        assertThat(meetingService.findAll().get(0).getName()).isEqualTo("M1");
    }

    @Test
    public void save() throws Exception {
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

    @Test
    public void addPlaces() throws Exception {

    }

    @Test
    public void addUsers() throws Exception {

    }

}