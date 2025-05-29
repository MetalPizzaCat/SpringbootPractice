package com.goblin.internetproviderpractice.services;

import com.goblin.internetproviderpractice.model.Role;
import com.goblin.internetproviderpractice.model.UserProfile;
import com.goblin.internetproviderpractice.model.requests.UserRegisterRequest;
import com.goblin.internetproviderpractice.repositories.RegisterNewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired
    private RegisterNewUserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public boolean registerUser(UserRegisterRequest profile, Role role) {
        Optional<UserProfile> user = userRepository.findByName(profile.name());
        if (user.isEmpty()) {

            userRepository.save(UserProfile.builder()
                    .name(profile.name())
                    .password(encoder.encode(profile.password()))
                    .role(role).build());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteUser(String name) {
        Optional<UserProfile> userProfile = userRepository.findByName(name);
        if (userProfile.isPresent()) {
            userRepository.delete(userProfile.get());
            return true;
        }
        return false;
    }
}
