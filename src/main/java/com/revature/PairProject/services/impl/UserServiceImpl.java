package com.revature.PairProject.services.impl;

import com.revature.PairProject.daos.ItemDAO;
import com.revature.PairProject.daos.UserDAO;
import com.revature.PairProject.models.Item;
import com.revature.PairProject.models.User;
import com.revature.PairProject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User createUser(User user) {
        User u = userDAO.save(user);

        if (u == null){
            throw new RuntimeException("User was not created");
        }

        return u;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User updateUser(String username, User user) {
        User u1 = userDAO.findByUsername(username);

        u1.setUsername(user.getUsername());
        u1.setPassword(user.getPassword());

        User u = userDAO.save(u1);
        return u;
    }

    @Override
    public void deleteUser(String username) {
        userDAO.deleteByUsername(username);

    }


}
