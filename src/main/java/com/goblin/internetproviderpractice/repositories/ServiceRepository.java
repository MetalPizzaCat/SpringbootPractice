package com.goblin.internetproviderpractice.repositories;

import com.goblin.internetproviderpractice.model.ServiceInfo;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceInfo, Integer> {

    Iterable<ServiceInfo> findByApprovedAtIsNull();
}
