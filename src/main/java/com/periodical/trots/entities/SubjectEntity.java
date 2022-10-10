package com.periodical.trots.entities;

import javax.persistence.*;
import java.util.Set;

@Table(name = "subject")
@Entity
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "themes_of_books", nullable = false)
    private String themesOfBooks;

    @OneToMany(mappedBy = "subject")
    Set<PeriodicalHasSubjectEntity> periodicalHasSubject;

    public SubjectEntity(Integer id) {
        this.id = id;
    }

    public SubjectEntity() {
    }

    public SubjectEntity(Integer id, String themesOfBooks) {
        this.id = id;
        this.themesOfBooks = themesOfBooks;
    }

    public Set<PeriodicalHasSubjectEntity> getPeriodicalHasSubject() {
        return periodicalHasSubject;
    }

    public void setPeriodicalHasSubject(Set<PeriodicalHasSubjectEntity> periodicalHasSubject) {
        this.periodicalHasSubject = periodicalHasSubject;
    }

    public String getThemesOfBooks() {
        return themesOfBooks;
    }

    public void setThemesOfBooks(String themesOfBooks) {
        this.themesOfBooks = themesOfBooks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}