package com.periodical.trots.repositories;

import com.periodical.trots.entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Integer> {

    List<ReceiptEntity> findAllByStatusStatusNameAndCreateTime(String status_statusName, Date createTime);
}
