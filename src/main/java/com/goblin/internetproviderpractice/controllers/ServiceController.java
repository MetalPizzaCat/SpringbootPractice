package com.goblin.internetproviderpractice.controllers;

import com.goblin.internetproviderpractice.model.ServiceInfo;
import com.goblin.internetproviderpractice.model.requests.ApproveRequest;
import com.goblin.internetproviderpractice.model.requests.ServiceCreateRequest;
import com.goblin.internetproviderpractice.model.requests.ServiceUpdateRequest;
import com.goblin.internetproviderpractice.services.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceManagementService managementService;

    @GetMapping
    public ResponseEntity<Iterable<ServiceInfo>> findAll() {
        return ResponseEntity.ok(managementService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceInfo> findById(@PathVariable Integer id) {
        return managementService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceInfo> createService(
            @CurrentSecurityContext(expression = "authentication.name") String name,
            @RequestBody ServiceCreateRequest serviceCreateRequest,
            UriComponentsBuilder ucb) {

        ServiceInfo result = managementService.createService(serviceCreateRequest, name);
        URI location = ucb.path("/services/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    /**
     * Request handler for the update of the service action
     *
     * @param id            Id of the service to be updated
     * @param updateRequest Data related to the update
     * @return Response entity either storing the updated service or "Not found" response if no service uses this id
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceInfo> updateService(@PathVariable Integer id,
                                                     @RequestBody ServiceUpdateRequest updateRequest) {
        try {
            return ResponseEntity.ok(managementService.updateService(id, updateRequest.title(), updateRequest.parameters()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/unapproved")
    public ResponseEntity<Iterable<ServiceInfo>> findUnapproved() {
        return ResponseEntity.ok(managementService.findAllUnapproved());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ServiceInfo> approveService(@PathVariable Integer id,
                                                      @RequestBody ApproveRequest approveRequest,
                                                      UriComponentsBuilder ucb) {
        ServiceInfo result = managementService.approveService(id, approveRequest.classId());
        URI location = ucb.path("/services/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }
}
