package com.revature.daos;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    By extending JpaRepository, we get access to various DAO methods
    that we don't need to write.

    JpaRepository takes two generics:
    -The Java Model AND Database table that we're working with
    -The data type of the primary key of that table

 */

@Repository  //one of 4 stereotype annotations -- makes class a bean
public interface UserDAO extends JpaRepository<User, Integer> {

    //I want to be able to find a user by their username
    //since JpaRespository doesn't have that method, we have to
    //make one ourselves.
    //Spring Data IS smart enough to implement this method
    //we just need to define it

    public User findByUsername(String username);



    //Note: The method MUST BE NAMED "findByXYZ" or it won't work

    //How does Spring Data know? It's based on the name of the FIELD in the class

    //ex: findByUsernameAndPassword for multiple fields

}