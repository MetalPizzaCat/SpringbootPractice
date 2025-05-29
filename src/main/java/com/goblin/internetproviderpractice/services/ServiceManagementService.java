package com.goblin.internetproviderpractice.services;

import com.goblin.internetproviderpractice.model.requests.ServiceCreateRequest;
import com.goblin.internetproviderpractice.model.ServiceInfo;
import com.goblin.internetproviderpractice.repositories.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceManagementService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private ClassService classService;

    /**
     * Create a new service with given name and author
     *
     * @param serviceCreateRequest Data used for the creation of the service
     * @param creator              Name of the user who requested the creation of the service
     * @return Newly created service
     */
    @Transactional
    public ServiceInfo createService(@NonNull ServiceCreateRequest serviceCreateRequest,
                                     @NonNull String creator) {
        return serviceRepository.save(ServiceInfo.builder()
                .title(serviceCreateRequest.title())
                .createdAt(LocalDateTime.now())
                .createdBy(creator).build());
    }

    /**
     * Create a new service with given name and author with initial parameters
     *
     * @param serviceCreateRequest Data used for the creation of the service
     * @param creator              Name of the user who requested the creation of the service
     * @param parameterIds         List of all the parameters that this service has
     * @return Newly created service
     * @throws NoSuchElementException If parameters id is invalid an exception will be thrown
     */
    @Transactional
    public ServiceInfo createService(@NonNull ServiceCreateRequest serviceCreateRequest,
                                     @NonNull String creator,
                                     @NonNull List<Integer> parameterIds) throws NoSuchElementException {
        return serviceRepository.save(ServiceInfo.builder()
                .title(serviceCreateRequest.title())
                .createdAt(LocalDateTime.now())
                .createdBy(creator)
                .parameters(parameterIds.stream().map(i -> parameterService.findById(i).orElseThrow()).collect(Collectors.toSet()))
                .build());
    }

    @Transactional(readOnly = true)
    public Optional<ServiceInfo> findById(int id) {
        return serviceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Iterable<ServiceInfo> findAll() {
        return serviceRepository.findAll();
    }

    /**
     * Update service with new information
     *
     * @param id           Id of the element to update
     * @param title        New title for the service
     * @param parameterIds Ids of the parameters that need to be added to this service
     * @return Updated service
     * @throws NoSuchElementException If service with given id or any of the parameters are invalid exception will be thrown
     */
    @Transactional
    public ServiceInfo updateService(Integer id,
                                     @NonNull String title,
                                     @NonNull List<Integer> parameterIds) throws NoSuchElementException {
        ServiceInfo service = serviceRepository.findById(id).orElseThrow();
        service.setTitle(title);
        service.setParameters(parameterIds.stream().map(i ->
                parameterService.findById(i).orElseThrow()
        ).collect(Collectors.toSet()));

        return serviceRepository.save(service);
    }

    /**
     * @return All the services which were not yet approved
     */
    @Transactional(readOnly = true)
    public Iterable<ServiceInfo> findAllUnapproved() {
        return serviceRepository.findByApprovedAtIsNull();
    }

    /**
     * Approve a service to be a part of a given class
     * @param serviceId Id of the service to approve
     * @param classId Id of the class to assign to the service
     * @return Updated service value
     * @throws NoSuchElementException If either service or class id are invalid
     */
    @Transactional
    public ServiceInfo approveService(Integer serviceId, Integer classId) throws  NoSuchElementException{
        ServiceInfo service = serviceRepository.findById(serviceId).orElseThrow();
        service.setServiceClass(classService.findById(classId).orElseThrow());
        service.setApprovedAt(LocalDateTime.now());
        return serviceRepository.save(service);
    }
}
