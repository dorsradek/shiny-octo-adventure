package pl.dors.radek.followme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.impl.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user1;
    User user2;
    private List<User> users;

    double newX = 11;
    double newY = 22;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Stefan");
        user2 = new User();
        user2.setId(2L);
        user2.setUsername("ASDF");
        users = Arrays.asList(user1, user2);

        Mockito.when(this.userRepository.findAll())
                .thenReturn(users);

        Mockito.when(this.userRepository.findByUsername(eq("Stefan")))
                .thenReturn(Optional.of(user1));
        Mockito.when(this.userRepository.findByUsername(not(eq("Stefan"))))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(this.userRepository.findById(eq(1L)))
                .thenReturn(Optional.of(user1));
        Mockito.when(this.userRepository.findById(not(eq(1L))))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(this.userRepository.save(any(User.class)))
                .then(i -> i.getArgumentAt(0, User.class));
    }

    @Test
    public void findAllTest() throws Exception {
        List<User> result = userService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsOnlyElementsOf(users);
    }

    @Test
    public void findByUsernameTest() throws Exception {
        User user = userService.findByUsername("Stefan");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("Stefan");
    }

    @Test(expected = RuntimeException.class)
    public void findByUsernameTest_NotExist() throws Exception {
        userService.findByUsername("");
    }

    @Test(expected = RuntimeException.class)
    public void findByUsernameTest_Null() throws Exception {
        userService.findByUsername(null);
    }

    @Test
    public void findByIdTest() throws Exception {
        User user = userService.findById(1L);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("Stefan");
    }

    @Test(expected = RuntimeException.class)
    public void findByIdTest_NotExist() throws Exception {
        userService.findById(3L);
    }

    @Test
    public void findAllExceptUsernameTest() throws Exception {
        List<User> result = userService.findAllExceptUsername(user1.getUsername());
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(user2);
    }

    @Test
    public void findAllExceptUsernameTest_NotExist() throws Exception {
        List<User> result = userService.findAllExceptUsername("TEST");
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(user1, user2);
    }

    @Test
    public void findAllExceptUsernameTest_Null() throws Exception {
        List<User> result = userService.findAllExceptUsername(null);
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(user1, user2);
    }

    @Test
    public void updateLocationTest() throws Exception {
        User result = userService.updateLocation("Stefan", newX, newY);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("Stefan");
        assertThat(result.getX()).isEqualTo(newX);
        assertThat(result.getY()).isEqualTo(newY);
    }

    @Test(expected = RuntimeException.class)
    public void updateLocationTest_UserNotExist() throws Exception {
        userService.updateLocation("Stefan22", newX, newY);
    }

    @Test(expected = RuntimeException.class)
    public void updateLocationTest_UserNull() throws Exception {
        userService.updateLocation(null, newX, newY);
    }

    @Test(expected = RuntimeException.class)
    public void updateLocationTest_LocationXNull() throws Exception {
        userService.updateLocation("Stefan", null, newY);
    }

    @Test(expected = RuntimeException.class)
    public void updateLocationTest_LocationYNull() throws Exception {
        userService.updateLocation("Stefan", newX, null);
    }

    @Test(expected = RuntimeException.class)
    public void updateLocationTest_LocationXAndYNull() throws Exception {
        userService.updateLocation("Stefan", null, null);
    }

}
