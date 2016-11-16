package pl.dors.radek.followme.service.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Relationship;
import pl.dors.radek.followme.model.RelationshipStatus;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.RelationshipRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.FriendshipService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;

/**
 * Created by rdors on 2016-10-22.
 */
@RunWith(SpringRunner.class)
public class FriendshipServiceTest {

    @InjectMocks
    private FriendshipService friendshipService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RelationshipRepository relationshipRepository;

    @Before
    public void setUp() throws Exception {
        User u1 = new User();
        u1.setUsername("User1");
        User u2 = new User();
        u2.setUsername("User2");

        Mockito.when(userRepository.findById(anyLong()))
                .then(i -> {
                    Long userId = i.getArgumentAt(0, Long.class);
                    User user = new User();
                    user.setId(userId);
                    user.setUsername("U111");
                    return Optional.of(user);
                });
        Mockito.when(userRepository.findByUsername(anyString()))
                .then(i -> {
                    String username = i.getArgumentAt(0, String.class);
                    User user = new User();
                    user.setId(111L);
                    user.setUsername(username);
                    return Optional.of(user);
                });
        Mockito.when(relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(anyLong()))
                .then(i -> {
                    Relationship r1 = new Relationship();
                    r1.getPk().setUser(u1);
                    r1.getPk().setFriend(u2);
                    r1.setRelationshipStatus(RelationshipStatus.ACCEPTED);
                    return Arrays.asList(r1);
                });
        Mockito.when(relationshipRepository.findAllRelationshipsByFriendIdAndStatus(anyLong(), eq(RelationshipStatus.PENDING)))
                .then(i -> {
                    Relationship r1 = new Relationship();
                    r1.getPk().setUser(u2);
                    r1.getPk().setFriend(u1);
                    r1.setRelationshipStatus(RelationshipStatus.PENDING);
                    return Arrays.asList(r1);
                });
    }

    @Test
    public void findAllFriends() {
        List<User> friends = friendshipService.findAllFriends("User1");
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getUsername()).isEqualTo("User2");
    }

    @Test
    public void findAllReceivedInvitations() {
        List<User> invitations = friendshipService.findAllReceivedInvitations("User1");
        assertThat(invitations).hasSize(1);
        assertThat(invitations.get(0).getUsername()).isEqualTo("User2");
    }


}
