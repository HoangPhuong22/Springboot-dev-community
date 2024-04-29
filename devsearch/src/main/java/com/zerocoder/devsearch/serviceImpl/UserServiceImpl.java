package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.UserDAO;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.UserService;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        try
        {
            User user = userDAO.getUserByUserName(userName);
            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try
        {
            User user = userDAO.getUserByEmail(email);
            return user;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public User getUserRoleById(Long id) {
        return userDAO.getUserRoleById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public User checkUserByUserName(String userName, Long id) {
        try
        {
            return userDAO.checkUserByUserName(userName, id);
        }catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public User checkUserByEmail(String email, Long id) {
        try
        {
            return userDAO.checkUserByEmail(email, id);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}
