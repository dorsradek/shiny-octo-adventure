package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.dors.radek.followme.model.id.RelationshipID;
import pl.dors.radek.followme.model.security.User;

import javax.persistence.*;

/**
 * Created by rdors on 2016-11-16.
 */
@Entity
@Table(name = "RELATIONSHIPS")
public class Relationship {

    @JsonIgnore
    private RelationshipID pk = new RelationshipID();
    private RelationshipStatus relationshipStatus;

    @EmbeddedId
    public RelationshipID getPk() {
        return pk;
    }

    public void setPk(RelationshipID pk) {
        this.pk = pk;
    }

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

}
