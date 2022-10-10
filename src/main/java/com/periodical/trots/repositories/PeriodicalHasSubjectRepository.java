package com.periodical.trots.repositories;

import com.periodical.trots.entities.PeriodicalHasSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalHasSubjectRepository extends JpaRepository<PeriodicalHasSubjectEntity, Integer> {

    @Query("select p from PeriodicalHasSubjectEntity p group by p.periodical.sellId")
    Page<PeriodicalHasSubjectEntity> findAllGroupBySellId(Pageable pageable);

    @Query("select p from PeriodicalHasSubjectEntity p where p.subject.themesOfBooks=:category")
    Page<PeriodicalHasSubjectEntity> findAllByCategory(Pageable pageable, @Param("category") String category);

    @Query("select p from PeriodicalHasSubjectEntity p where p.subject.themesOfBooks=:category and p.periodical.title like %:title%")
    Page<PeriodicalHasSubjectEntity> findAllByCategoryAAndPeriodicalTitle(Pageable pageable, @Param("category") String category, @Param("title") String title);

    @Query("select p from PeriodicalHasSubjectEntity p where p.periodical.title like %:title% group by p.periodical.sellId")
    Page<PeriodicalHasSubjectEntity> findAllByTitle(Pageable pageable, @Param("title") String title);
}
