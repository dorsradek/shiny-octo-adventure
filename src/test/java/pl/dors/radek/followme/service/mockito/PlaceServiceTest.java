package pl.dors.radek.followme.service.mockito;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
public class PlaceServiceTest {

//    @InjectMocks
//    private PlaceService placeService;
//
//    @Mock
//    private PlaceRepository2 placeRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        Mockito.when(placeRepository.findAll())
//                .thenReturn(Arrays.asList(new Place(new GeoJsonPoint(12, 13), "Stefan")));
//    }
//
//    @Test
//    public void findAllTest() {
//        Stream<Place> result = placeService.findAll();
//
//        Mockito.verify(placeRepository, Mockito.times(1)).findAll();
//        assertThat(result.peek(System.out::println).collect(Collectors.toList())).hasSize(1);
//    }
}
