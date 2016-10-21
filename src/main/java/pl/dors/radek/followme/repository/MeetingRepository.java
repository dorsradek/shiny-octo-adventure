package pl.dors.radek.followme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.dors.radek.followme.model.Meeting;

/**
 * Created by rdors on 2016-10-21.
 */
public interface MeetingRepository extends MongoRepository<Meeting, Long> {
}
