package com.periodical.trots.services;

import com.periodical.trots.entities.SubjectEntity;
import com.periodical.trots.repositories.SubjectRepository;
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
public class SubjectServiceTest {

    @InjectMocks
    private SubjectService testInstance;

    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void shouldFindAllSubjects() {
        List<SubjectEntity> expectedList = new ArrayList<>();
        when(subjectRepository.findAll()).thenReturn(expectedList);

        List<SubjectEntity> actualList = testInstance.findAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldSaveSubject() {
        SubjectEntity subjectEntity = mock(SubjectEntity.class);
        when(subjectEntity.getId()).thenReturn(1);
        when(subjectRepository.save(subjectEntity)).thenReturn(subjectEntity);

        Integer result = testInstance.save(subjectEntity);

        assertEquals(subjectEntity.getId(), result);
    }
}