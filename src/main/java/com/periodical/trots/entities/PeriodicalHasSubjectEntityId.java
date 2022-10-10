package com.periodical.trots.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PeriodicalHasSubjectEntityId implements Serializable {
    private static final long serialVersionUID = 569355759347814802L;
    @Column(name = "periodical_id", nullable = false)
    private Integer periodicalId;

    @Column(name = "subject_id", nullable = false)
    private Integer subjectId;

    public PeriodicalHasSubjectEntityId() {
    }

    public PeriodicalHasSubjectEntityId(Integer periodicalId, Integer subjectId) {
        this.periodicalId = periodicalId;
        this.subjectId = subjectId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(Integer periodicalId) {
        this.periodicalId = periodicalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodicalId, subjectId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeriodicalHasSubjectEntityId entity = (PeriodicalHasSubjectEntityId) o;
        return Objects.equals(this.periodicalId, entity.periodicalId) && Objects.equals(this.subjectId, entity.subjectId);
    }
}