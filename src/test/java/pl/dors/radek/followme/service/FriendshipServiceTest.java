package pl.dors.radek.followme.service;

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
import pl.dors.radek.followme.service.impl.FriendshipService;

import java.util.ArrayList;
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

    User user1;
    User user2;
    User user3;
    private List<User> users;

    Relationship relationship1;
    Relationship relationship2;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Stefan");
        user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");
        users = Arrays.asList(user1, user2);

        user3 = new User();
        user3.setId(3L);
        user3.setUsername("not-exists");

        relationship1 = new Relationship();
        relationship1.getPk().setUser(user1);
        relationship1.getPk().setFriend(user2);
        relationship1.setRelationshipStatus(RelationshipStatus.ACCEPTED);

        relationship2 = new Relationship();
        relationship2.getPk().setUser(user2);
        relationship2.getPk().setFriend(user1);
        relationship2.setRelationshipStatus(RelationshipStatus.PENDING);

        Mockito.when(userRepository.findByUsername(eq(user1.getUsername())))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByUsername(eq(user2.getUsername())))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByUsername(eq(user3.getUsername())))
                .thenReturn(Optional.ofNullable(null));
        Mockito.when(userRepository.findByUsername(isNull(String.class)))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(userRepository.findById(eq(1L)))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(eq(2L)))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user3.getId()))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(eq(user1.getId())))
                .then(i -> Arrays.asList(relationship1));
        Mockito.when(relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(eq(user2.getId())))
                .then(i -> new ArrayList<>());

        Mockito.when(relationshipRepository.findAllRelationshipsByFriendIdAndStatus(eq(user1.getId()), eq(RelationshipStatus.PENDING)))
                .thenReturn(Arrays.asList(relationship2));
        Mockito.when(relationshipRepository.findAllRelationshipsByFriendIdAndStatus(eq(user2.getId()), eq(RelationshipStatus.PENDING)))
                .thenReturn(new ArrayList<>());

        Mockito.when(relationshipRepository.save(any(Relationship.class)))
                .then(i -> i.getArgumentAt(0, Relationship.class));

        Mockito.when(relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(eq(user2.getId()), eq(user1.getId()), eq(RelationshipStatus.PENDING)))
                .thenReturn(Optional.of(relationship2));
        Mockito.when(relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(eq(user1.getId()), eq(user2.getId()), eq(RelationshipStatus.PENDING)))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(relationshipRepository.findAllRelationships(eq(user1.getId()), eq(user2.getId())))
                .thenReturn(Arrays.asList(relationship1));
        Mockito.when(relationshipRepository.findAllRelationships(eq(user2.getId()), eq(user1.getId())))
                .thenReturn(new ArrayList<>());

        Mockito.doAnswer(i -> {
            Relationship relationship = i.getArgumentAt(0, Relationship.class);
            relationship.setRelationshipStatus(null);
            return relationship;
        }).when(relationshipRepository).delete(any(Relationship.class));
    }

    @Test
    public void findAllFriendsTest() {
        List<User> friends = friendshipService.findAllFriends(user1.getUsername());
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getUsername()).isEqualTo(user2.getUsername());
    }

    @Test
    public void findAllFriendsTest_Empty() {
        List<User> friends = friendshipService.findAllFriends(user2.getUsername());
        assertThat(friends).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void findAllFriendsTest_UserNotExists() {
        friendshipService.findAllFriends(user3.getUsername());
    }

    @Test
    public void findAllReceivedInvitationsTest() {
        List<User> invitations = friendshipService.findAllReceivedInvitations(user1.getUsername());
        assertThat(invitations).hasSize(1);
        assertThat(invitations.get(0).getUsername()).isEqualTo(user2.getUsername());
    }

    @Test
    public void findAllReceivedInvitationsTest_Empty() {
        List<User> invitations = friendshipService.findAllReceivedInvitations(user2.getUsername());
        assertThat(invitations).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void findAllReceivedInvitationsTest_UserNotExists() {
        friendshipService.findAllReceivedInvitations(user3.getUsername());
    }

    @Test
    public void addToFriendsTest() {
        Relationship result = friendshipService.addToFriends(user1.getUsername(), user2);
        assertThat(result).isNotNull();
        assertThat(result.getRelationshipStatus()).isEqualTo(RelationshipStatus.PENDING);
        assertThat(result.getPk().getUser()).isEqualTo(user1);
        assertThat(result.getPk().getFriend()).isEqualTo(user2);
    }

    @Test(expected = RuntimeException.class)
    public void addToFriendsTest_UsernameNotExists() {
        friendshipService.addToFriends(user3.getUsername(), user2);
    }

    @Test(expected = RuntimeException.class)
    public void addToFriendsTest_UsernameNull() {
        friendshipService.addToFriends(null, user2);
    }

    @Test(expected = RuntimeException.class)
    public void addToFriendsTest_FriendNotExists() {
        friendshipService.addToFriends(user1.getUsername(), user3);
    }

    @Test(expected = RuntimeException.class)
    public void addToFriendsTest_FriendNull() {
        friendshipService.addToFriends(user1.getUsername(), null);
    }

    @Test
    public void acceptInvitationTest() {
        Relationship result = friendshipService.acceptInvitation(user1.getUsername(), user2);
        assertThat(result).isNotNull();
        assertThat(result.getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);
        assertThat(result.getPk().getUser()).isEqualTo(user1);
        assertThat(result.getPk().getFriend()).isEqualTo(user2);
    }

    @Test(expected = RuntimeException.class)
    public void acceptInvitationTest_NotExists() {
        friendshipService.acceptInvitation(user2.getUsername(), user1);
    }

    @Test(expected = RuntimeException.class)
    public void acceptInvitationTest_UsernameNotExists() {
        friendshipService.acceptInvitation(user3.getUsername(), user2);
    }

    @Test(expected = RuntimeException.class)
    public void acceptInvitationTest_UsernameNull() {
        friendshipService.acceptInvitation(null, user2);
    }

    @Test(expected = RuntimeException.class)
    public void acceptInvitationTest_FriendNotExists() {
        friendshipService.acceptInvitation(user1.getUsername(), user3);
    }

    @Test(expected = RuntimeException.class)
    public void acceptInvitationTest_FriendNull() {
        friendshipService.acceptInvitation(user1.getUsername(), null);
    }

    @Test
    public void removeFromFriendsTest() {
        friendshipService.removeFromFriends(user1.getUsername(), user2.getId());
        assertThat(relationship1.getRelationshipStatus()).isNull();
    }

    @Test
    public void removeFromFriendsTest_NotExists() {
        friendshipService.removeFromFriends(user2.getUsername(), user1.getId());
        assertThat(relationship1.getRelationshipStatus()).isNotNull();
    }

    @Test(expected = RuntimeException.class)
    public void removeFromFriendsTest_UsernameNotExists() {
        friendshipService.removeFromFriends(user3.getUsername(), user2.getId());
    }

    @Test(expected = RuntimeException.class)
    public void removeFromFriendsTest_UsernameNull() {
        friendshipService.removeFromFriends(null, user2.getId());
    }

    @Test(expected = RuntimeException.class)
    public void removeFromFriendsTest_FriendNotExists() {
        friendshipService.removeFromFriends(user1.getUsername(), user3.getId());
    }

}
