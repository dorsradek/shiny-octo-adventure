package pl.dors.radek.followme.security.controller.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import pl.dors.radek.followme.security.JwtTokenUtil;
import pl.dors.radek.followme.security.service.JwtAuthenticationResponse;

import javax.annotation.PostConstruct;

@RestController
public class FacebookAuthenticationRestController {

    @Value("${spring.social.facebook.app-id}")
    private String facebookAppId;
    @Value("${spring.social.facebook.app-secret}")
    private String facebookAppSecret;

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;
    private FacebookConnectionFactory facebookConnectionFactory;

    public FacebookAuthenticationRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void postInit() {
        this.facebookConnectionFactory = new FacebookConnectionFactory(facebookAppId, facebookAppSecret);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationTokenFacebook(@RequestParam String tokenString) throws AuthenticationException {

        AccessGrant accessGrant = new AccessGrant(tokenString);
        Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);

        UserProfile userProfile = connection.fetchUserProfile();

        // Perform the security
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userProfile.getUsername(), null,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userProfile.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

}