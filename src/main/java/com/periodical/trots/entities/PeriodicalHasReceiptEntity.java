package com.periodical.trots.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "periodical_has_receipt", indexes = {@Index(name = "fk_periodical_has_receipt_periodical1_idx", columnList = "periodical_sell_id"), @Index(name = "fk_periodical_has_receipt_receipt1_idx", columnList = "receipt_id")})
@Entity
public class PeriodicalHasReceiptEntity {
    @EmbeddedId
    private PeriodicalHasReceiptEntityId id;

    @Column(name = "price_per_month", nullable = false, precision = 4, scale = 2)
    private BigDecimal pricePerMonth;

    @Column(name = "number_of_month", nullable = false, length = 45)
    private Integer numberOfMonth;

    @ManyToOne
    @MapsId("receiptId")
    @JoinColumn(name = "receipt_id")
    ReceiptEntity mReceipt;

    @ManyToOne
    @MapsId("periodicalSellId")
    @JoinColumn(name = "periodical_sell_id")
    PeriodicalEntity periodical;

    public PeriodicalHasReceiptEntity() {
    }

    public PeriodicalHasReceiptEntity(PeriodicalHasReceiptEntityId id, BigDecimal pricePerMonth, Integer numberOfMonth) {
        this.id = id;
        this.pricePerMonth = pricePerMonth;
        this.numberOfMonth = numberOfMonth;
    }

    public ReceiptEntity getmReceipt() {
        return mReceipt;
    }

    public void setmReceipt(ReceiptEntity mReceipt) {
        this.mReceipt = mReceipt;
    }

    public PeriodicalEntity getPeriodical() {
        return periodical;
    }

    public void setPeriodical(PeriodicalEntity periodical) {
        this.periodical = periodical;
    }

    public Integer getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(Integer numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public PeriodicalHasReceiptEntityId getId() {
        return id;
    }

    public void setId(PeriodicalHasReceiptEntityId id) {
        this.id = id;
    }
}