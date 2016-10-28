package pl.dors.radek.followme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dors.radek.followme.model.Meeting;
import pl.dors.radek.followme.service.IMeetingService;

import java.util.List;

/**
 * Created by rdors on 2016-10-21.
 */
@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private IMeetingService meetingService;

    @Autowired
    public MeetingController(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Meeting> findAll() {
        return meetingService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Meeting meeting, UriComponentsBuilder ucBuilder) {
        meetingService.save(meeting);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/meetings/{id}").buildAndExpand(meeting.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{meetingId}", method = RequestMethod.GET)
    public ResponseEntity<Meeting> showDetails(@PathVariable("meetingId") long meetingId) {
        Meeting meeting = meetingService.findOne(meetingId);
        ResponseEntity<Meeting> asd =  new ResponseEntity<>(meeting, HttpStatus.OK);
        return asd;
    }
}
