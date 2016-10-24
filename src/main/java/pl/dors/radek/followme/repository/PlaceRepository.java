package pl.dors.radek.followme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.custom.PlaceRepositoryCustom;

/**
 * Created by rdors on 2016-10-21.
 */
@Repository
public interface PlaceRepository extends MongoRepository<Place, Long>, PlaceRepositoryCustom {

}
