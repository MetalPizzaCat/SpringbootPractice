package com.goblin.internetproviderpractice.services;

import com.goblin.internetproviderpractice.model.UserProfile;
import com.goblin.internetproviderpractice.repositories.RegisterNewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service for converting the given user details into details from the service database
 */
@Service
public class UserInfoService implements UserDetailsService {
    /**
     * Reference to the users table where actual user info is stored
     */
    @Autowired
    private RegisterNewUserRepository userRepository;

    private final Set<GrantedAuthority> authoritySet = new HashSet<>();

    /**
     * Retrieve full user data from a name
     * @param username Source username
     * @return User details for a given user
     * @throws UsernameNotFoundException If no user uses this name
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> user = userRepository.findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user with name " + username + " exists");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.get().getRole().toString());
        authoritySet.add(authority);
        return new User(username, user.get().getPassword(), authoritySet);
    }
}
