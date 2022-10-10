package com.periodical.trots.repositories;

import com.periodical.trots.entities.PeriodicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalRepository  extends JpaRepository<PeriodicalEntity, Integer> {

    PeriodicalEntity getBySellId(Integer periodicalId);

    PeriodicalEntity getByTitle(String title);

}
