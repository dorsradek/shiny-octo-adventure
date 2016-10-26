package pl.dors.radek.followme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.dors.radek.followme.model.MeetingUser;

/**
 * Created by rdors on 2016-10-26.
 */
public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long>, JpaSpecificationExecutor<MeetingUser> {
}
