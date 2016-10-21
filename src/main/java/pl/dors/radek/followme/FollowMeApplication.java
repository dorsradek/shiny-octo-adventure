package pl.dors.radek.followme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
public class FollowMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FollowMeApplication.class, args);
    }

    @Component
    class CLR implements CommandLineRunner {

        @Autowired
        private CustomerRepository repository;

        @Override
        public void run(String... args) throws Exception {
            repository.deleteAll();

            // save a couple of customers
            repository.save(new Customer("Alice", "Smith"));
            repository.save(new Customer("Bob", "Smith"));

            // fetch all customers
            System.out.println("Customers found with findAll():");
            System.out.println("-------------------------------");
            repository.findAll().forEach(System.out::println);
            System.out.println();

            // fetch an individual customer
            System.out.println("Customer found with findByFirstName('Alice'):");
            System.out.println("--------------------------------");
            System.out.println(repository.findByFirstName("Alice"));

            System.out.println("Customers found with findByLastName('Smith'):");
            System.out.println("--------------------------------");
            repository.findByLastName("Smith").forEach(System.out::println);
        }
    }
}
