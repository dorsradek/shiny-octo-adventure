package pl.dors.radek.followme.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.dors.radek.followme.commons.TestCommons;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.service.PlaceService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rdors on 2016-06-30.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceService placeService;

    @Autowired
    private TestCommons testCommons;

    @Before
    public void setup() {
        Stream<Place> expectedResult = Stream.of(new Place(new GeoJsonPoint(12, 13), "Stefan"));
        given(this.placeService.findAll()).willReturn(expectedResult);
    }

    @Test
    public void testGetPlaces() throws Exception {
        Place p1 = new Place(new GeoJsonPoint(12, 13), "Stefan");
        List<Place> expectedResult = Stream.of(p1).collect(Collectors.toList());

        this.mvc.perform(get("/places"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestCommons.CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.content().json(testCommons.json(expectedResult)));
    }
}
