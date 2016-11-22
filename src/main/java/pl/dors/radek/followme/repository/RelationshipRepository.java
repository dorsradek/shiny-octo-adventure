package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.dors.radek.followme.model.Relationship;
import pl.dors.radek.followme.model.RelationshipStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by stephan on 20.03.16.
 */
public interface RelationshipRepository extends CrudRepository<Relationship, Long> {

    List<Relationship> findAll();

    @Query("SELECT r FROM Relationship r WHERE r.pk.user.id = :userId AND r.relationshipStatus = pl.dors.radek.followme.model.RelationshipStatus.ACCEPTED")
    List<Relationship> findAllRelationshipsByUserIdAndAreFriends(@Param("userId") Long userId);

    @Query("SELECT r FROM Relationship r WHERE r.pk.user.id = :userId AND r.relationshipStatus = :status")
    List<Relationship> findAllRelationshipsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") RelationshipStatus status);

    @Query("SELECT r FROM Relationship r WHERE r.pk.friend.id = :friendId AND r.relationshipStatus = :status")
    List<Relationship> findAllRelationshipsByFriendIdAndStatus(@Param("friendId") Long friendId, @Param("status") RelationshipStatus status);

    @Query("SELECT r FROM Relationship r WHERE r.pk.user.id = :userId AND r.pk.friend.id = :friendId")
    Optional<Relationship> findRelationshipByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query("SELECT r FROM Relationship r WHERE " +
            "(r.pk.user.id = :userId AND r.pk.friend.id = :friendId) OR " +
            "(r.pk.user.id = :friendId AND r.pk.friend.id = :userId)")
    List<Relationship> findAllRelationships(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
