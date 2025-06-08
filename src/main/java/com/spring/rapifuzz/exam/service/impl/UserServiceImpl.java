package com.spring.rapifuzz.exam.service.impl;

import com.spring.rapifuzz.exam.entity.Address;
import com.spring.rapifuzz.exam.entity.User;
import com.spring.rapifuzz.exam.exception.DuplicateRequestException;
import com.spring.rapifuzz.exam.exception.ResourceNotFoundException;
import com.spring.rapifuzz.exam.repo.UserRepository;
import com.spring.rapifuzz.exam.service.AbstractService;
import com.spring.rapifuzz.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        Optional<User> userByUserName = userRepository.findUserByUserName(user.getUserName());
        if (userByUserName.isPresent()) {
            throw new DuplicateRequestException("User already exists");
        }
        user.setCreateDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findUserByStatus("A");
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return user;
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userName: " + userName));
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUserName(userDetails.getUserName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setUpdateDate(LocalDateTime.now());
        user.setStatus(userDetails.getStatus());
        user.setPassword(userDetails.getPassword());
        user.setUserType(userDetails.getUserType());
        Address address = user.getAddress();
        Address userAddress = userDetails.getAddress();
        address.setAddress(userAddress.getAddress());
        address.setPinCode(userAddress.getPinCode());
        address.setCity(userAddress.getCity());
        address.setCountry(userAddress.getCountry());
        address.setState(userAddress.getState());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getLoggedInUser() {
        return userRepository.findById(getLoggedInUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + getLoggedInUserId()));
    }

    @Override
    public User updatePassword(User reqUser, User dbUser) {
        dbUser.setPassword(reqUser.getPassword());
        userRepository.save(dbUser);
        return dbUser;
    }
}

