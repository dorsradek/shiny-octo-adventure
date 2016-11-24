package pl.dors.radek.followme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.service.IUserService;
import pl.dors.radek.followme.util.SecurityUtil;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    private IUserService userService;

    public LocationController(IUserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam double x, @RequestParam double y) {
        String username = SecurityUtil.extractUser().orElseThrow(() -> new RuntimeException("Authentication problem"));
        userService.updateLocation(username, x, y);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
