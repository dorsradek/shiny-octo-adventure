package pl.dors.radek.followme.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.model.Place;
import pl.dors.radek.followme.service.IPlaceService;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/places")
public class PlaceController {

    private IPlaceService placeService;

    public PlaceController(IPlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> findAll() {
        return placeService.findAll();
    }
}
