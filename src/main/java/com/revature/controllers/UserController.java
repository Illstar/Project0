package com.revature.controllers;

import com.revature.daos.ItemDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Item;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Makes class a bean, converts every response to JSON
@RequestMapping("/users")
public class UserController {

    private UserDAO userDAO;


    private ItemDAO itemDAO;

    //Constructor Injection (because this controller depends on the UserDAO)

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //method that inserts a new user (POST request)
    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user){

        User u = userDAO.save(user);

        if (u == null){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.status(201).body(u);


    }

    //method to get all users (GET request)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        //not much error handling needed, since there's no user input
        List<User> users = userDAO.findAll();

        return ResponseEntity.ok(users);
    }

    //method to get user by username (GET request with a Path Variable, error checking)
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){

        //if user is not found, User u will be null, else it will contain given username
        User u = userDAO.findByUsername(username);

        if (u == null){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        //if user exists, return a ResponseEntity with status 200 (ok) and user in the body
        return ResponseEntity.ok(u);
    }

    /*

    UNCOMMENTING THIS BREAKS FIND BY USERNAME

    //method to get one user by its ID
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserByUserId(@PathVariable Integer userId){

        //first we'll get the item by ID to see if it exists
        //error message if not
        Optional<User> foundUser = userDAO.findById(userId);


        if(foundUser.isEmpty()){
            return ResponseEntity.status(404).body("No user found with ID of " + userId);
        }


        return ResponseEntity.ok(foundUser);

    }

     */




    //This method will replace an existing user with the new user object
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateGame(@RequestBody User user, @PathVariable int userId){

        User u1 = userDAO.findById(userId).get();

        u1.setUsername(user.getUsername());
        u1.setPassword(user.getPassword());

        User u = userDAO.save(u1);  //Updates and inserts use the SAME JPA METHOD, save()

        //how does the DB know if it's an update or an insert?
        //It looks for an existing PK.
        //if we send an Item with no PK, it knows to create a new item
        //if we send an Item with an EXISTING PK, it knows to update the existing item

        return ResponseEntity.ok(u);

    }



    //method to get items that belong to a certain user
    @GetMapping("/{username}/items")
    public ResponseEntity<List<Item>>  getUserItemsById(@PathVariable String username) {

        User u = userDAO.findByUsername(username);

        if (u == null){
            //return a ResponseEntity with status 404 (not found)
            return ResponseEntity.notFound().build();
        }

        List<Item> items = itemDAO.findByUser_UserId(u.getUserId());


        return ResponseEntity.ok(items);



    }








}
