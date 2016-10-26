package pl.dors.radek.followme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

/**
 * Created by rdors on 2016-10-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    @MockBean(name = "meetingRepository")
    private MeetingRepository meetingRepository;
    @MockBean(name = "placeRepository")
    private PlaceRepository placeRepository;
    @MockBean(name = "userRepository")
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        given(this.meetingRepository.findAll())
                .willReturn(Arrays.asList(new Meeting("Test")));
        given(this.placeRepository.findAll(any(Example.class)))
                .willReturn(Arrays.asList(new Place("Place2", 10, 9)));
        given(this.placeRepository.findAll(any(Specification.class)))
                .willReturn(Arrays.asList(new Place("Place3", 11, 15)));
    }

    @Test
    public void findAll() throws Exception {
        assertThat(meetingService.findAll()).hasSize(1);
        assertThat(meetingService.findAll().get(0).getName()).isEqualTo("Test");
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void addPlaces() throws Exception {

    }

    @Test
    public void addUsers() throws Exception {

    }

}