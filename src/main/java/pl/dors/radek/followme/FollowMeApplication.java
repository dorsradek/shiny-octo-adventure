package pl.dors.radek.followme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class FollowMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FollowMeApplication.class, args);
    }

    /*@Component
    class CLR implements CommandLineRunner {

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private PlaceRepository2 placeRepository;

        @Autowired
        private MeetingRepository meetingRepository;

        @Override
        public void run(String... args) throws Exception {
            prepareCustomers();
            preparePlaces();
            prepareMeetings();
        }

        private void prepareCustomers() {
            customerRepository.deleteAll();

            // save a couple of customers
            customerRepository.save(new Customer("Alice", "Smith"));
            customerRepository.save(new Customer("Bob", "Smith"));

            // fetch all customers
            System.out.println("Customers found with findAll():");
            System.out.println("-------------------------------");
            customerRepository.findAll().forEach(System.out::println);
            System.out.println();

            // fetch an individual customer
            System.out.println("Customer found with findByFirstName('Alice'):");
            System.out.println("--------------------------------");
            System.out.println(customerRepository.findByFirstName("Alice"));

            System.out.println("Customers found with findByLastName('Smith'):");
            System.out.println("--------------------------------");
            customerRepository.findByLastName("Smith").forEach(System.out::println);
        }

        private void preparePlaces() {
            placeRepository.deleteAll();

            placeRepository.save(new Place(new GeoJsonPoint(1, 2), "Place 1"));
            placeRepository.save(new Place(new GeoJsonPoint(10, 20), "Place 2"));

            // fetch all places
            System.out.println("Places found with findAll():");
            System.out.println("-------------------------------");
            placeRepository.findAll().forEach(System.out::println);
        }

        private void prepareMeetings() {
            List<Place> places = Arrays.asList(
                    new Place(new GeoJsonPoint(20, 10), "Place Meeting 1"),
                    new Place(new GeoJsonPoint(15, 5), "Place Meeting 2"));
            places.stream().forEach(placeRepository::save);

            meetingRepository.deleteAll();

            Meeting m1 = new Meeting(places);
            meetingRepository.save(m1);
            Meeting m2 = new Meeting(places);
            meetingRepository.save(m2);

            // fetch all meetings
            System.out.println("-------------------------------");
            System.out.println("Meetings found with findAll():");
            System.out.println("-------------------------------");
            meetingRepository.findAll().stream()
                    .flatMap(m -> m.getPlaces().stream())
                    .forEach(System.out::println);
        }
    }*/
}
