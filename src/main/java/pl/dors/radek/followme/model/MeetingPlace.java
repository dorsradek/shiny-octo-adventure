package pl.dors.radek.followme.model;

import javax.persistence.*;

/**
 * Created by rdors on 2016-10-25.
 */
@Entity
@Table(name = "meeting_place")
@AssociationOverrides({
        @AssociationOverride(name = "pk.meeting",
                joinColumns = @JoinColumn(name = "MEETING_ID")),
        @AssociationOverride(name = "pk.place",
                joinColumns = @JoinColumn(name = "PLACE_ID"))})
public class MeetingPlace {

    @EmbeddedId
    private MeetingPlaceID pk = new MeetingPlaceID();

    @Column(name = "OWNER")
    private boolean owner;

    public MeetingPlaceID getPk() {
        return pk;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
