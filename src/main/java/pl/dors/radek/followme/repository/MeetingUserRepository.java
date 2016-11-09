package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.dors.radek.followme.model.MeetingPerson;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-26.
 */
public interface MeetingUserRepository extends CrudRepository<MeetingPerson, Long> {

    List<MeetingPerson> findAll();

    @Query(value = "SELECT mu FROM MeetingPerson mu WHERE mu.pk.meeting.id = :meetingId")
    List<MeetingPerson> findByMeetingId(@Param("meetingId") Long meetingId);

    @Query(value = "SELECT mu FROM MeetingPerson mu WHERE mu.pk.meeting.id = :meetingId AND mu.pk.person.id = :userId")
    Optional<MeetingPerson> findByMeetingIdAndUserId(@Param("meetingId") Long meetingdId, @Param("userId") Long userId);

}
