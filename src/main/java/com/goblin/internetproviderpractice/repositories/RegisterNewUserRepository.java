package com.goblin.internetproviderpractice.repositories;

import com.goblin.internetproviderpractice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterNewUserRepository extends JpaRepository<UserProfile, Integer> {

    Optional<UserProfile> findByName(String name);
}
