package pl.dors.radek.followme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.security.JwtUser;
import pl.dors.radek.followme.service.IUserService;

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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestParam double x, @RequestParam double y) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            String username = ((JwtUser) principal).getUsername();
            userService.updateLocation(username, x, y);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new RuntimeException("Authentication error");
        }
    }

}
