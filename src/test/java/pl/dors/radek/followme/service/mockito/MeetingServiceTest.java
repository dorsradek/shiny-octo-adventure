package pl.dors.radek.followme.service.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.repository.MeetingRepository;
import pl.dors.radek.followme.repository.PersonRepository;
import pl.dors.radek.followme.repository.PlaceRepository;
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
    private PersonRepository personRepository;

    @Before
    public void setUp() throws Exception {
        MeetingServiceCommon.setUp(meetingRepository, personRepository, placeRepository);
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