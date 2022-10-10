package com.periodical.trots.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Table(name = "periodical", indexes = {@Index(name = "idx_periodical_title", columnList = "title"), @Index(name = "fk_periodical_publisher1_idx", columnList = "publisher_id")}, uniqueConstraints = {@UniqueConstraint(name = "title_UNIQUE", columnNames = {"title"})})
@Entity
public class PeriodicalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sell_id", nullable = false)
    private Integer sellId;

    @Column(name = "title", nullable = false, unique = true, length = 125)
    @Pattern(regexp = "[A-Za-z0-9_ ]{2,64}", message = "{error.title}")
    private String title;

    @NotNull(message = "{error.numberOfPages}")
    @Positive(message = "{error.numberOfPages}")
    @Min(message = "{error.numberOfPages}", value = 1)
    @Max(value = 1000, message = "{error.numberOfPages}")
    @Column(name = "number_of_pages", nullable = false)
    private Integer numberOfPages;

    @Column(name = "periodicity_per_year", nullable = false)
    private Integer periodicityPerYear;

    @NotNull(message = "{error.percentageOfAdvertising}")
    @Positive(message = "{error.percentageOfAdvertising}")
    @Min(message = "{error.percentageOfAdvertising}", value = 0)
    @Max(value = 100, message = "{error.percentageOfAdvertising}")
    @Column(name = "percentage_of_advertising", nullable = false)
    private Integer percentageOfAdvertising;

    @NotNull(message = "{error.pricePerMonth}")
    @Positive(message = "{error.pricePerMonth}")
    @Min(message = "{error.pricePerMonth}", value = 1)
    @Column(name = "price_per_month", nullable = false)
    private BigDecimal pricePerMonth;

    @Length(message = "{error.description}", min = 5, max = 1000)
    @NotBlank(message = "{error.description}")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "{error.rating}")
    @Max(message = "{error.rating}", value = 5)
    @Min(message = "{error.rating}", value = 0)
    @Column(name = "rating")
    private Double rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private PublisherEntity publisher;

    @Column(name = "images", nullable = false, length = 500)
    private String images;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "periodical")
    Set<PeriodicalHasReceiptEntity> receiptEntities;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "periodical")
    Set<PeriodicalHasSubjectEntity> periodicalHasSubject;

    public PeriodicalEntity(Integer sellId) {
        this.sellId = sellId;
    }

    public PeriodicalEntity() {
    }

    public PeriodicalEntity(Integer id, String title, Integer numberOfPages, Integer periodicityPerYear, Integer percentageOfAdvertising, BigDecimal pricePerMonth, String description, Double rating, PublisherEntity publisher, String images) {
        this.sellId = id;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.periodicityPerYear = periodicityPerYear;
        this.percentageOfAdvertising = percentageOfAdvertising;
        this.pricePerMonth = pricePerMonth;
        this.description = description;
        this.rating = rating;
        this.publisher = publisher;
        this.images = images;
    }

    public Set<PeriodicalHasReceiptEntity> getReceiptEntities() {
        return receiptEntities;
    }

    public void setReceiptEntities(Set<PeriodicalHasReceiptEntity> receiptEntities) {
        this.receiptEntities = receiptEntities;
    }

    public Set<PeriodicalHasSubjectEntity> getPeriodicalHasSubject() {
        return periodicalHasSubject;
    }

    public void setPeriodicalHasSubject(Set<PeriodicalHasSubjectEntity> periodicalHasSubject) {
        this.periodicalHasSubject = periodicalHasSubject;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Integer getPercentageOfAdvertising() {
        return percentageOfAdvertising;
    }

    public void setPercentageOfAdvertising(Integer percentageOfAdvertising) {
        this.percentageOfAdvertising = percentageOfAdvertising;
    }

    public Integer getPeriodicityPerYear() {
        return periodicityPerYear;
    }

    public void setPeriodicityPerYear(Integer periodicityPerYear) {
        this.periodicityPerYear = periodicityPerYear;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }
}