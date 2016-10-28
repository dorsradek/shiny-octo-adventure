package pl.dors.radek.followme.service;

import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.repository.PlaceRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@Service
@Transactional
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
    public List<Place> findByName(String name) {
        return placeRepository.findByName(name);
    }

}
