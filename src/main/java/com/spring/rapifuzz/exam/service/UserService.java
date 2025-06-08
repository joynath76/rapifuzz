package com.spring.rapifuzz.exam.service;

import com.spring.rapifuzz.exam.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByName(String userName);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    User getLoggedInUser();
    User updatePassword(User reqUser, User dbUser);
}
