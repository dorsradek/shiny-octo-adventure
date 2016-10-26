package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.model.User;
import pl.dors.radek.followme.specification.PlaceSpecification;
import pl.dors.radek.followme.specification.UserSpecification;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private List<User> users;

    @Before
    public void setUp() throws Exception {
        users = Arrays.asList(
                new User("Stefan"),
                new User("ASD")
        );
        users.forEach(entityManager::persist);
    }

    @Test
    public void findAllTest() throws Exception {
        List<User> result = userRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsAll(users);
    }

    @Test
    public void findByNameTest() throws Exception {
        List<User> result = userRepository.findAll(UserSpecification.findByName("Stefan"));

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(users.stream().filter(p -> p.getName().equals("Stefan")).findAny().get());
    }
}