package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.specification.PlaceSpecification;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class PlaceRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceSpecification placeSpecification;

    private List<Place> places;

    @Before
    public void setUp() throws Exception {
        places = Arrays.asList(
                new Place("Stefan", 12, 13),
                new Place("ASD", 11, 11)
        );
        places.forEach(entityManager::persist);
    }

    @Test
    public void findAllTest() throws Exception {
        List<Place> result = placeRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsAll(places);
    }

    @Test
    public void findByNameTest() throws Exception {
        List<Place> result = placeRepository.findAll(PlaceSpecification.findByName("Stefan"));

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(places.stream().filter(p -> p.getName().equals("Stefan")).findAny().get());
    }
}