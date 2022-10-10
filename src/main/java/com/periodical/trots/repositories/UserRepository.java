package com.periodical.trots.repositories;

import com.periodical.trots.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity findByTelephone(String telephone);
}