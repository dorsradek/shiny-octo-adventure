package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.security.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        User u1 = new User();
        u1.setUsername("Stefan");
        User u2 = new User();
        u2.setUsername("ASDF");
        users = Arrays.asList(u1, u2);
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
        Optional<User> result = userRepository.findByUsername("Stefan");

        assertThat(result.get()).isNotNull();
        assertThat(result.get().getUsername()).isEqualTo("Stefan");
    }

    @Test
    public void saveTest() throws Exception {
        User user = new User();
        user.setUsername("U111");
        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
        User result = entityManager.find(User.class, user.getId());
        assertThat(result.getUsername()).isEqualTo("U111");
    }

    @Test
    public void updateNameTest() throws Exception {
        User user = entityManager.find(User.class, users.get(0).getId());
        user.setUsername("U333");
        userRepository.save(user);
        user = entityManager.find(User.class, user.getId());
        assertThat(user.getUsername()).isEqualTo("U333");
    }
}