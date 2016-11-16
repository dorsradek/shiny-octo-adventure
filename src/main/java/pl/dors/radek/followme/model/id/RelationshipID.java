package pl.dors.radek.followme.model.id;

import pl.dors.radek.followme.model.security.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by rdors on 2016-11-16.
 */
@Embeddable
public class RelationshipID implements java.io.Serializable {

    private User user;
    private User friend;

    @ManyToOne
    @JoinColumn(name = "ME")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "FRIEND")
    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
