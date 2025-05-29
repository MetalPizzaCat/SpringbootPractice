package com.goblin.internetproviderpractice.controllers;

import com.goblin.internetproviderpractice.model.ServiceClass;
import com.goblin.internetproviderpractice.model.ServiceInfo;
import com.goblin.internetproviderpractice.model.requests.ClassCreateRequest;
import com.goblin.internetproviderpractice.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<Iterable<ServiceClass>> findAll() {
        return ResponseEntity.ok(classService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceClass> findById(@PathVariable Integer id) {
        return classService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceClass> createClass(@RequestBody ClassCreateRequest createRequest, UriComponentsBuilder ucb) {
        ServiceClass result = classService.createClass(createRequest.title());
        URI location = ucb.path("/classes/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }
}
