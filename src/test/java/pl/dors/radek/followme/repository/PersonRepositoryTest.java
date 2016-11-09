package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.Person;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    private List<Person> persons;

    @Before
    public void setUp() throws Exception {
        persons = Arrays.asList(
                new Person("Stefan"),
                new Person("ASD")
        );
        persons.forEach(entityManager::persist);
    }

    @Test
    public void findAllTest() throws Exception {
        List<Person> result = personRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsAll(persons);
    }

    @Test
    public void findByNameTest() throws Exception {
        List<Person> result = personRepository.findByName("Stefan");

        assertThat(result).hasSize(1);
        assertThat(result).containsOnly(persons.stream().filter(p -> p.getName().equals("Stefan")).findAny().get());
    }

    @Test
    public void saveTest() throws Exception {
        Person person = new Person("U1");
        personRepository.save(person);

        assertThat(person.getId()).isNotNull();
        Person result = entityManager.find(Person.class, person.getId());
        assertThat(result.getName()).isEqualTo("U1");
    }

    @Test
    public void updateNameTest() throws Exception {
        Person person = entityManager.find(Person.class, persons.get(0).getId());
        person.setName("U33");
        personRepository.save(person);
        person = entityManager.find(Person.class, person.getId());
        assertThat(person.getName()).isEqualTo("U33");
    }
}