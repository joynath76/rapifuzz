package com.spring.rapifuzz.exam.repo;

import com.spring.rapifuzz.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByStatus(String status);
    Optional<User> findUserByUserName(String userName);
}

