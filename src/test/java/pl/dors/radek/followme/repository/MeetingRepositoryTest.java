package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Meeting;

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

    @Before
    public void setUp() throws Exception {
        meetings = Arrays.asList(
                new Meeting("m1"),
                new Meeting("m2")
        );
        meetings.forEach(entityManager::persist);
    }


    @Test
    public void findAll() throws Exception {
        List<Meeting> result = meetingRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(meetings);
    }

}
