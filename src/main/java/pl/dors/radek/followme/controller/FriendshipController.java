package pl.dors.radek.followme.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.service.IFriendshipService;
import pl.dors.radek.followme.util.SecurityUtil;

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

    @GetMapping
    public List<User> findAll() {
        String username = SecurityUtil.extractUser().orElseThrow(() -> new RuntimeException("Authentication problem"));
        return friendshipService.findAllFriends(username);
    }

}
