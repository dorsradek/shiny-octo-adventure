package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.security.User;

import java.util.List;

/**
 * Created by rdors on 2016-11-16.
 */
public interface IFriendshipService {

    List<User> findAllFriends(String username);

    List<User> findAllReceivedInvitations(String username);

    void addToFriends(String username, User user);

    void acceptInvitation(String username, User user);

    void removeFromFriends(String username, User user);
}
