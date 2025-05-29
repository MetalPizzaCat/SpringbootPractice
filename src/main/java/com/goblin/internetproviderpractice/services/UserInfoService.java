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

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private RegisterNewUserRepository userRepository;

    private final Set<GrantedAuthority> authoritySet = new HashSet<>();

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
