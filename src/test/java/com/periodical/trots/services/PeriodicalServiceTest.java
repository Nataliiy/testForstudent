package com.periodical.trots.services;

import com.periodical.trots.entities.PeriodicalEntity;
import com.periodical.trots.repositories.PeriodicalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeriodicalServiceTest {

    @Mock
    private PeriodicalRepository periodicalRepository;

    @InjectMocks
    private PeriodicalService testInstance;

    @Test
    public void shouldAddPeriodical() {
        PeriodicalEntity periodicalEntity = mock(PeriodicalEntity.class);
        when(periodicalEntity.getSellId()).thenReturn(5);
        when(periodicalRepository.save(periodicalEntity)).thenReturn(periodicalEntity);

        Integer actual = testInstance.addPeriodical(periodicalEntity);

        assertThat(actual).isEqualTo(5);
    }

    @Test
    public void shouldGetPeriodicalById() {
        PeriodicalEntity expectedPeriodical = mock(PeriodicalEntity.class);
        when(expectedPeriodical.getSellId()).thenReturn(1);
        when(periodicalRepository.getBySellId(expectedPeriodical.getSellId())).thenReturn(expectedPeriodical);

        PeriodicalEntity actualPeriodical = testInstance.getPeriodicalById(expectedPeriodical.getSellId());

        assertThat(actualPeriodical).isEqualTo(expectedPeriodical);
    }

    @Test
    public void shouldUpdatePeriodical() {
        PeriodicalEntity initialPeriodical = mock(PeriodicalEntity.class);
        PeriodicalEntity periodicalForUpdate = mock(PeriodicalEntity.class);

        when(periodicalRepository.getBySellId(1)).thenReturn(initialPeriodical);
        when(periodicalRepository.save(initialPeriodical)).thenReturn(initialPeriodical);

        boolean actualResult = testInstance.updatePeriodical(1, periodicalForUpdate);

        assertTrue(actualResult);
    }

    @Test
    public void shouldGetAllPeriodicals() {
        List<PeriodicalEntity> expectedPeriodicals = new ArrayList<>();
        when(periodicalRepository.findAll()).thenReturn(expectedPeriodicals);

        List<PeriodicalEntity> actualPeriodicals = testInstance.getAllPeriodicals();

        assertEquals(expectedPeriodicals, actualPeriodicals);
    }

    @Test
    public void shouldDeletePeriodicalTest() {
        PeriodicalEntity periodical = mock(PeriodicalEntity.class);
        when(periodical.getSellId()).thenReturn(1);

        boolean deletePeriodical = testInstance.deletePeriodical(periodical.getSellId());

        assertTrue(deletePeriodical);
    }

    @Test
    public void shouldGetPeriodicalByTitle(){
        PeriodicalEntity expectedPeriodical = mock(PeriodicalEntity.class);

        when(expectedPeriodical.getTitle()).thenReturn("testTile");
        when(periodicalRepository.getByTitle(expectedPeriodical.getTitle())).thenReturn(expectedPeriodical);

        PeriodicalEntity actualPeriodical = testInstance.getPeriodicalByTitle(expectedPeriodical.getTitle());

        assertEquals(expectedPeriodical, actualPeriodical);
    }
}