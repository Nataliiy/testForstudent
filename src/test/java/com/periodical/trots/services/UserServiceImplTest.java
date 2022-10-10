package com.periodical.trots.services;

import com.periodical.trots.entities.UserEntity;
import com.periodical.trots.repositories.UserRepository;
import com.periodical.trots.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl testInstance;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void shouldSaveUser() {
        UserEntity user = mock(UserEntity.class);
        when(user.getPassword()).thenReturn("password");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        boolean userCreated = testInstance.save(user);

        assertTrue(userCreated);
    }

    @Test
    public void shouldGetAllUsers() {
        List<UserEntity> expectedList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedList);

        List<UserEntity> actualList = testInstance.getAll();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldFindUserByUsername() {
        UserEntity expectedUser = mock(UserEntity.class);
        when(expectedUser.getUsername()).thenReturn("customer");
        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(expectedUser);

        UserEntity actualUser = testInstance.findByUsername(expectedUser.getUsername());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldSaveUserByAdmin() {
        UserEntity user = mock(UserEntity.class);
        when(user.getPassword()).thenReturn("password");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        boolean userCreated = testInstance.save(user);

        assertTrue(userCreated);
    }

    @Test
    public void shouldBanUser() {
        UserEntity user = mock(UserEntity.class);
        when(user.getId()).thenReturn(1);
        when(userRepository.getById(user.getId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        boolean isBanned = testInstance.banUserById(user.getId());

        assertTrue(isBanned);
    }


    @Test
    public void shouldTopUpBalance() {
        UserEntity user = mock(UserEntity.class);
        when(user.getBalance()).thenReturn(BigDecimal.valueOf(200));
        when(user.getId()).thenReturn(1);
        when(userRepository.getById(user.getId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        BigDecimal actualBalance = testInstance.topUpBalance(BigDecimal.valueOf(200), user.getBalance(), user.getId());

        assertEquals(400, actualBalance.doubleValue(), 0.01);
    }

    @Test
    public void shouldUpdateBalance() {
        UserEntity user = mock(UserEntity.class);
        when(user.getBalance()).thenReturn(BigDecimal.valueOf(200));
        when(user.getUsername()).thenReturn("username");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        boolean isUpdated = testInstance.updateBalanceAfterPayment(user.getUsername(), user.getBalance());

        assertTrue(isUpdated);
    }

    @Test
    public void shouldFindUserById() {
        UserEntity expectedUser = mock(UserEntity.class);
        when(expectedUser.getId()).thenReturn(1);
        when(userRepository.getById(expectedUser.getId())).thenReturn(expectedUser);

        UserEntity actualUser = testInstance.findUserById(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldGetUserByEmail() {
        UserEntity expectedUser = mock(UserEntity.class);
        when(expectedUser.getEmail()).thenReturn("mail@gmail.com");
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(expectedUser);

        UserEntity actualUser = testInstance.findUserByEmail(expectedUser.getEmail());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldGetUserByTelephone() {
        UserEntity expectedUser = mock(UserEntity.class);
        when(expectedUser.getTelephone()).thenReturn("380999999999");
        when(userRepository.findByTelephone(expectedUser.getTelephone())).thenReturn(expectedUser);

        UserEntity actualUser = testInstance.findUserByTelephone(expectedUser.getTelephone());

        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void shouldDeleteUserById() {
        UserEntity user = new UserEntity();
        user.setId(1);

        boolean deleteUser = testInstance.deleteUserById(user.getId());

        assertTrue(deleteUser);
    }
}