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
import pl.dors.radek.followme.model.Place;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class PlaceRepositoryTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private PlaceRepository placeRepository;

    private List<Place> places;

    @Before
    public void setUp() throws Exception {
        places = Arrays.asList(
                new Place(new GeoJsonPoint(12, 13), "Stefan"),
                new Place(new GeoJsonPoint(11, 11), "ASD")
        );
        places.forEach(template::save);
    }

    @After
    public void tearDown() throws Exception {
        template.dropCollection(Place.class);
    }

    @Test
    public void findAllTest() throws Exception {
        List<Place> result = placeRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsAll(places);
    }

    @Test
    public void findByNameCriteria() throws Exception {
        List<Place> result = placeRepository.findByNameCriteria("Stefan");

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(places.stream().filter(p -> p.getName().equals("Stefan")).findAny().get());
    }
}