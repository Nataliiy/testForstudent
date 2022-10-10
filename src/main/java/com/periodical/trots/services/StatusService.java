package com.periodical.trots.services;

import com.periodical.trots.entities.StatusEntity;
import com.periodical.trots.repositories.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public StatusEntity getStatusById(Integer statusId){
        return statusRepository.getById(statusId);
    }

    public StatusEntity save(StatusEntity status){
        return statusRepository.save(status);
    }
}
