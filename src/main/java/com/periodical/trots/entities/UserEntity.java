package com.periodical.trots.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "user", indexes = {@Index(name = "email_UNIQUE", columnList = "email", unique = true), @Index(name = "telephone_UNIQUE", columnList = "telephone", unique = true), @Index(name = "username_UNIQUE", columnList = "username", unique = true)})
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 16)
    @NotBlank(message = "{error.username}")
    @Pattern(regexp = "[a-zA-Z0-9]{6,18}", message = "{error.username}")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{error.email}")
    private String email;

    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "telephone", nullable = false, unique = true, length = 14)
    @Pattern(regexp = "[0-9]{11,12}", message = "{error.telephone}")
    private String telephone;

    @Column(name = "name", nullable = false, length = 45)
    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]{1,25}", message = "{error.name}")
    private String name;

    @Column(name = "surname", nullable = false, length = 45)
    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]{1,25}", message = "{error.surname}")
    private String surname;

    @Column(name = "ban_status", length = 45)
    private String banStatus;

    @Column(name = "balance", columnDefinition="Decimal(9,2) default '0.00'")
    private BigDecimal balance;

    @Column(name = "role", length = 45)
    private String role;

    @NotBlank(message = "Specify this value correctly")
    @Column(name = "address", length = 1024)
    @Length(max = 1024, message = "{error.address}")
    private String address;

    public UserEntity() {
    }

    public UserEntity(Integer id, String username, String email, String password, String telephone, String name, String surname, String role, String address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(String banStatus) {
        this.banStatus = banStatus;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}