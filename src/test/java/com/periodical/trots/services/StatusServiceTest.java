package com.periodical.trots.services;

import com.periodical.trots.entities.StatusEntity;
import com.periodical.trots.repositories.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    @InjectMocks
    private StatusService testInstance;

    @Mock
    private StatusRepository statusRepository;

    @Test
    public void shouldGetStatusById() {
        StatusEntity expectedStatus = mock(StatusEntity.class);
        when(expectedStatus.getId()).thenReturn(1);
        when(statusRepository.getById(expectedStatus.getId())).thenReturn(expectedStatus);

        StatusEntity actualStatus = testInstance.getStatusById(expectedStatus.getId());

        assertEquals(expectedStatus, actualStatus);
    }
}