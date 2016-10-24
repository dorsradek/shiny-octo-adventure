package pl.dors.radek.followme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @MockBean(name = "placeRepository")
    private PlaceRepository placeRepository;

    @Before
    public void setUp() throws Exception {
        given(this.placeRepository.findAll())
                .willReturn(Arrays.asList(new Place(new GeoJsonPoint(12, 13), "Stefan")));
    }

    @Test
    public void findAllTest() {
        Stream<Place> places = placeService.findAll();

        assertThat(places.peek(System.out::println).collect(Collectors.toList())).hasSize(1);
    }
}
