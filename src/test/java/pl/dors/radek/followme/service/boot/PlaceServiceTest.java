package pl.dors.radek.followme.service.boot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.service.impl.PlaceService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

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
                .willReturn(Arrays.asList(new Place("Place1", 12, 13)));
        given(this.placeRepository.findByName(anyString()))
                .willReturn(Arrays.asList(new Place("Place2", 10, 9)));
    }

    @Test
    public void findAll() throws Exception {
        List<Place> places = placeService.findAll();
        assertThat(places).hasSize(1);
    }

    @Test
    public void findByNameExample() throws Exception {
        List<Place> places = placeService.findByName("name");
        assertThat(places).hasSize(1);
        assertThat(places.get(0).getName()).isEqualTo("Place2");
    }


}
