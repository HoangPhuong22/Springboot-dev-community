package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserRoleById(Long id);
    void updateUser(User user);
    User checkUserByUserName(String userName, Long id);
    User checkUserByEmail(String email, Long id);
    User findUserByEmail(String email);
    void deleteUser(Long id);
}
