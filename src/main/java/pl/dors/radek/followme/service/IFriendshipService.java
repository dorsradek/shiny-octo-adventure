package pl.dors.radek.followme.service;

import pl.dors.radek.followme.model.Relationship;
import pl.dors.radek.followme.model.security.User;

import java.util.List;

/**
 * Created by rdors on 2016-11-16.
 */
public interface IFriendshipService {

    List<User> findAllFriends(String username);

    List<User> findAllReceivedInvitations(String username);

    Relationship addToFriends(String username, User user);

    Relationship acceptInvitation(String username, User user);

    void removeFromFriends(String username, long friendId);
}
