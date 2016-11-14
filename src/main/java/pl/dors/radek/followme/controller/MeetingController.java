package pl.dors.radek.followme.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPerson;
import pl.dors.radek.followme.service.IMeetingService;

import java.util.List;
import java.util.Optional;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private IMeetingService meetingService;

    public MeetingController(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Meeting> findAll() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        return meetingService.findAll((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Meeting meeting,
                                       UriComponentsBuilder uriComponentsBuilder) {
        meetingService.save(meeting);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable("meetingId") long meetingId,
                                       @RequestBody Meeting meeting,
                                       UriComponentsBuilder uriComponentsBuilder) {
        meetingService.update(Optional.ofNullable(meetingId), meeting);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.DELETE)
    public ResponseEntity<Meeting> delete(@PathVariable("meetingId") long meetingId) {
        meetingService.delete(Optional.ofNullable(meetingId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.GET)
    public ResponseEntity<Meeting> showDetails(@PathVariable("meetingId") long meetingId) {
        Meeting meeting = meetingService.findOne(Optional.ofNullable(meetingId));
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}/addUser", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@PathVariable("meetingId") long meetingId,
                                        @RequestBody MeetingPerson meetingPerson,
                                        UriComponentsBuilder uriComponentsBuilder) {
        meetingService.addUser((Optional.ofNullable(meetingId)), meetingPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meetingId).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{meetingId}/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable("meetingId") long meetingId,
                                           @PathVariable("userId") long userId,
                                           @RequestBody MeetingPerson meetingPerson,
                                           UriComponentsBuilder uriComponentsBuilder) {
        meetingService.updateUser(Optional.ofNullable(meetingId), Optional.ofNullable(userId), meetingPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meetingId).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meetingId}/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Meeting> deleteUser(@PathVariable("meetingId") long meetingId,
                                              @PathVariable("userId") long userId) {
        meetingService.deleteUser(Optional.ofNullable(meetingId), Optional.ofNullable(userId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
