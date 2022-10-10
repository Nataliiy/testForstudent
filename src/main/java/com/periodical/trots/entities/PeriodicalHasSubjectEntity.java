package com.periodical.trots.entities;

import javax.persistence.*;

@Table(name = "periodical_has_subject", indexes = {@Index(name = "fk_periodical_has_subject_subject1_idx", columnList = "subject_id"), @Index(name = "fk_periodical_has_subject_periodical_idx", columnList = "periodical_id")})
@Entity
public class PeriodicalHasSubjectEntity {
    @EmbeddedId
    private PeriodicalHasSubjectEntityId id;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    SubjectEntity subject;

    @ManyToOne
    @MapsId("periodicalId")
    @JoinColumn(name = "periodical_id")
    PeriodicalEntity periodical;

    public PeriodicalHasSubjectEntity() {
    }

    public PeriodicalHasSubjectEntity(PeriodicalHasSubjectEntityId id, SubjectEntity subject, PeriodicalEntity periodical) {
        this.id = id;
        this.subject = subject;
        this.periodical = periodical;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public PeriodicalEntity getPeriodical() {
        return periodical;
    }

    public void setPeriodical(PeriodicalEntity periodical) {
        this.periodical = periodical;
    }

    public PeriodicalHasSubjectEntityId getId() {
        return id;
    }

    public void setId(PeriodicalHasSubjectEntityId id) {
        this.id = id;
    }
}