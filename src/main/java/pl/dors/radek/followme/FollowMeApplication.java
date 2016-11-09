package pl.dors.radek.followme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.dors.radek.followme.model.security.Authority;
import pl.dors.radek.followme.model.security.AuthorityName;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.repository.AuthorityRepository;
import pl.dors.radek.followme.security.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class FollowMeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FollowMeApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        Authority a = new Authority();
        a.setName(AuthorityName.ROLE_ADMIN);
        authorityRepository.save(a);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi");
        user.setFirstname("admin");
        user.setLastname("admin");
        user.setEmail("Asdfgh");
        user.setEnabled(true);
        LocalDateTime dateTime = LocalDateTime.now().minusDays(5);
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        user.setLastPasswordResetDate(Date.from(instant));
        user.setAuthorities(Arrays.asList(a));
        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);
    }
}
