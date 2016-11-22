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
    public void findAllTest_Empty() throws Exception {
        users.forEach(entityManager::remove);

        List<User> result = userRepository.findAll();

        assertThat(result).hasSize(0);
    }

    @Test
    public void findByUsernameTest() throws Exception {
        Optional<User> result = userRepository.findByUsername("Stefan");

        assertThat(result.get()).isNotNull();
        assertThat(result.get().getUsername()).isEqualTo("Stefan");
    }

    @Test
    public void findByUsernameTest_NotExist() throws Exception {
        Optional<User> result = userRepository.findByUsername("Stefan1");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByUsernameTest_Null() throws Exception {
        Optional<User> result = userRepository.findByUsername(null);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findById() throws Exception {
        Optional<User> result = userRepository.findById(users.get(0).getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUsername()).isEqualTo(users.get(0).getUsername());
    }

    @Test
    public void findById_NotExist() throws Exception {
        Optional<User> result = userRepository.findById(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
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