package pl.dors.radek.followme.service.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.MeetingService;
import pl.dors.radek.followme.service.PlaceService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

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

    @Before
    public void setUp() throws Exception {
        Mockito.when(this.meetingRepository.findAll())
                .thenReturn(Arrays.asList(new Meeting("M1")));
        Mockito.when(this.placeRepository.findAll(any(Example.class)))
                .thenReturn(Arrays.asList(new Place("Place2", 10, 9)));
        Mockito.when(this.placeRepository.findAll(any(Specification.class)))
                .thenReturn(Arrays.asList(new Place("Place3", 11, 15)));
    }

    @Test
    public void findAll() throws Exception {
        assertThat(meetingService.findAll()).hasSize(1);
        assertThat(meetingService.findAll().get(0).getName()).isEqualTo("M1");
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