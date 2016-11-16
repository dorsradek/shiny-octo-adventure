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

    User u1;
    User u2;
    User u3;
    User u4;
    User u5;

    @Before
    public void setUp() throws Exception {
        u1 = new User();
        u1.setUsername("User1");
        u2 = new User();
        u2.setUsername("User2");
        u3 = new User();
        u3.setUsername("User3");
        List<User> users = Arrays.asList(u1, u2, u3);
        users.forEach(entityManager::persist);

        //Friends U1 - U2, U1 - U3
        //Pending U2 -> U3
        List<Relationship> relationships = new ArrayList<>();
        Relationship r1 = new Relationship();
        r1.getPk().setUser(u1);
        r1.getPk().setFriend(u2);
        r1.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r1);

        Relationship r2 = new Relationship();
        r2.getPk().setUser(u2);
        r2.getPk().setFriend(u1);
        r2.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r2);

        Relationship r3 = new Relationship();
        r3.getPk().setUser(u1);
        r3.getPk().setFriend(u3);
        r3.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r3);

        Relationship r4 = new Relationship();
        r4.getPk().setUser(u3);
        r4.getPk().setFriend(u1);
        r4.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r4);

        Relationship r5 = new Relationship();
        r5.getPk().setUser(u2);
        r5.getPk().setFriend(u3);
        r5.setRelationshipStatus(RelationshipStatus.PENDING);
        relationships.add(r5);

        relationships.forEach(entityManager::persist);
    }

    @Test
    public void findAll() {
        List<Relationship> relationships = relationshipRepository.findAll();
        assertThat(relationships).hasSize(5);
    }

    @Test
    public void findAllFriendsByUserId() {
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(u1.getId());
        assertThat(relationships).hasSize(2);
        relationships = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(u2.getId());
        assertThat(relationships).hasSize(1);
        relationships = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(u3.getId());
        assertThat(relationships).hasSize(1);
    }

    @Test
    public void findAllRelationshipsByOwnerUserIdAndStatus() {
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsByOwnerUserIdAndStatus(u1.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(0);
        relationships = relationshipRepository.findAllRelationshipsByOwnerUserIdAndStatus(u1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(relationships).hasSize(2);
        relationships = relationshipRepository.findAllRelationshipsByOwnerUserIdAndStatus(u2.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(1);
        relationships = relationshipRepository.findAllRelationshipsByOwnerUserIdAndStatus(u3.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(0);
    }

    @Test
    public void findAllRelationshipsByFriendIdAndStatus() {
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(u1.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(0);
        relationships = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(u1.getId(), RelationshipStatus.ACCEPTED);
        assertThat(relationships).hasSize(2);
        relationships = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(u2.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(0);
        relationships = relationshipRepository.findAllRelationshipsByFriendIdAndStatus(u3.getId(), RelationshipStatus.PENDING);
        assertThat(relationships).hasSize(1);
    }

    @Test
    public void findRelationshipsByUserIdAndFriendId() {
        Optional<Relationship> relationship = relationshipRepository.findRelationshipByUserIdAndFriendId(u1.getId(), u2.getId());
        assertThat(relationship.get().getRelationshipStatus()).isNotNull();
        relationship = relationshipRepository.findRelationshipByUserIdAndFriendId(u1.getId(), u3.getId());
        assertThat(relationship.get().getRelationshipStatus()).isNotNull();
        relationship = relationshipRepository.findRelationshipByUserIdAndFriendId(u2.getId(), u3.getId());
        assertThat(relationship.get().getRelationshipStatus()).isNotNull();
    }

    @Test
    public void deleteRelationships() {
        u4 = new User();
        u4.setUsername("User4");
        u5 = new User();
        u5.setUsername("User5");
        List<User> users = Arrays.asList(u4, u5);
        users.forEach(entityManager::persist);

        List<Relationship> relationships = new ArrayList<>();
        Relationship r1 = new Relationship();
        r1.getPk().setUser(u4);
        r1.getPk().setFriend(u5);
        r1.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r1);

        Relationship r2 = new Relationship();
        r2.getPk().setUser(u5);
        r2.getPk().setFriend(u4);
        r2.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationships.add(r2);
        relationships.forEach(entityManager::persist);

        Optional<Relationship> relationship = relationshipRepository.findRelationshipByUserIdAndFriendId(u4.getId(), u5.getId());
        assertThat(relationship.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);
        relationship = relationshipRepository.findRelationshipByUserIdAndFriendId(u5.getId(), u4.getId());
        assertThat(relationship.get().getRelationshipStatus()).isEqualTo(RelationshipStatus.ACCEPTED);

        relationships = relationshipRepository.findAllRelationships(u4.getId(), u5.getId());
        assertThat(relationships).hasSize(2);

    }

}
