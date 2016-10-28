package pl.dors.radek.followme.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.model.MeetingPlace;
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
        return meetingService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Meeting meeting, UriComponentsBuilder uriComponentsBuilder) {
        meetingService.save(meeting);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Meeting meeting, UriComponentsBuilder uriComponentsBuilder) {
        meetingService.update(meeting);
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

    @RequestMapping(value = "/{meetingId}/addPlace", method = RequestMethod.POST)
    public ResponseEntity<Void> addPlace(@PathVariable("meetingId") long meetingId, @RequestBody MeetingPlace meetingPlace, UriComponentsBuilder uriComponentsBuilder) {
        meetingService.addPlace(Optional.ofNullable(meetingId), meetingPlace);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/meetings/{id}").buildAndExpand(meetingId).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
