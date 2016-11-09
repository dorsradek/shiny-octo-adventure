package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPerson;
import pl.dors.radek.followme.model.Person;

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
public class MeetingPersonRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MeetingUserRepository meetingUserRepository;

    private List<Person> persons;
    private List<Meeting> meetings;
    private List<MeetingPerson> meetingPersons;

    @Before
    public void setUp() throws Exception {
        meetingPersons = new ArrayList<>();
        meetings = Arrays.asList(
                new Meeting("m1"),
                new Meeting("m2")
        );

        persons = Arrays.asList(
                new Person("User1"),
                new Person("User2"),
                new Person("User3")
        );
        persons.forEach(entityManager::persist);

        MeetingPerson meetingPerson1 = new MeetingPerson();
        meetingPerson1.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingPerson1.setUser(persons.stream().filter(p -> p.getName().equals("User1")).findAny().get());
        meetingPerson1.setOwner(true);
        meetingPersons.add(meetingPerson1);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingPersons().add(meetingPerson1));

        MeetingPerson meetingPerson2 = new MeetingPerson();
        meetingPerson2.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingPerson2.setUser(persons.stream().filter(p -> p.getName().equals("User2")).findAny().get());
        meetingPerson2.setOwner(false);
        meetingPersons.add(meetingPerson2);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingPersons().add(meetingPerson2));

        MeetingPerson meetingPerson3 = new MeetingPerson();
        meetingPerson3.setMeeting(meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get());
        meetingPerson3.setUser(persons.stream().filter(p -> p.getName().equals("User3")).findAny().get());
        meetingPerson3.setOwner(false);
        meetingPersons.add(meetingPerson3);
        meetings.stream().filter(m -> m.getName().equals("m2")).forEach(m -> m.getMeetingPersons().add(meetingPerson3));

        meetings.forEach(entityManager::persist);
    }


    @Test
    public void findAll() throws Exception {
        List<MeetingPerson> result = meetingUserRepository.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void findByMeeting() throws Exception {
        List<MeetingPerson> result = meetingUserRepository.findByMeetingId(
                meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get().getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getName()).isEqualTo("User3");
    }
}
