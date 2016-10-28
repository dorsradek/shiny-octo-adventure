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
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.specification.MeetingUserSpecification;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private List<User> users;
    private List<Meeting> meetings;
    private List<MeetingUser> meetingUsers;

    @Before
    public void setUp() throws Exception {
        meetingUsers = new ArrayList<>();
        meetings = Arrays.asList(
                new Meeting("m1"),
                new Meeting("m2")
        );

        users = Arrays.asList(
                new User("User1"),
                new User("User2"),
                new User("User3")
        );
        users.forEach(entityManager::persist);

        MeetingUser meetingUser1 = new MeetingUser();
        meetingUser1.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingUser1.setUser(users.stream().filter(p -> p.getName().equals("User1")).findAny().get());
        meetingUser1.setOwner(true);
        meetingUsers.add(meetingUser1);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingUsers().add(meetingUser1));

        MeetingUser meetingUser2 = new MeetingUser();
        meetingUser2.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingUser2.setUser(users.stream().filter(p -> p.getName().equals("User2")).findAny().get());
        meetingUser2.setOwner(false);
        meetingUsers.add(meetingUser2);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingUsers().add(meetingUser2));

        MeetingUser meetingUser3 = new MeetingUser();
        meetingUser3.setMeeting(meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get());
        meetingUser3.setUser(users.stream().filter(p -> p.getName().equals("User3")).findAny().get());
        meetingUser3.setOwner(false);
        meetingUsers.add(meetingUser3);
        meetings.stream().filter(m -> m.getName().equals("m2")).forEach(m -> m.getMeetingUsers().add(meetingUser3));

        meetings.forEach(entityManager::persist);
    }


    @Test
    public void findAll() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void findByMeeting() throws Exception {
        List<MeetingUser> result = meetingUserRepository.findByMeetingId(
                meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get().getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getName()).isEqualTo("User3");
    }
}
