package pl.dors.radek.followme.service.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.MeetingService;
import pl.dors.radek.followme.service.MeetingServiceCommon;

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
        MeetingServiceCommon.setUp(meetingRepository, userRepository, placeRepository);
    }

    @Test
    public void findAll() throws Exception {
        MeetingServiceCommon.findAll(meetingService);
    }

    @Test
    public void save() throws Exception {
        MeetingServiceCommon.save(meetingService);
    }

    @Test
    public void addPlaces() throws Exception {
        MeetingServiceCommon.addPlaces(meetingService);
    }

    @Test
    public void addUsers() throws Exception {
        MeetingServiceCommon.addUsers(meetingService);
    }

}