package com.goblin.internetproviderpractice.security;

import com.goblin.internetproviderpractice.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Config for the default security which set ups the password encoder and request permits
 */
@Configuration
@ConditionalOnProperty(value = "security.enabled", havingValue = "true")
public class SecurityConfig {
    @Autowired
    private UserInfoService infoService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    /**
     * @return Password encoder used by the user profile system
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Setup the security filter chain with all necessary security features
     * @param http Security config builder object
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disable csrf because this is a simple practice project
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityContext(withDefaults()) // infrastructure
                .servletApi(withDefaults()) // infrastructure
                //.csrf(withDefaults()) // defense
                .headers(withDefaults()) // defense
                .logout(withDefaults()) // authentication
                .sessionManagement(withDefaults()) // authentication
                .requestCache(withDefaults()) // authentication
                .formLogin(withDefaults()) // authentication
                .httpBasic(withDefaults()) // authentication
                .anonymous(withDefaults()) // authentication
                .exceptionHandling(withDefaults()) // infrastructuresecurityMatcher("/services*", "/user*")
                .authorizeHttpRequests(unauthorizedEntryPoint ->
                        // setup all the permits for requests
                        unauthorizedEntryPoint
                                .requestMatchers("/services*").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/classes*").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/user").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/user/register*").permitAll()
                                .anyRequest().authenticated()
                ).httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(unauthorizedEntryPoint)).userDetailsService(infoService);
        return http.build();
    }


}
