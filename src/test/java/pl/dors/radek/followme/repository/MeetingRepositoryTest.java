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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by rdors on 2016-10-24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class MeetingRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MeetingRepository meetingRepository;

    private List<Meeting> meetings;

    @Before
    public void setUp() throws Exception {
        User u1 = new User();
        u1.setUsername("user1");
        User u2 = new User();
        u2.setUsername("user2");

        List<User> users = Arrays.asList(u1, u2);
        users.forEach(entityManager::persist);

        MeetingUser mu1 = new MeetingUser();
        mu1.setUser(u1);
        mu1.setOwner(false);
        MeetingUser mu2 = new MeetingUser();
        mu2.setUser(u2);
        mu2.setOwner(false);
        MeetingUser mu3 = new MeetingUser();
        mu3.setUser(u2);
        mu3.setOwner(false);

        Meeting m1 = new Meeting();
        m1.setName("m111");
        m1.getMeetingUsers().add(mu1);
        m1.getMeetingUsers().add(mu2);

        mu1.setMeeting(m1);
        mu2.setMeeting(m1);

        Meeting m2 = new Meeting();
        m2.setName("m222");
        m2.getMeetingUsers().add(mu3);

        mu3.setMeeting(m2);

        meetings = Arrays.asList(m1, m2);
        meetings.forEach(entityManager::persist);
    }

    @Test
    public void findByUsername() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername("user1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMeetingUsers().get(0).getUser().getUsername()).isEqualTo("user1");
    }

    @Test
    public void findAll() throws Exception {
        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(meetings);
    }

}
