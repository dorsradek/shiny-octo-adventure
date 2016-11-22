package pl.dors.radek.followme.service.impl;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Relationship;
import pl.dors.radek.followme.model.RelationshipStatus;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.repository.RelationshipRepository;
import pl.dors.radek.followme.repository.UserRepository;
import pl.dors.radek.followme.service.IFriendshipService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rdors on 2016-11-16.
 */
@Service
@Transactional
public class FriendshipService implements IFriendshipService {

    private UserRepository userRepository;
    private RelationshipRepository relationshipRepository;

    public FriendshipService(UserRepository userRepository, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    public List<User> findAllFriends(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<Relationship> relationships = relationshipRepository.findAllRelationshipsByUserIdAndAreFriends(user.getId());
        return relationships.stream().map(r -> r.getPk().getFriend()).collect(Collectors.toList());
    }

    @Override
    public List<User> findAllReceivedInvitations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<Relationship> relationships = relationshipRepository
                .findAllRelationshipsByFriendIdAndStatus(user.getId(), RelationshipStatus.PENDING);
        return relationships.stream().map(r -> r.getPk().getUser()).collect(Collectors.toList());
    }

    @Override
    public Relationship addToFriends(String username, User user) {
        User me = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        User friend = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Friend not found"));
        Relationship relationship = new Relationship();
        relationship.getPk().setUser(me);
        relationship.getPk().setFriend(friend);
        relationship.setRelationshipStatus(RelationshipStatus.PENDING);
        return relationshipRepository.save(relationship);
    }

    @Override
    public Relationship acceptInvitation(String username, User user) {
        User me = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        User invinter = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Invinter not found"));
        //invinter is userId and me is a friend
        Relationship relationship = relationshipRepository.findRelationshipByUserIdAndFriendIdAndStatus(invinter.getId(), me.getId(), RelationshipStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        relationship.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        relationshipRepository.save(relationship);

        Relationship myRelationship = new Relationship();
        myRelationship.getPk().setUser(me);
        myRelationship.getPk().setFriend(invinter);
        myRelationship.setRelationshipStatus(RelationshipStatus.ACCEPTED);
        return relationshipRepository.save(myRelationship);
    }

    @Override
    public void removeFromFriends(String username, long friendId) {
        User me = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));
        List<Relationship> relationships = relationshipRepository.findAllRelationships(me.getId(), friend.getId());
        relationships.forEach(relationshipRepository::delete);
    }
}
