package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;
import pl.dors.radek.followme.model.security.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rdors on 2016-10-25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class MeetingUserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MeetingUserRepository meetingUserRepository;

    private List<Meeting> meetings;
    private List<User> users;

    @Before
    public void setUp() throws Exception {
        List<MeetingUser> meetingUsers = new ArrayList<>();
        meetings = Arrays.asList(
                new Meeting("meeting1"),
                new Meeting("meeting2"),
                new Meeting("meeting3")
        );

        User u1 = new User();
        u1.setUsername("User1");
        User u2 = new User();
        u2.setUsername("User2");
        User u3 = new User();
        u3.setUsername("User3");
        User u4 = new User();
        u4.setUsername("User4");
        users = Arrays.asList(u1, u2, u3, u4);
        users.forEach(entityManager::persist);

        MeetingUser meetingUser1 = new MeetingUser();
        meetingUser1.setMeeting(meetings.stream().filter(m -> m.getName().equals("meeting1")).findAny().get());
        meetingUser1.setUser(users.stream().filter(p -> p.getUsername().equals("User1")).findAny().get());
        meetingUser1.setOwner(true);
        meetingUsers.add(meetingUser1);
        meetings.stream().filter(m -> m.getName().equals("meeting1")).forEach(m -> m.getMeetingUsers().add(meetingUser1));

        MeetingUser meetingUser2 = new MeetingUser();
        meetingUser2.setMeeting(meetings.stream().filter(m -> m.getName().equals("meeting1")).findAny().get());
        meetingUser2.setUser(users.stream().filter(p -> p.getUsername().equals("User2")).findAny().get());
        meetingUser2.setOwner(false);
        meetingUsers.add(meetingUser2);
        meetings.stream().filter(m -> m.getName().equals("meeting1")).forEach(m -> m.getMeetingUsers().add(meetingUser2));

        MeetingUser meetingUser3 = new MeetingUser();
        meetingUser3.setMeeting(meetings.stream().filter(m -> m.getName().equals("meeting2")).findAny().get());
        meetingUser3.setUser(users.stream().filter(p -> p.getUsername().equals("User3")).findAny().get());
        meetingUser3.setOwner(false);
        meetingUsers.add(meetingUser3);
        meetings.stream().filter(m -> m.getName().equals("meeting2")).forEach(m -> m.getMeetingUsers().add(meetingUser3));

        meetings.forEach(entityManager::persist);
    }


    @Test
    public void findAllTest() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void findAllTest_Empty() throws Exception {
        meetings.forEach(entityManager::remove);

        List<MeetingUser> result = meetingUserRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByMeetingIdTest() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByMeetingId(meetings.stream().filter(m -> m.getName().equals("meeting2")).findAny().get().getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getUsername()).isEqualTo("User3");
    }

    @Test
    public void findByMeetingIdTest_NotExist() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByMeetingId(meetings.stream().mapToLong(meeting -> meeting.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByMeetingIdTest_MeetingExists_MeetingUserNotExists() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByMeetingId(meetings.stream().filter(m -> m.getName().equals("meeting3")).findAny().get().getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdTest() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByUserId(users.stream().filter(m -> m.getUsername().equals("User1")).findAny().get().getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getUsername()).isEqualTo("User1");
    }

    @Test
    public void findByUserIdTest_NotExist() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByUserId(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByMeetingIdTest_UserExists_MeetingUserNotExists() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByMeetingId(users.stream().filter(m -> m.getUsername().equals("User4")).findAny().get().getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByMeetingIdAndUserIdTest() throws Exception {
        Optional<MeetingUser> result = meetingUserRepository.findByMeetingIdAndUserId(
                meetings.stream().filter(m -> m.getName().equals("meeting1")).findAny().get().getId(),
                users.stream().filter(m -> m.getUsername().equals("User1")).findAny().get().getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getMeeting().getName()).isEqualTo("meeting1");
        assertThat(result.get().getUser().getUsername()).isEqualTo("User1");
    }

    @Test
    public void findByMeetingIdAndUserIdTest_MeetingUserNotExists() throws Exception {
        Optional<MeetingUser> result = meetingUserRepository.findByMeetingIdAndUserId(
                meetings.stream().filter(m -> m.getName().equals("meeting1")).findAny().get().getId(),
                users.stream().filter(m -> m.getUsername().equals("User3")).findAny().get().getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByMeetingIdAndUserIdTest_MeetingAndUserNotExist() throws Exception {
        Optional<MeetingUser> result = meetingUserRepository.findByMeetingIdAndUserId(
                meetings.stream().mapToLong(meeting -> meeting.getId()).sum(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByMeetingIdAndUserIdTest_MeetingNotExists() throws Exception {
        Optional<MeetingUser> result = meetingUserRepository.findByMeetingIdAndUserId(
                meetings.stream().mapToLong(meeting -> meeting.getId()).sum(),
                users.stream().filter(m -> m.getUsername().equals("User3")).findAny().get().getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByMeetingIdAndUserIdTest_UserNotExists() throws Exception {
        Optional<MeetingUser> result = meetingUserRepository.findByMeetingIdAndUserId(
                meetings.stream().filter(m -> m.getName().equals("meeting1")).findAny().get().getId(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }
}
