package pl.dors.radek.followme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.repository.custom.MeetingRepositoryCustom;

/**
 * Created by rdors on 2016-10-21.
 */
@Repository
public interface MeetingRepository extends MongoRepository<Meeting, Long>, MeetingRepositoryCustom {
}
