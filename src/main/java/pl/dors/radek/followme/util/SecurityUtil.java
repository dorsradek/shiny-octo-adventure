package pl.dors.radek.followme.util;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.dors.radek.followme.security.JwtUser;

import java.util.Optional;

/**
 * Created by rdors on 2016-11-24.
 */
public class SecurityUtil {

    public static Optional<String> extractUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof JwtUser) {
            return Optional.ofNullable(((JwtUser) principal).getUsername());
        }
        return Optional.ofNullable(null);
    }

}
