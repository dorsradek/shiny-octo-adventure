package pl.dors.radek.followme.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingUser;
import pl.dors.radek.followme.service.IMeetingService;
import pl.dors.radek.followme.util.SecurityUtil;

import java.util.List;

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

    @GetMapping
    public List<Meeting> findAll() {
        String username = SecurityUtil.extractUser().orElseThrow(() -> new RuntimeException("Authentication problem"));
        return meetingService.findByUsername(username);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> create(@RequestBody Meeting meeting,
                                       UriComponentsBuilder uriComponentsBuilder) {
        String username = SecurityUtil.extractUser().orElseThrow(() -> new RuntimeException("Authentication problem"));
        meetingService.saveWithUsernameAsOwner(meeting, username);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{meetingId}")
    public ResponseEntity<Void> update(@PathVariable("meetingId") long meetingId,
                                       @RequestBody Meeting meeting,
                                       UriComponentsBuilder uriComponentsBuilder) {
        meetingService.update(meetingId, meeting);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{meetingId}")
    public ResponseEntity<Meeting> delete(@PathVariable("meetingId") long meetingId) {
        meetingService.delete(meetingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{meetingId}")
    public ResponseEntity<Meeting> showDetails(@PathVariable("meetingId") long meetingId) {
        Meeting meeting = meetingService.findById(meetingId);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    @PostMapping(value = "/{meetingId}/addUser")
    public ResponseEntity<Void> addUser(@PathVariable("meetingId") long meetingId,
                                        @RequestBody MeetingUser meetingPerson,
                                        UriComponentsBuilder uriComponentsBuilder) {
        meetingService.addUser(meetingId, meetingPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meetingId).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{meetingId}/users/{userId}")
    public ResponseEntity<Meeting> deleteUser(@PathVariable("meetingId") long meetingId,
                                              @PathVariable("userId") long userId) {
        meetingService.deleteUser(meetingId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
