package com.periodical.trots.services;

import com.periodical.trots.entities.PeriodicalHasSubjectEntity;
import com.periodical.trots.repositories.PeriodicalHasSubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PeriodicalHasSubjectService {

    private final PeriodicalHasSubjectRepository periodicalHasSubjectRepository;

    public PeriodicalHasSubjectService(PeriodicalHasSubjectRepository periodicalHasSubjectRepository) {
        this.periodicalHasSubjectRepository = periodicalHasSubjectRepository;
    }

    public boolean save(PeriodicalHasSubjectEntity periodicalHasSubject) {
        periodicalHasSubjectRepository.save(periodicalHasSubject);
        return true;
    }

    public Page<PeriodicalHasSubjectEntity> findAllWithSort(Integer page, String sort, String order) {
        if (order.equals("ASC")) {
            return periodicalHasSubjectRepository.findAllGroupBySellId(PageRequest.of(page, 12, Sort.by(sort).ascending()));
        }
        return periodicalHasSubjectRepository.findAllGroupBySellId(PageRequest.of(page, 12, Sort.by(sort).descending()));
    }

    public Page<PeriodicalHasSubjectEntity> findAllWithCategory(Integer page, String sort, String category, String order) {
        if (order.equals("ASC")) {
            return periodicalHasSubjectRepository.findAllByCategory(PageRequest.of(page, 12, Sort.by(sort).ascending()), category);
        }
        return periodicalHasSubjectRepository.findAllByCategory(PageRequest.of(page, 12, Sort.by(sort).descending()), category);
    }

    public Page<PeriodicalHasSubjectEntity> findAllWithCategoryAndTitle(Integer page, String sort, String category, String title, String order) {
        if (order.equals("ASC")) {
            return periodicalHasSubjectRepository.findAllByCategoryAAndPeriodicalTitle(PageRequest.of(page, 12, Sort.by(sort).ascending()), category, title);
        }
        return periodicalHasSubjectRepository.findAllByCategoryAAndPeriodicalTitle(PageRequest.of(page, 12, Sort.by(sort).descending()), category, title);
    }

    public Page<PeriodicalHasSubjectEntity> findAllByTitle(Integer page, String sort, String title, String order) {
        if (order.equals("ASC")) {
            return periodicalHasSubjectRepository.findAllByTitle(PageRequest.of(page, 12, Sort.by(sort).ascending()), title);
        }
        return periodicalHasSubjectRepository.findAllByTitle(PageRequest.of(page, 12, Sort.by(sort).descending()), title);
    }

    public Page<PeriodicalHasSubjectEntity> findAllByTitle(Integer page, String title) {
        return periodicalHasSubjectRepository.findAllByTitle(PageRequest.of(page, 12), title);
    }

    public Page<PeriodicalHasSubjectEntity> findAllWithCategoryAndTitle(Integer page, String category, String title) {
        return periodicalHasSubjectRepository.findAllByCategoryAAndPeriodicalTitle(PageRequest.of(page, 12), category, title);
    }

    public Page<PeriodicalHasSubjectEntity> findAllWithCategory(Integer page, String category) {
        return periodicalHasSubjectRepository.findAllByCategory(PageRequest.of(page, 12), category);
    }

    public Page<PeriodicalHasSubjectEntity> findAll(Integer page) {
        return periodicalHasSubjectRepository.findAllGroupBySellId(PageRequest.of(page, 12));
    }


}
