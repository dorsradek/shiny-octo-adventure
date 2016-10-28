package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPlace;
import pl.dors.radek.followme.model.Place;

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
public class MeetingPlaceRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MeetingPlaceRepository meetingPlaceRepository;

    private List<Place> places;
    private List<Meeting> meetings;
    private List<MeetingPlace> meetingPlaces;

    @Before
    public void setUp() throws Exception {
        meetingPlaces = new ArrayList<>();
        meetings = Arrays.asList(
                new Meeting("m1"),
                new Meeting("m2")
        );

        places = Arrays.asList(
                new Place("Place1", 12, 13),
                new Place("Place2", 12, 13),
                new Place("Place3", 12, 13)
        );
        places.forEach(entityManager::persist);

        MeetingPlace meetingPlace1 = new MeetingPlace();
        meetingPlace1.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingPlace1.setPlace(places.stream().filter(p -> p.getName().equals("Place1")).findAny().get());
        meetingPlace1.setOwner(true);
        meetingPlaces.add(meetingPlace1);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingPlaces().add(meetingPlace1));

        MeetingPlace meetingPlace2 = new MeetingPlace();
        meetingPlace2.setMeeting(meetings.stream().filter(m -> m.getName().equals("m1")).findAny().get());
        meetingPlace2.setPlace(places.stream().filter(p -> p.getName().equals("Place2")).findAny().get());
        meetingPlace2.setOwner(false);
        meetingPlaces.add(meetingPlace2);
        meetings.stream().filter(m -> m.getName().equals("m1")).forEach(m -> m.getMeetingPlaces().add(meetingPlace2));

        MeetingPlace meetingPlace3 = new MeetingPlace();
        meetingPlace3.setMeeting(meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get());
        meetingPlace3.setPlace(places.stream().filter(p -> p.getName().equals("Place3")).findAny().get());
        meetingPlace3.setOwner(false);
        meetingPlaces.add(meetingPlace3);
        meetings.stream().filter(m -> m.getName().equals("m2")).forEach(m -> m.getMeetingPlaces().add(meetingPlace3));

        meetings.forEach(entityManager::persist);
    }


    @Test
    public void findAll() throws Exception {
        List<MeetingPlace> result = meetingPlaceRepository.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void findByMeeting() throws Exception {
        List<MeetingPlace> result = meetingPlaceRepository.findByMeetingId(
                meetings.stream().filter(m -> m.getName().equals("m2")).findAny().get().getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPlace().getName()).isEqualTo("Place3");
    }
}
