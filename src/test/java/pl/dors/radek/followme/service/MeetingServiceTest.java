package pl.dors.radek.followme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.UserStatus;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.impl.MeetingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

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

    //FIXME: to remove
    private static long id;

    Meeting meeting1;
    Meeting meeting2;
    Meeting meeting3;
    Meeting meeting4;
    private List<Meeting> meetings;

    User user1;
    User user2;
    User user3;
    private List<User> users;

    MeetingUser meetingUser2;
    MeetingUser meetingUser1;

    @Before
    public void setUp() throws Exception {
        meeting1 = new Meeting("Meeting1");
        meeting1.setId(1L);
        meeting2 = new Meeting("Meeting2");
        meeting2.setId(2L);
        meeting3 = new Meeting("Meeting3");
        meeting3.setId(3L);
        meeting3.setActive(true);
        meetings = Arrays.asList(meeting1, meeting2, meeting3);

        meeting4 = new Meeting("not-exists");
        meeting4.setId(4L);

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Stefan");
        user2 = new User();
        user2.setId(2L);
        user2.setUsername("ASDF");
        users = Arrays.asList(user1, user2);

        user3 = new User();
        user3.setId(3L);
        user3.setUsername("not-exists");

        meetingUser1 = new MeetingUser();
        meetingUser1.setUser(user1);
        meetingUser1.setMeeting(meeting1);
        meetingUser1.setOwner(true);
        meetingUser1.setUserStatus(UserStatus.ACTIVE);

        meetingUser2 = new MeetingUser();
        meetingUser2.setUser(user2);
        meetingUser2.setOwner(true);
        meetingUser2.setUserStatus(UserStatus.ACTIVE);
        meeting2.getMeetingUsers().add(meetingUser2);

        Place place1 = new Place();
        place1.setId(1L);
        place1.setName("Place1");
        place1.setX(11);
        place1.setY(22);
        meeting2.setPlace(place1);

        Mockito.when(meetingRepository.findByUsername(eq(user1.getUsername())))
                .thenReturn(Arrays.asList(meeting1));
        Mockito.when(meetingRepository.findByUsername(eq(user2.getUsername())))
                .thenReturn(new ArrayList<>());
        Mockito.when(meetingRepository.findByActiveTrue())
                .thenReturn(meetings.stream().filter(m -> m.isActive()).collect(Collectors.toList()));

        Mockito.when(meetingRepository.findById(eq(meeting1.getId())))
                .thenReturn(Optional.of(meeting1));
        Mockito.when(meetingRepository.findById(eq(meeting2.getId())))
                .thenReturn(Optional.of(meeting2));
        Mockito.when(meetingRepository.findById(eq(meeting3.getId())))
                .thenReturn(Optional.of(meeting3));
        Mockito.when(meetingRepository.findById(eq(meeting4.getId())))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(meetingRepository.findByUserId(user1.getId()))
                .thenReturn(Arrays.asList(meeting1, meeting2));
        Mockito.when(meetingRepository.findByUserId(user2.getId()))
                .thenReturn(new ArrayList<>());

        Mockito.when(meetingRepository.findByUserIdAndActiveTrue(user1.getId()))
                .thenReturn(Arrays.asList(meeting1, meeting2));
        Mockito.when(meetingRepository.findByUserIdAndActiveTrue(user2.getId()))
                .thenReturn(new ArrayList<>());

        Mockito.when(userRepository.findById(user1.getId()))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getId()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user3.getId()))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(userRepository.findByUsername(user1.getUsername()))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByUsername(user2.getUsername()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByUsername(user3.getUsername()))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(placeRepository.save(any(Place.class)))
                .then(i -> i.getArgumentAt(0, Place.class));
        Mockito.when(meetingRepository.save(any(Meeting.class)))
                .then(i -> i.getArgumentAt(0, Meeting.class));
    }

    @Test
    public void findByUsernameTest() throws Exception {
        List<Meeting> result = meetingService.findByUsername(user1.getUsername());
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting1);
    }

    @Test
    public void findByUsernameTest_Empty() throws Exception {
        List<Meeting> result = meetingService.findByUsername(user2.getUsername());
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllActiveTest() throws Exception {
        List<Meeting> result = meetingService.findAllActive();
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting3);
    }

    @Test
    public void findByIdTest() throws Exception {
        Meeting result = meetingService.findById(meeting1.getId());
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(meeting1);
    }

    @Test(expected = RuntimeException.class)
    public void findByIdTest_NotExists() throws Exception {
        meetingService.findById(meeting4.getId());
    }

    @Test
    public void findByUserIdTest() throws Exception {
        List<Meeting> result = meetingService.findByUserId(user1.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(meeting1, meeting2);
    }

    @Test
    public void findByUserIdTest_Empty() throws Exception {
        List<Meeting> result = meetingService.findByUserId(user2.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdActiveTest() throws Exception {
        List<Meeting> result = meetingService.findByUserIdActive(user1.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(meeting1, meeting2);
    }

    @Test
    public void findByUserIdActiveTest_Empty() throws Exception {
        List<Meeting> result = meetingService.findByUserIdActive(user2.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void saveWithUsernameAsOwnerTest() throws Exception {
        Meeting result = meetingService.saveWithUsernameAsOwner(meeting2, user1.getUsername());
        assertThat(result).isNotNull();
        assertThat(result.getPlace().getMeeting()).isEqualTo(meeting2);
        assertThat(result.getMeetingUsers()).hasSize(2);
        assertThat(result.getMeetingUsers()).contains(meetingUser2);
        MeetingUser meetingUser3 = new MeetingUser();
        meetingUser3.setMeeting(meeting2);
        meetingUser3.setUser(user1);
        assertThat(result.getMeetingUsers()).contains(meetingUser2, meetingUser3);
    }

//    @Test
//    public void save() throws Exception {
//        Meeting meeting = new Meeting("M1");
//        Place place = new Place("P1", 1, 2);
//        meeting.setPlace(place);
//        meetingService.saveWithUsernameAsOwner(meeting, "U111");
//        assertThat(meeting.getMeetingUsers()).hasSize(1);
//        assertThat(meeting.getMeetingUsers().get(0).getUser().getUsername()).isEqualTo("U111");
//        assertThat(meeting.getMeetingUsers().get(0).getUser().getId()).isNotNull();
//        assertThat(meeting.getPlace().getId()).isNotNull();
//    }

}