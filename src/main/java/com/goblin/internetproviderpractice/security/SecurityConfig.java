package com.goblin.internetproviderpractice.security;

import com.goblin.internetproviderpractice.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@ConditionalOnProperty(value = "security.enabled", havingValue = "true")
public class SecurityConfig {
    @Autowired
    private UserInfoService infoService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**")));
    }
}
