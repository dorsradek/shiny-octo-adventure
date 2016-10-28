package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.dors.radek.followme.model.MeetingPlace;

import java.util.List;

/**
 * Created by rdors on 2016-10-25.
 */
public interface MeetingPlaceRepository extends CrudRepository<MeetingPlace, Long> {

    List<MeetingPlace> findAll();

    @Query(value = "SELECT mu FROM MeetingPlace mu WHERE mu.pk.meeting.id = :meetingId")
    List<MeetingPlace> findByMeetingId(@Param("meetingId") Long meetingId);

}
