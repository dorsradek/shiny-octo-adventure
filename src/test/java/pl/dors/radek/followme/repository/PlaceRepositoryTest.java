package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void findAllTest_Empty() throws Exception {
        places.forEach(entityManager::remove);

        List<Place> result = placeRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void findByNameTest() throws Exception {
        List<Place> result = placeRepository.findByName("Stefan");

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(places.stream().filter(p -> p.getName().equals("Stefan")).findAny().get());
    }

    @Test
    public void findByNameTest_NotExist() throws Exception {
        List<Place> result = placeRepository.findByName("Stefan2");

        assertThat(result).hasSize(0);
    }

    @Test
    public void findByIdTest() throws Exception {
        Place place = places.get(0);

        Optional<Place> result = placeRepository.findById(place.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(place.getName());
    }

    @Test
    public void findByIdTest_NotExist() throws Exception {
        Optional<Place> result = placeRepository.findById(places.stream().mapToLong(place -> place.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void saveTest() throws Exception {
        Place place = new Place("P1", 1, 2);
        placeRepository.save(place);

        assertThat(place.getId()).isNotNull();
        Place result = entityManager.find(Place.class, place.getId());
        assertThat(result.getName()).isEqualTo("P1");
    }

    @Test
    public void updateNameTest() throws Exception {
        Place place = entityManager.find(Place.class, places.get(0).getId());
        place.setName("P33");
        placeRepository.save(place);
        place = entityManager.find(Place.class, place.getId());
        assertThat(place.getName()).isEqualTo("P33");
    }
}