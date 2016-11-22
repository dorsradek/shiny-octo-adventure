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
import java.util.Optional;

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
    public void findByIdTest() throws Exception {
        Optional<Meeting> result = meetingRepository.findById(meetings.get(0).getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(meetings.get(0).getName());
    }

    @Test
    public void findByIdTest_NotExist() throws Exception {
        Optional<Meeting> result = meetingRepository.findById(meetings.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByIdTest_Null() throws Exception {
        Optional<Meeting> result = meetingRepository.findById(null);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findAllTest() throws Exception {
        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(meetings);
    }

    @Test
    public void findAllTest_Empty() throws Exception {
        meetings.forEach(entityManager::remove);

        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByActiveTrueTest() throws Exception {
        List<Meeting> result = meetingRepository.findByActiveTrue();

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting2);
    }

    @Test
    public void findByActiveTrueTest_Empty() throws Exception {
        entityManager.remove(meeting2);

        List<Meeting> result = meetingRepository.findByActiveTrue();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUsernameTest() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername("user1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMeetingUsers().get(0).getUser().getUsername()).isEqualTo("user1");
    }

    @Test
    public void findByUsernameTest_NotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername("user12");

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUsernameTest_Null() throws Exception {
        List<Meeting> result = meetingRepository.findByUsername(null);

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdTest() throws Exception {
        List<Meeting> result = meetingRepository.findByUserId(user1.getId());

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting1);
    }

    @Test
    public void findByUserIdTest_NotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUserId(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdTest_Null() throws Exception {
        List<Meeting> result = meetingRepository.findByUserId(null);

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdAndActiveTrueTest() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(user2.getId());

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(meeting2);
    }

    @Test
    public void findByUserIdAndActiveTrueTest_IdNotExist() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdAndActiveTrueTest_UserExistButInactive() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(user1.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findByUserIdAndActiveTrueTest_Null() throws Exception {
        List<Meeting> result = meetingRepository.findByUserIdAndActiveTrue(null);

        assertThat(result).isEmpty();
    }

}
