package pl.dors.radek.followme.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public List<User> findAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            String username = ((JwtUser) principal).getUsername();
            return friendshipService.findAllFriends(username);
        } else {
            return new ArrayList<>();
        }
    }

}
