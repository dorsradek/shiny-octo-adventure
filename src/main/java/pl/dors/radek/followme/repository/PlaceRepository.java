package pl.dors.radek.followme.repository;

import org.springframework.data.repository.CrudRepository;
import pl.dors.radek.followme.model.Place;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findByName(String name);

    List<Place> findAll();

    Optional<Place> findById(Long id);

}
