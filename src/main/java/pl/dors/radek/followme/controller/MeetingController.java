package pl.dors.radek.followme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
}
