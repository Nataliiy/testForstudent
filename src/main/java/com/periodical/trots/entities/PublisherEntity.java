package com.periodical.trots.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "publisher", indexes = {@Index(name = "name_UNIQUE", columnList = "name", unique = true)})
@Entity
public class PublisherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Specify this field correctly")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "telephone_number", length = 14)
    private String telephoneNumber;

    public PublisherEntity(Integer id) {
        this.id = id;
    }

    public PublisherEntity() {
    }

    public PublisherEntity(Integer id, String name, String telephoneNumber) {
        this.id = id;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}