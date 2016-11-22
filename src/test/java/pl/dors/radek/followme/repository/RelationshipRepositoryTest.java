package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Relationship;
import pl.dors.radek.followme.model.RelationshipStatus;
import pl.dors.radek.followme.model.security.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rdors on 2016-11-16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class RelationshipRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RelationshipRepository relationshipRepository;

    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    List<User> users;

    Relationship relationship1;
    Relationship relationship2;
    Relationship relationship3;
    Relationship relationship4;
    Relationship relationship5;
    List<Relationship> relationships;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setUsername("User1");
        user2 = new User();
        user2.setUsername("User2");
        user3 = new User();
        user3.setUsername("User3");
        user4 = new User();
        user4.setUsername("User4");
        users = Arrays.asList(user1, user2, user3, user4);
        users.forEach(entityManager::persist);

        //Friends U1 - U2, U1 - U3
        //Pending U2 -> U3
        relationships = new ArrayList<>();
        relationship1 = new Relationship();
        relationship1.getPk().setUser(user1);
        relationship1.getPk().setFriend(user2);
        relationship1.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(relationship1);

        relationship2 = new Relationship();
        relationship2.getPk().setUser(user2);
        relationship2.getPk().setFriend(user1);
        relationship2.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(relationship2);

        relationship3 = new Relationship();
        relationship3.getPk().setUser(user1);
        relationship3.getPk().setFriend(user3);
        relationship3.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(relationship3);

        relationship4 = new Relationship();
        relationship4.getPk().setUser(user3);
        relationship4.getPk().setFriend(user1);
        relationship4.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(relationship4);

        relationship5 = new Relationship();
        relationship5.getPk().setUser(user2);
        relationship5.getPk().setFriend(user3);
        relationship5.setRelationshipStatus(RelationshipStatus.PENDING);
        relationships.add(relationship5);

        relationships.forEach(entityManager::persist);
    }

    @Test
    public void findAllTest() {
        List<Relationship> relationships = relationshipRepository.findAll();
        assertThat(relationships).hasSize(5);
    }

    @Test
    public void findAllTest_Empty() throws Exception {
        relationships.forEach(entityManager::remove);

        List<Relationship> result = relationshipRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByUserIdAndAreFriendsTest() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(user1.getId());

        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship1, relationship3);
    }

    @Test
    public void findAllRelationshipsByUserIdAndAreFriendsTest_hasOnePending() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(user2.getId());

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship2);
    }

    @Test
    public void findAllRelationshipsByUserIdAndAreFriendsTest_UserIdNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByUserIdAndAreFriendsTest_RelationshipNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(user4.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByUserIdAndStatusTest() {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user1.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship1, relationship3);

        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user2.getId(), RelationshipStatus.PENDING);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship5);
        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user2.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship2);

        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user3.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user3.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship4);
    }

    @Test
    public void findAllRelationshipsByUserIdAndStatusTest_UserIdNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(users.stream().mapToLong(user -> user.getId()).sum(), RelationshipStatus.ACCEPTED);

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByUserIdAndStatusTest_StatusNull() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user1.getId(), null);

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByUserIdAndStatusTest_RelationshipNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user4.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).isEmpty();
        result = relationshipRepository.findAllRelationshipsByUserIdAndStatus(user1.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByFriendIdAndStatusTest() {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user1.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship2, relationship4);

        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user2.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user2.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship1);

        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user3.getId(), RelationshipStatus.PENDING);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship5);
        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user3.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship3);
    }

    @Test
    public void findAllRelationshipsByFriendIdAndStatusTest_FriendIdNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(users.stream().mapToLong(user -> user.getId()).sum(), RelationshipStatus.ACCEPTED);

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByFriendIdAndStatusTest_StatusNull() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user1.getId(), null);

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsByFriendIdAndStatusTest_RelationshipNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user4.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result).isEmpty();

        result = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(user1.getId(), RelationshipStatus.PENDING);
        assertThat(result).isEmpty();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdTest() {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendId(user1.getId(), user2.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendId(user2.getId(), user1.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendId(user1.getId(), user3.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendId(user3.getId(), user1.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendId(user2.getId(), user3.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.PENDING);
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdTest_RelationshipNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendId(
                user1.getId(),
                user4.getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdTest_UserAndFriendNotExist() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendId(
                users.stream().mapToLong(user -> user.getId()).sum(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdTest_UserNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendId(
                users.stream().mapToLong(user -> user.getId()).sum(),
                user1.getId());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdTest_FriendNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendId(
                user1.getId(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest() {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(user1.getId(), user2.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(user2.getId(), user1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(user1.getId(), user3.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(user3.getId(), user1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(user2.getId(), user3.getId(), RelationshipStatus.PENDING);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.PENDING);
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest_RelationshipNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                user1.getId(),
                user4.getId(),
                RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isFalse();

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                user1.getId(),
                user2.getId(),
                RelationshipStatus.PENDING);
        assertThat(result.isPresent()).isFalse();

        result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                user2.getId(),
                user3.getId(),
                RelationshipStatus.ACCEPTED);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest_UserAndFriendNotExist() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                users.stream().mapToLong(user -> user.getId()).sum(),
                users.stream().mapToLong(user -> user.getId()).sum(),
                RelationshipStatus.ACCEPTED);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest_UserNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                users.stream().mapToLong(user -> user.getId()).sum(),
                user1.getId(),
                RelationshipStatus.ACCEPTED);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest_FriendNotExists() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                user1.getId(),
                users.stream().mapToLong(user -> user.getId()).sum(),
                RelationshipStatus.ACCEPTED);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findRelationshipByUserIdAndFriendIdAndStatusTest_StatusNull() throws Exception {
        Optional<Relationship> result = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(
                user1.getId(),
                user2.getId(),
                null);

        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void findAllRelationshipsTest() {
        List<Relationship> result = relationshipRepository.findAllRelationships(user1.getId(), user2.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship1, relationship2);

        result = relationshipRepository.findAllRelationships(user2.getId(), user1.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship1, relationship2);

        result = relationshipRepository.findAllRelationships(user1.getId(), user3.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship3, relationship4);

        result = relationshipRepository.findAllRelationships(user3.getId(), user1.getId());
        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(relationship3, relationship4);

        result = relationshipRepository.findAllRelationships(user2.getId(), user3.getId());
        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(relationship5);
    }

    @Test
    public void findAllRelationshipsTest_RelationshipNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationships(
                user1.getId(),
                user4.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsTest_UserAndFriendNotExist() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationships(
                users.stream().mapToLong(user -> user.getId()).sum(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsTest_UserNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationships(
                users.stream().mapToLong(user -> user.getId()).sum(),
                user1.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllRelationshipsTest_FriendNotExists() throws Exception {
        List<Relationship> result = relationshipRepository.findAllRelationships(
                user1.getId(),
                users.stream().mapToLong(user -> user.getId()).sum());

        assertThat(result).isEmpty();
    }

}
