package pl.dors.radek.followme.security.controller.social;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.UserProfile;
import pl.dors.radek.followme.model.security.Authority;
import pl.dors.radek.followme.model.security.AuthorityName;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtTokenUtil;
import pl.dors.radek.followme.security.repository.AuthorityRepository;
import pl.dors.radek.followme.security.repository.UserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * Created by rdors on 2016-11-10.
 */
public abstract class SocialRestController {

    protected JwtTokenUtil jwtTokenUtil;
    protected UserDetailsService userDetailsService;
    protected UserRepository userRepository;
    protected AuthorityRepository authorityRepository;

    public SocialRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }


    protected void createUser(UserProfile userProfile) {
        Optional<User> userOptional = userRepository.findByUsername(userProfile.getEmail());
        if (!userOptional.isPresent()) {
            transformUser(userProfile);
        }
    }

    protected void transformUser(UserProfile userProfile) {
        //TODO: change user creation
        Optional<Authority> authorityOptional = authorityRepository.findByAuthorityName(AuthorityName.ROLE_USER);
        User user = new User();
        user.setAuthorities(Arrays.asList(authorityOptional.orElse(null)));
        user.setEmail(userProfile.getEmail());
        user.setUsername(Optional.ofNullable(userProfile.getUsername()).orElse(userProfile.getEmail()));
        user.setEnabled(true);
        user.setFirstname(userProfile.getFirstName());
        user.setLastname(userProfile.getLastName());
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);
    }

    protected abstract UserProfile obtainSocialUser(String token, String tokenSecret);

}
