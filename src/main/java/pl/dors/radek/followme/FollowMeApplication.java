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

}
