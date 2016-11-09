package pl.dors.radek.followme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rdors on 2016-10-24.
 */
@Entity
@Table(name = "PERSON")
public class Person {

    private Long id;
    private String name;
    private List<MeetingPerson> meetingPersons;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.person")
    public List<MeetingPerson> getMeetingPersons() {
        return meetingPersons;
    }

    public void setMeetingPersons(List<MeetingPerson> meetingPersons) {
        this.meetingPersons = meetingPersons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        return name != null ? name.equals(person.name) : person.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", meetingPersons=" + meetingPersons +
                '}';
    }
}
