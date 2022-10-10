package com.periodical.trots.services;

import com.periodical.trots.entities.PeriodicalHasSubjectEntity;
import com.periodical.trots.repositories.PeriodicalHasSubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeriodicalHasSubjectServiceTest {

    @InjectMocks
    private PeriodicalHasSubjectService testInstance;

    @Mock
    private PeriodicalHasSubjectRepository periodicalHasSubjectRepository;

    @Test
    public void shouldSaveSubjectOfPeriodical() {
        PeriodicalHasSubjectEntity periodicalHasSubject = mock(PeriodicalHasSubjectEntity.class);
        when(periodicalHasSubjectRepository.save(periodicalHasSubject)).thenReturn(periodicalHasSubject);

        boolean result = testInstance.save(periodicalHasSubject);

        assertTrue(result);
    }

    @Test
    public void testforTest(){
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());
    }

}