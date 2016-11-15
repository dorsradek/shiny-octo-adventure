package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.dors.radek.followme.model.Meeting;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    Optional<Meeting> findById(Long id);

    List<Meeting> findAll();

    List<Meeting> findByActiveTrue();

    @Query(value = "SELECT m FROM Meeting m LEFT JOIN FETCH m.meetingUsers mu WHERE mu.pk.user.username = :username")
    List<Meeting> findByUsername(@Param("username") String username);

    @Query(value = "SELECT m FROM Meeting m LEFT JOIN FETCH m.meetingUsers mu WHERE mu.pk.user.id = :userId")
    List<Meeting> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT m FROM Meeting m LEFT JOIN FETCH m.meetingUsers mu WHERE mu.pk.user.id = :userId AND m.active = true")
    List<Meeting> findByUserIdAndActiveTrue(@Param("userId") Long userId);

}