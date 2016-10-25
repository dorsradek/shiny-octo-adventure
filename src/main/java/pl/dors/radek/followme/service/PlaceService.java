package pl.dors.radek.followme.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;
import pl.dors.radek.followme.specification.PlaceSpecification;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
public class PlaceService implements IPlaceService {

    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public List<Place> findByNameExample(String name) {
        Place place = new Place();
        place.setName(name);
        Example<Place> example = Example.of(place);
        return placeRepository.findAll(example);
    }

    @Override
    public List<Place> findByNameSpecification(String name) {
        return placeRepository.findAll(PlaceSpecification.findByName(name));
    }

}
