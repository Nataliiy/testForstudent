package com.periodical.trots.services;

import com.periodical.trots.entities.PublisherEntity;
import com.periodical.trots.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherEntity> findAll(){
        return publisherRepository.findAll();
    }

    public Integer save(PublisherEntity publisherEntity){
        return publisherRepository.save(publisherEntity).getId();
    }
}
