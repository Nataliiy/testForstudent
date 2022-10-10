package com.periodical.trots.services;

import com.periodical.trots.entities.PublisherEntity;
import com.periodical.trots.repositories.PublisherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService testInstance;

    @Test
    public void shouldSavePublisher() {
        PublisherEntity initialPublisherEntity = mock(PublisherEntity.class);
        when(initialPublisherEntity.getId()).thenReturn(1);
        when(publisherRepository.save(initialPublisherEntity)).thenReturn(initialPublisherEntity);

        int actual = testInstance.save(initialPublisherEntity);

        assertEquals(1, actual);
    }

    @Test
    public void shouldFindAllPublisher() {
        List<PublisherEntity> expectedList = new ArrayList<>();
        when(publisherRepository.findAll()).thenReturn(expectedList);

        List<PublisherEntity> actualList = testInstance.findAll();

        Assertions.assertEquals(expectedList, actualList);
    }
}