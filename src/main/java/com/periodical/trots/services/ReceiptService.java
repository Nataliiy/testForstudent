package com.periodical.trots.services;

import com.periodical.trots.entities.ReceiptEntity;
import com.periodical.trots.entities.StatusEntity;
import com.periodical.trots.repositories.ReceiptRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public List<ReceiptEntity> getAllReceiptForAdmin(){
        return receiptRepository.findAll(Sort.by(Sort.Direction.ASC, "status"));
    }

    public boolean acceptOrderByAdmin(Integer receiptId, StatusEntity status){
        ReceiptEntity receipt = receiptRepository.getById(receiptId);
        receipt.setStatus(status);
        receiptRepository.save(receipt);
        return true;
    }

    public boolean discardOrderByAdmin(Integer receiptId, StatusEntity status){
        ReceiptEntity receipt = receiptRepository.getById(receiptId);
        receipt.setStatus(status);
        receiptRepository.save(receipt);
        return true;
    }

    public Integer saveReceipt(ReceiptEntity receipt){
        return receiptRepository.save(receipt).getId();
    }

    public ReceiptEntity getReceiptById(Integer receiptId){
        return receiptRepository.getById(receiptId);
    }

    public List<ReceiptEntity> getReceiptsForDailyOrder(Date createTime){
        return receiptRepository.findAllByStatusStatusNameAndCreateTime("accepted", createTime);
    }

}
