package pl.dors.radek.followme.service.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.service.PlaceService;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
public class PlaceServiceTest {

    @InjectMocks
    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;

    @Test
    public void findAllTest() {
        Mockito.when(placeRepository.findAll()).thenReturn(Arrays.asList(new Place(new GeoJsonPoint(12, 13), "Stefan")));
        Stream<Place> places = placeService.findAll();

        Mockito.verify(placeRepository, Mockito.times(1)).findAll();
        assertThat(places.peek(System.out::println).collect(Collectors.toList())).hasSize(1);
    }
}
