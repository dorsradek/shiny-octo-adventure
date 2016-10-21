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

}
