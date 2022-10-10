package com.periodical.trots.entities;

import java.math.BigDecimal;

public class Cart {
    private Integer months;

    private PeriodicalEntity periodical;

    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PeriodicalEntity getPeriodical() {
        return periodical;
    }

    public void setPeriodical(PeriodicalEntity periodical) {
        this.periodical = periodical;
    }

    public Cart() {
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }
}
