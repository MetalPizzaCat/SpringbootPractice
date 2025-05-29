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

    @Transactional(readOnly = true)
    public Optional<ServiceClass> findById(Integer id) {
        return classRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Iterable<ServiceClass> findAll() {
        return classRepository.findAll();
    }

    @Transactional
    public ServiceClass createClass(@NonNull String name) {
        return classRepository.save(ServiceClass.builder()
                .title(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()
                ).build());
    }
}
