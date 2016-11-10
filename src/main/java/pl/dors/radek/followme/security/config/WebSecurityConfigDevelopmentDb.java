package pl.dors.radek.followme.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("development-db")
public class WebSecurityConfigDevelopmentDb extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfigDevelopmentDb.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("Run DEVELOPMENT Security Configuration");
        http.csrf().disable();
    }
}