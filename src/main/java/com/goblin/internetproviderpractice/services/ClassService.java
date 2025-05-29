package com.goblin.internetproviderpractice.services;

import com.goblin.internetproviderpractice.model.ServiceClass;
import com.goblin.internetproviderpractice.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    /**
     * Find class by id
     * @param id Id of the class
     * @return Class with given id or None if no value is found
     */
    @Transactional(readOnly = true)
    public Optional<ServiceClass> findById(Integer id) {
        return classRepository.findById(id);
    }

    /**
     * Get all existing classes
     * @return Iterable object with all existing classes
     */
    @Transactional(readOnly = true)
    public Iterable<ServiceClass> findAll() {
        return classRepository.findAll();
    }

    /**
     * Create a new class with a given name. Time fields will be populated with the time of the function call
     * @param name Name to give the new class
     * @return Newly created class
     */
    @Transactional
    public ServiceClass createClass(@NonNull String name) {
        return classRepository.save(ServiceClass.builder()
                .title(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()
                ).build());
    }
}
