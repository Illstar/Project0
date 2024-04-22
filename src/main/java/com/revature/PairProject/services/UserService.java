package com.revature.PairProject.services;

import com.revature.PairProject.models.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public User getUserByUsername(String username);

    public User updateUser(String username, User user);
    public void deleteUser(String username);
}
