package com.revature.PairProject.controllers;

import com.revature.PairProject.daos.ItemDAO;
import com.revature.PairProject.daos.UserDAO;
import com.revature.PairProject.models.Item;
import com.revature.PairProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemDAO itemDAO;

    private UserDAO userDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO, UserDAO userDAO) {
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }


    //this method will insert a new Item into the items table of the database
    @PostMapping("/{userId}")
    public ResponseEntity<Item> insertItem (@RequestBody Item item, @PathVariable int userId){

        //we want to send a user id int from postman, but we only have the User object in the Item class.
        //we have to send in the user id as a Path Variable and get the user by ID to attach to the Item.
        User u = userDAO.findById(userId).get();

        //Why .get()?^ findById() returns an OPTIONAL.  This basically means it may find the user or might not
        //In other words, it may be a User object, or it may be null.
        //.get() will return the User object if it exists.  If it doesn't, it will throw an exception.

        item.setUser(u); //attach the given User to the item object before we send it to the DB

        Item savedItem = itemDAO.save(item);  //Remember, save() also RETURNS the object saved

        return ResponseEntity.status(201).body(savedItem);
    }

    //This method will take an entire item object and replace an existing item with the new item object
    @PutMapping("/{userId}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item, @PathVariable int userId){

        User u = userDAO.findById(userId).get();

        item.setUser(u);

        Item savedItem = itemDAO.save(item);  //Updates and inserts use the SAME JPA METHOD, save()

        //how does the DB know if it's an update or an insert?
        //It looks for an existing PK.
        //if we send an Item with no PK, it knows to create a new item
        //if we send an Item with an EXISTING PK, it knows to update the existing item

        return ResponseEntity.ok(savedItem);

    }

    //This method will update ONLY the title of a item
    // The method uses <Object> in the return type here because we could return a <Item> or String.
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItemName(@RequestBody String title, @PathVariable int itemId){

        //get the item by ID, set the new title with setter, save it back to the DB

        //this time, we'll use Optionals as they were meant to be used - to prevent NullPointerExceptions
        Optional<Item> foundItem = itemDAO.findById(itemId);

        if (foundItem.isEmpty()){
            //This will return a String message telling the user what went wrong
            // The method uses <Object> in the return type here because we could return a <Item> or String.
            return ResponseEntity.status(404).body("No item found with ID of " + itemId);
        }

        //Extract the Item from the Optional
        Item item = foundItem.get();

        //Update the title, using the setter
        item.setNameOfItem(title);

        //Save the updated item back to the DB
        itemDAO.save(item);

        return ResponseEntity.accepted().body(item);

    }

    //This method will delete an item by its ID
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable int itemId){

        //first we'll get the item by ID to see if it exists
        //error message if not
        Optional<Item> foundItem = itemDAO.findById(itemId);

        if(foundItem.isEmpty()){
            return ResponseEntity.status(404).body("No item found with ID of " + itemId);
        }

        Item item = foundItem.get();

        //Now we can do the delete
        itemDAO.deleteById(itemId);

        //we could send back the entire item, but let's send a confirmation message instead
        return ResponseEntity.accepted().body("Item " + item.getNameOfItem() + " has been deleted.");

    }

    //method to select one item by its ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemByItemId(@PathVariable Integer itemId){

        //first we'll get the item by ID to see if it exists
        //error message if not
        Optional<Item> foundItem = itemDAO.findById(itemId);


        if(foundItem.isEmpty()){
            return ResponseEntity.status(404).body("No item found with ID of " + itemId);
        }


        return ResponseEntity.ok(foundItem);

    }

    //method to get all items from the table
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(){

        //not much error handling needed, since there's no user input
        List<Item> items = itemDAO.findAll();

        return ResponseEntity.ok(items);
    }




}
