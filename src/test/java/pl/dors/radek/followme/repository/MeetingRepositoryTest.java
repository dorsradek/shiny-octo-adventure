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
    private List<User> users;

    Meeting meeting1;
    Meeting meeting2;
    User user1;
    User user2;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setUsername("user1");
        user2 = new User();
        user2.setUsername("user2");

        users = Arrays.asList(user1, user2);
        users.forEach(entityManager::persist);

        MeetingUser mu1 = new MeetingUser();
        mu1.setUser(user1);
        mu1.setOwner(false);
        MeetingUser mu2 = new MeetingUser();
        mu2.setUser(user2);
        mu2.setOwner(false);
        MeetingUser mu3 = new MeetingUser();
        mu3.setUser(user2);
        mu3.setOwner(false);

        meeting1 = new Meeting();
        meeting1.setName("m111");
        meeting1.getMeetingUsers().add(mu1);
        meeting1.getMeetingUsers().add(mu2);

        mu1.setMeeting(meeting1);
        mu2.setMeeting(meeting1);

        meeting2 = new Meeting();
        meeting2.setName("m222");
        meeting2.getMeetingUsers().add(mu3);
        meeting2.setActive(true);

        mu3.setMeeting(meeting2);

        meetings = Arrays.asList(meeting1, meeting2);
        meetings.forEach(entityManager::persist);
    }

    @Test
    public void findAll() throws Exception {
        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(meetings);
    }

    @Test
    public void findAll_Empty() throws Exception {
        meetings.forEach(entityManager::remove);

        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByActiveTrue() throws Exception {
        List<Meeting> result = meetingRepository.findByActiveTrue();

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting2);
    }

    @Test
    public void findByActiveTrue_Empty() throws Exception {
        entityManager.remove(meeting2);

        List<Meeting> result = meetingRepository.findByActiveTrue();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUsername() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername("user1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMeetingUsers().get(0).getUser().getUsername()).isEqualTo("user1");
    }

    @Test
    public void findByUsername_NotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername("user12");

        assertThat(result).isEmpty();
    }

    @Test
    public void findById() throws Exception {
        List<Meeting> result = meetingRepository.findByUserId(user1.getId());

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting1);
    }

    @Test
    public void findById_NotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUserId(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdAndActiveTrue() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(user2.getId());

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting2);
    }

    @Test
    public void findByUserIdAndActiveTrue_IdNotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdAndActiveTrue_UserExistButInactive() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(user1.getId());

        assertThat(result).isEmpty();
    }

}
