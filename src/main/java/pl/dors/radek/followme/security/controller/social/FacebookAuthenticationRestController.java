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
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dors.radek.followme.model.security.User;
import pl.dors.radek.followme.security.JwtTokenUtil;
import pl.dors.radek.followme.security.repository.AuthorityRepository;
import pl.dors.radek.followme.security.repository.UserRepository;
import pl.dors.radek.followme.security.service.JwtAuthenticationResponse;

import javax.annotation.PostConstruct;

@RestController
public class FacebookAuthenticationRestController extends SocialRestController {

    @Value("${spring.social.facebook.app-id}")
    private String facebookAppId;
    @Value("${spring.social.facebook.app-secret}")
    private String facebookAppSecret;

    private FacebookConnectionFactory facebookConnectionFactory;

    public FacebookAuthenticationRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, UserRepository userRepository, AuthorityRepository authorityRepository) {
        super(jwtTokenUtil, userDetailsService, userRepository, authorityRepository);
    }

    @PostConstruct
    public void postInit() {
        this.facebookConnectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenFacebook(@RequestParam String token, Device device) throws AuthenticationException {

        UserProfile userProfile = obtainSocialUser(token, null);
        // Perform the security
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getEmail(), null,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        createUser(userProfile);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userProfile.getEmail());
        User user = new User();
        user.setUsername(userProfile.getEmail());

        final String jwtToken = jwtTokenUtil.generateToken(userDetails, device);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, user));
    }

    @Override
    protected UserProfile obtainSocialUser(String token, String tokenSecret) {
        AccessGrant accessGrant = new AccessGrant(token);
        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
        return connection.fetchUserProfile();
    }

}