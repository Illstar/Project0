package com.revature.PairProject.daos;

import com.revature.PairProject.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {


    //this will break the server on load
    //for the method to get items that belong to a certain user
    //this is the last thing but does not work

    @Query(value = "SELECT * FROM items i where user_id=?1  ", nativeQuery = true)
    List<Item> findByUserId(int userId);


}
