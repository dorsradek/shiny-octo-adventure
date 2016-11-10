package pl.dors.radek.followme.security.controller.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.security.JwtTokenUtil;
import pl.dors.radek.followme.security.repository.AuthorityRepository;
import pl.dors.radek.followme.security.repository.UserRepository;
import pl.dors.radek.followme.security.service.JwtAuthenticationResponse;

import javax.annotation.PostConstruct;

@RestController
public class TwitterAuthenticationRestController extends SocialRestController {

    @Value("${spring.social.twitter.app-id}")
    private String twitterAppId;
    @Value("${spring.social.twitter.app-secret}")
    private String twitterAppSecret;

    private TwitterConnectionFactory twitterConnectionFactory;

    public TwitterAuthenticationRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, UserRepository userRepository, AuthorityRepository authorityRepository) {
        super(jwtTokenUtil, userDetailsService, userRepository, authorityRepository);
    }

    @PostConstruct
    public void postInit() {
        this.twitterConnectionFactory = new TwitterConnectionFactory(twitterAppId, twitterAppSecret);
    }

    @RequestMapping(value = "/twitter", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenFacebook(@RequestParam String token, @RequestParam String tokenSecret, Device device) throws AuthenticationException {

        UserProfile userProfile = obtainSocialUser(token, tokenSecret);
        // Perform the security
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), null,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        createUser(userProfile);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userProfile.getUsername());

        final String jwtToken = jwtTokenUtil.generateToken(userDetails, device);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @Override
    protected UserProfile obtainSocialUser(String token, String tokenSecret) {
        OAuthToken oAuthToken = new OAuthToken(token, tokenSecret);
        Connection<Twitter> connection = twitterConnectionFactory.createConnection(oAuthToken);
        return connection.fetchUserProfile();
    }

}