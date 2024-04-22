package com.revature.PairProject.controllers;

import com.revature.PairProject.models.User;
import com.revature.PairProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Makes class a bean, converts every response to JSON
@RequestMapping("/users")
public class UserController {

    private UserService userService;




    //Constructor Injection (because this controller depends on the UserDAO)

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //method that inserts a new user (POST request)
    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user){
        User u = userService.createUser(user);
        return ResponseEntity.status(201).body(u);

    }

    //method to get all users (GET request)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //method to get user by username (GET request with a Path Variable, error checking)
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){

        //if user is not found, User u will be null, else it will contain given username
        User u = userService.getUserByUsername(username);

        if (u == null){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        //if user exists, return a ResponseEntity with status 200 (ok) and user in the body
        return ResponseEntity.ok(u);
    }


    //This method will replace an existing user with the new user object
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username){
        User u = userService.updateUser(username, user);
        return ResponseEntity.ok(u);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }



    //method to get items that belong to a certain user
//    @GetMapping("/{username}/items")
//    public ResponseEntity<List<Item>>  getUserItemsById(@PathVariable String username) {
//
//        User u = userDAO.findByUsername(username);
//
//        if (u == null){
//            //return a ResponseEntity with status 404 (not found)
//            return ResponseEntity.notFound().build();
//        }
//
//        List<Item> items = itemDAO.findByUser_UserId(u.getUserId());
//
//
//        return ResponseEntity.ok(items);
//
//
//
//    }








}
