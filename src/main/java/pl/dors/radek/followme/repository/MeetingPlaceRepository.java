package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.dors.radek.followme.model.MeetingPlace;

/**
 * Created by rdors on 2016-10-25.
 */
public interface MeetingPlaceRepository extends JpaRepository<MeetingPlace, Long>, JpaSpecificationExecutor<MeetingPlace> {
}
