package pl.dors.radek.followme.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUser;
import pl.dors.radek.followme.service.IFriendshipService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/friends")
public class FriendshipController {

    private IFriendshipService friendshipService;

    public FriendshipController(IFriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            String username = ((JwtUser) principal).getUsername();
            return friendshipService.findAllFriends(username);
        } else {
            return new ArrayList<>();
        }
    }

//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    public ResponseEntity<User> showDetails(@PathVariable("userId") long userId) {
//        User user = userService.findOne(Optional.ofNullable(userId));
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{userId}/meetings", method = RequestMethod.GET)
//    public List<Meeting> findAllByUserId(@PathVariable("userId") long userId) {
//        return meetingService.findByUserId(Optional.ofNullable(userId));
//    }

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
