package com.goblin.internetproviderpractice.repositories;

import com.goblin.internetproviderpractice.dto.ShortServiceInfoDto;
import com.goblin.internetproviderpractice.model.ServiceInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceInfo, Integer> {

    Iterable<ServiceInfo> findByApprovedAtIsNull();

    @Modifying
    @Query(value = "SELECT new ShortServiceInfoDto(s.id, s.title, u.name) " +
            "from ServiceInfo s, UserProfile u " +
            "where s.createdBy.id=u.id")
    Iterable<ShortServiceInfoDto> findAllShortServiceInfoDto();

}
