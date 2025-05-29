package com.goblin.internetproviderpractice.services;

import com.goblin.internetproviderpractice.model.Parameter;
import com.goblin.internetproviderpractice.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Transactional(readOnly = true)
    public Optional<Parameter> findById(int id) {
        return parameterRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Iterable<Parameter> findAll() {
        return parameterRepository.findAll();
    }

    @Transactional
    public Parameter addParameter(@NonNull String name) {
        return parameterRepository.save(Parameter.builder()
                .title(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
    }
}
