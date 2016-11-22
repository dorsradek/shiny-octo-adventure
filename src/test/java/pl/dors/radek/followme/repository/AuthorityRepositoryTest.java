package pl.dors.radek.followme.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dors.radek.followme.model.security.Authority;
import pl.dors.radek.followme.model.security.AuthorityName;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Transactional
public class AuthorityRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthorityRepository authorityRepository;

    private List<Authority> authorities;

    @Before
    public void setUp() throws Exception {
        Authority authority1 = new Authority();
        authority1.setName(AuthorityName.ROLE_USER);

        authorities = Arrays.asList(
                authority1
        );
        authorities.forEach(entityManager::persist);
    }

    @Test
    public void findByAuthorityNameTest() throws Exception {
        Optional<Authority> result = authorityRepository.findByAuthorityName(AuthorityName.ROLE_USER);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(AuthorityName.ROLE_USER);
    }

    @Test
    public void findByAuthorityNameTest_NotExist() throws Exception {
        Optional<Authority> result = authorityRepository.findByAuthorityName(AuthorityName.ROLE_ADMIN);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByAuthorityNameTest_Null() throws Exception {
        Optional<Authority> result = authorityRepository.findByAuthorityName(null);

        assertThat(result.isPresent()).isFalse();
    }

}