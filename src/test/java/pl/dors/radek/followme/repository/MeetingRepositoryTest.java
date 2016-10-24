package pl.dors.radek.followme.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rdors on 2016-10-24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class MeetingRepositoryTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private MeetingRepository meetingRepository;

    private List<Place> places;
    private List<User> users;
    private List<Meeting> meetings;

    @Before
    public void setUp() throws Exception {
        users = Arrays.asList(
                new User("Radek"),
                new User("Marta")
        );

        places = Arrays.asList(
                new Place(new GeoJsonPoint(12, 13), "Place1"),
                new Place(new GeoJsonPoint(12, 13), "Place2"),
                new Place(new GeoJsonPoint(12, 13), "Place3"),
                new Place(new GeoJsonPoint(12, 13), "Place4"),
                new Place(new GeoJsonPoint(12, 13), "Place5"),
                new Place(new GeoJsonPoint(12, 13), "Place6"),
                new Place(new GeoJsonPoint(12, 13), "Place7"),
                new Place(new GeoJsonPoint(12, 13), "Place8")
        );

        meetings = Arrays.asList(
                new Meeting("m1", places.stream().filter(p -> p.getName().endsWith("1") || p.getName().endsWith("2")).collect(Collectors.toList()), users),
                new Meeting("m2", places.stream().filter(p -> p.getName().endsWith("3") || p.getName().endsWith("4")).collect(Collectors.toList()), users.stream().filter(p -> p.getName().equals("Radek")).collect(Collectors.toList())),
                new Meeting("m3", places.stream().filter(p -> p.getName().endsWith("5") || p.getName().endsWith("6")).collect(Collectors.toList()), users.stream().filter(p -> p.getName().equals("Marta")).collect(Collectors.toList())),
                new Meeting("m4", places.stream().filter(p -> p.getName().endsWith("7") || p.getName().endsWith("8")).collect(Collectors.toList()), new ArrayList<>())
        );

        users.forEach(template::save);
        places.forEach(template::save);
        meetings.forEach(template::save);
    }

    @After
    public void tearDown() throws Exception {
        template.dropCollection(Meeting.class);
        template.dropCollection(Place.class);
        template.dropCollection(User.class);
    }

    @Test
    public void findAll() throws Exception {
        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).hasSize(4);
        assertThat(result).containsOnlyElementsOf(meetings);
    }

    @Test
    public void findAllByUserTest() throws Exception {
        List<Meeting> result = meetingRepository.findAllByUser(users.stream().filter(p -> p.getName().equals("Radek")).findAny().get());

        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(meetings.stream().filter(m -> m.getName().equals("m1") || m.getName().equals("m2")).collect(Collectors.toList()));
    }
}
