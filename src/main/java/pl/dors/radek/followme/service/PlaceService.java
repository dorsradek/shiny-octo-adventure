package pl.dors.radek.followme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;

import java.util.stream.Stream;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
public class PlaceService implements IPlaceService {

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public Stream<Place> findAll() {
        return placeRepository.findAll().stream();
    }
}
