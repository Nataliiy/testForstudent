package com.periodical.trots.services.impl;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.repositories.UserRepository;
import com.periodical.trots.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean save(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("customer");
        user.setBalance(BigDecimal.ZERO);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Method returns all users.
     *
     * @return the list
     */
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Mathod saves user from admin page.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean saveUserByAdmin(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBalance(BigDecimal.ZERO);
        userRepository.save(user);
        return true;
    }

    /**
     * Mathod bans user from admin page.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean banUserById(Integer userId) {
        UserEntity user = userRepository.getById(userId);
        if (user.getBanStatus() == null) {
            user.setBanStatus("banned");
        } else {
            user.setBanStatus(null);
        }
        userRepository.save(user);
        return true;
    }

    /**
     * Method delete user from admin page.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
        return true;
    }

    /**
     * Method updates balance of user after topping up.
     *
     * @param balance        the balance
     * @param currentBalance the current balance
     * @param userId         the user id
     * @return the double
     */
    public BigDecimal topUpBalance(BigDecimal balance, BigDecimal currentBalance, Integer userId) {
        UserEntity user = userRepository.getById(userId);
        BigDecimal updatedBalance = balance.add(currentBalance);
        user.setBalance(updatedBalance);
        userRepository.save(user);
        return updatedBalance;
    }

    /**
     * Method updates balance of user after he pay for order.
     *
     * @param username      the username
     * @param actualBalance the actual balance
     * @return the boolean
     */
    public boolean updateBalanceAfterPayment(String username, BigDecimal actualBalance) {
        UserEntity user = userRepository.findByUsername(username);
        user.setBalance(actualBalance);
        userRepository.save(user);
        return true;
    }

    /**
     * Method finds user by ID.
     *
     * @param userId the user id
     * @return the user entity
     */
    public UserEntity findUserById(Integer userId) {
        return userRepository.getById(userId);
    }

    /**
     * Method finds user by email.
     *
     * @param email the email
     * @return the user entity
     */
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Method finds user by telephone.
     *
     * @param telephone the telephone
     * @return the user entity
     */
    public UserEntity findUserByTelephone(String telephone) {
        return userRepository.findByTelephone(telephone);
    }


}
