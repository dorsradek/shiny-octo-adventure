package pl.dors.radek.followme.repository;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class PlaceRepositoryTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private PlaceRepository placeRepository;

    private Place place;

    @Before
    public void setUp() throws Exception {
        place = new Place(new GeoJsonPoint(12, 13), "Stefan");
        template.save(place);
    }

    @Test
    public void findAllTest() {
        List<Place> result = placeRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(place);
    }

}