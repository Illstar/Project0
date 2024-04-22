package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {


    //this will break the server on load
    //for the method to get items that belong to a certain user
    //this is the last thing but does not work

    List<Item> findByUser_UserId(Integer userId);


}
