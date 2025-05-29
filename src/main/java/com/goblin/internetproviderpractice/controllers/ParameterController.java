package com.goblin.internetproviderpractice.controllers;

import com.goblin.internetproviderpractice.model.Parameter;
import com.goblin.internetproviderpractice.model.requests.ParameterCreateRequest;
import com.goblin.internetproviderpractice.services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;

    @GetMapping
    public ResponseEntity<Iterable<Parameter>> findAll() {
        return ResponseEntity.ok(parameterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parameter> findById(int id) {
        return parameterService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Parameter> createParameter(@RequestBody ParameterCreateRequest parameterCreateRequest,
                                                     UriComponentsBuilder ucb) {
        Parameter parameter = parameterService.addParameter(parameterCreateRequest.title());
        URI location = ucb.path("/parameters/{id}").buildAndExpand(parameter.getId()).toUri();
        return ResponseEntity.created(location).body(parameter);
    }


}
