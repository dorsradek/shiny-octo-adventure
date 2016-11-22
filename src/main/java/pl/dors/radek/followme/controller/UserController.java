package pl.dors.radek.followme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUser;
import pl.dors.radek.followme.service.IMeetingService;
import pl.dors.radek.followme.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private IMeetingService meetingService;
    private IUserService userService;

    public UserController(IMeetingService meetingService, IUserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            String username = ((JwtUser) principal).getUsername();
            return userService.findAllExceptUsername(username);
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> showDetails(@PathVariable("userId") long userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/meetings")
    public List<Meeting> findAllByUserId(@PathVariable("userId") long userId) {
        return meetingService.findByUserId(userId);
    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public ResponseEntity<Void> create(@RequestBody Meeting meeting, UriComponentsBuilder uriComponentsBuilder) {
//        meetingService.saveWithUsernameAsOwner(meeting);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT)
//    public ResponseEntity<Void> update(@RequestBody Meeting meeting, UriComponentsBuilder uriComponentsBuilder) {
//        meetingService.update(meeting);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{meetingId}", method = RequestMethod.DELETE)
//    public ResponseEntity<Meeting> delete(@PathVariable("meetingId") long meetingId) {
//        meetingService.delete(Optional.ofNullable(meetingId));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


//    @RequestMapping(value = "/{meetingId}/addPlace", method = RequestMethod.POST)
//    public ResponseEntity<Void> addPlace(@PathVariable("meetingId") long meetingId, @RequestBody MeetingPlace meetingPlace, UriComponentsBuilder uriComponentsBuilder) {
//        meetingService.addPlace(Optional.ofNullable(meetingId), meetingPlace);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meetingId).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }

}
