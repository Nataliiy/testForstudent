package com.periodical.trots.repositories;

import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodicalHasReceiptRepository extends JpaRepository<PeriodicalHasReceiptEntity, Integer> {

    @Query("select r from PeriodicalHasReceiptEntity r where r.mReceipt.user.id = :userId order by r.mReceipt.status.statusName asc")
    List<PeriodicalHasReceiptEntity> findAllOrdersOfUser(@Param("userId") Integer userId);
}
