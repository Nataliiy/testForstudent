package com.periodical.trots.services;

import com.periodical.trots.entities.ReceiptEntity;
import com.periodical.trots.entities.StatusEntity;
import com.periodical.trots.repositories.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService testInstance;

    @Test
    public void shouldGetAllReceiptForAdmin() {
        List<ReceiptEntity> expectedList = new ArrayList<>();
        when(receiptRepository.findAll(Sort.by(Sort.Direction.ASC, "status"))).thenReturn(expectedList);

        List<ReceiptEntity> actualList = testInstance.getAllReceiptForAdmin();

        assertEquals(expectedList,actualList);
    }

    @Test
    public void shouldAcceptOrderByAdmin() {
        ReceiptEntity receiptEntity = mock(ReceiptEntity.class);
        StatusEntity statusEntity = mock(StatusEntity.class);
        when(receiptEntity.getId()).thenReturn(1);
        when(receiptRepository.getById(receiptEntity.getId())).thenReturn(receiptEntity);
        when(receiptRepository.save(receiptEntity)).thenReturn(receiptEntity);

        boolean result = testInstance.acceptOrderByAdmin(receiptEntity.getId(), statusEntity);

        assertTrue(result);
    }

    @Test
    public void shouldSaveReceipt() {
        ReceiptEntity receiptEntity = mock(ReceiptEntity.class);
        when(receiptEntity.getId()).thenReturn(1);
        when(receiptRepository.save(receiptEntity)).thenReturn(receiptEntity);

        Integer result = testInstance.saveReceipt(receiptEntity);

        assertEquals(receiptEntity.getId(), result);
    }

    @Test
    public void shouldGetReceiptById() {
        ReceiptEntity expectedReceipt = mock(ReceiptEntity.class);
        when(expectedReceipt.getId()).thenReturn(1);
        when(receiptRepository.getById(expectedReceipt.getId())).thenReturn(expectedReceipt);

        ReceiptEntity actualReceipt = testInstance.getReceiptById(expectedReceipt.getId());

        assertEquals(expectedReceipt, actualReceipt);
    }
}