package com.periodical.trots.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "receipt", indexes = {@Index(name = "fk_receipt_user1_idx", columnList = "user_id"), @Index(name = "fk_receipt_status1_idx", columnList = "status_id")})
@Entity
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @Column(name = "adress", nullable = false, length = 1024)
    private String adress;

    @Column(name = "telephone_number", nullable = false, length = 14)
    private String telephoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mReceipt")
    Set<PeriodicalHasReceiptEntity> receiptEntities;

    public ReceiptEntity(Integer id) {
        this.id = id;
    }

    public ReceiptEntity() {
    }

    public ReceiptEntity(Integer id, String name, String surname, String adress, String telephoneNumber, StatusEntity status, UserEntity user, String email, Date createTime) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.telephoneNumber = telephoneNumber;
        this.status = status;
        this.user = user;
        this.email = email;
        this.createTime = createTime;
    }

    public Set<PeriodicalHasReceiptEntity> getReceiptEntities() {
        return receiptEntities;
    }

    public void setReceiptEntities(Set<PeriodicalHasReceiptEntity> receiptEntities) {
        this.receiptEntities = receiptEntities;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    @Override
    public String toString() {
        return "" + receiptEntities + "";
    }
}