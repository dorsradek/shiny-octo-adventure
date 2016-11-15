package pl.dors.radek.followme.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtUserFactory;
import pl.dors.radek.followme.repository.UserRepository;

import java.util.Optional;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
        return JwtUserFactory.create(user.get());
    }
}
