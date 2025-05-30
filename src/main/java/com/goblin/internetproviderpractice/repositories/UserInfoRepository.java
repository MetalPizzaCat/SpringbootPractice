package com.goblin.internetproviderpractice.repositories;

import com.goblin.internetproviderpractice.model.UserInfo;
import com.goblin.internetproviderpractice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String name);
}
