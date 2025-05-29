package com.goblin.internetproviderpractice.repositories;

import com.goblin.internetproviderpractice.model.ServiceClass;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends CrudRepository<ServiceClass, Integer> {
}
