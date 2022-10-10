package com.periodical.trots.services;

import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import com.periodical.trots.repositories.PeriodicalHasReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PeriodicalHasReceiptService {

    private final PeriodicalHasReceiptRepository periodicalHasReceiptRepository;

    public PeriodicalHasReceiptService(PeriodicalHasReceiptRepository periodicalHasReceiptRepository) {
        this.periodicalHasReceiptRepository = periodicalHasReceiptRepository;
    }

    public List<PeriodicalHasReceiptEntity> findAllReceiptOfUser(Integer userId){
        return periodicalHasReceiptRepository.findAllOrdersOfUser(userId);
    }

    public boolean saveOrder(PeriodicalHasReceiptEntity periodicalHasReceiptEntity){
        periodicalHasReceiptRepository.save(periodicalHasReceiptEntity);
        return true;
    }

    public boolean deleteOrdersAfterTime(){
        List<PeriodicalHasReceiptEntity> list = periodicalHasReceiptRepository.findAll();
        Date date = new Date();
        long currentDate = date.getTime();
        for (PeriodicalHasReceiptEntity p : list){
            long endDate =p.getmReceipt().getCreateTime().getTime()+(((long) (12 / p.getPeriodical().getPeriodicityPerYear()) *p.getNumberOfMonth())*30L*24L*60L*60L*1000L);
            if (endDate<currentDate){
                periodicalHasReceiptRepository.delete(p);
            }
        }
        return true;
    }


}
