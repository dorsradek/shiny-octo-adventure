package pl.dors.radek.followme.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceServiceTest {

//    @Autowired
//    private PlaceService placeService;
//
//    @MockBean(name = "placeRepository")
//    private PlaceRepository2 placeRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        given(this.placeRepository.findAll())
//                .willReturn(Arrays.asList(new Place(new GeoJsonPoint(12, 13), "Stefan")));
//    }
//
//    @Test
//    public void findAllTest() {
//        Stream<Place> places = placeService.findAll();
//
//        assertThat(places.peek(System.out::println).collect(Collectors.toList())).hasSize(1);
//    }
}
