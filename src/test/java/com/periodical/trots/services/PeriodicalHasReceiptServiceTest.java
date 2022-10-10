package com.periodical.trots.services;

import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.repositories.PeriodicalHasReceiptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeriodicalHasReceiptServiceTest {

    @InjectMocks
    private PeriodicalHasReceiptService testInstance;

    @Mock
    private PeriodicalHasReceiptRepository periodicalHasReceiptRepository;

    @Test
    public void shouldSaveOrderOfUser() {
        PeriodicalHasReceiptEntity periodicalHasReceiptEntity = mock(PeriodicalHasReceiptEntity.class);
        when(periodicalHasReceiptRepository.save(periodicalHasReceiptEntity)).thenReturn(periodicalHasReceiptEntity);

        boolean result = testInstance.saveOrder(periodicalHasReceiptEntity);

        assertTrue(result);
    }

    @Test
    public void shouldDeleteOrdersAfterSubscriptionEnd() {
        List<PeriodicalHasReceiptEntity> allReceipts = new ArrayList<>();
        when(periodicalHasReceiptRepository.findAll()).thenReturn(allReceipts);

        boolean result = testInstance.deleteOrdersAfterTime();

        assertTrue(result);
    }


    @Test
    public void shouldFindAllOrdersByUserId() {
        UserEntity user = mock(UserEntity.class);
        List<PeriodicalHasReceiptEntity> expectedOrders = new ArrayList<>();

        when(user.getId()).thenReturn(1);
        when(periodicalHasReceiptRepository.findAllOrdersOfUser(user.getId())).thenReturn(expectedOrders);

        List<PeriodicalHasReceiptEntity> actualOrders = testInstance.findAllReceiptOfUser(user.getId());

        assertEquals(expectedOrders, actualOrders);

    }

}