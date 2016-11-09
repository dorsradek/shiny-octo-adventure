package pl.dors.radek.followme.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("development")
class ConfigDevelopment extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigDevelopment.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("Run DEVELOPMENT Security Configuration");
    }
}