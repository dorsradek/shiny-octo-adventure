package pl.dors.radek.followme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.service.impl.PlaceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
public class PlaceServiceTest {

    @InjectMocks
    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;

    @Before
    public void setUp() throws Exception {
        Mockito.when(this.placeRepository.findAll())
                .thenReturn(
                        Arrays.asList(new Place("Place1", 12, 13))
                );
        Mockito.when(this.placeRepository.findByName(eq("test")))
                .thenReturn(
                        Arrays.asList(new Place("Place2", 10, 9))
                );
        Mockito.when(this.placeRepository.findByName(eq("")))
                .thenReturn(
                        new ArrayList<>()
                );
    }

    @Test
    public void findAllTest() throws Exception {
        List<Place> places = placeService.findAll();
        assertThat(places).hasSize(1);
    }

    @Test
    public void findByNameTest() throws Exception {
        List<Place> places = placeService.findByName("test");
        assertThat(places).hasSize(1);
        assertThat(places.get(0).getName()).isEqualTo("Place2");
    }

    @Test
    public void findByNameTest_NotExist() throws Exception {
        List<Place> places = placeService.findByName("");
        assertThat(places).isEmpty();
    }

    @Test
    public void findByNameTest_Null() throws Exception {
        List<Place> places = placeService.findByName(null);
        assertThat(places).isEmpty();
    }

}
