package pl.dors.radek.followme.service.boot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.impl.MeetingService;

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

}