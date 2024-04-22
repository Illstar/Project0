package com.revature.PairProject.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Table(name="items")
@Component
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String nameOfItem;

    private String typeOfItem;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    //Using this annotation would make a field that doesn't get persisted in the database
    //@Transient
    //private int transientUserId;
    //why would we want this? mostly utility.
    //we could use this to find by userId easier

    /* What is going on here?

    This is  how we establish a PK/Foreign Key relationship.  Every Item has a User it belongs to.

    @ManyToOne - This is saying "Item is the foreign key side of the relationship"

    fetch - defines whether the dependency (User) is eagerly or lazily loaded
    -Eager: loaded ASAP, Lazy: loaded as needed

    cascade - defines what happens to the dependency (item) when the parent (user) is updated
    so with cascade = ALL, if a user is deleted, all records associated with that user will also try to delete
    with cascade = MERGE, cascade only happens for updates.

    @JoinColumn - this is the column that links the two tables together.
    Use the name of the ID field in the parent.

     */

    //Boilerplate code -----------------------------------

    //no args constructor
    public Item() {
    }

    //all args constructor
    public Item(int itemId, String nameOfItem, String typeOfItem, User user) {
        this.itemId = itemId;
        this.nameOfItem = nameOfItem;
        this.typeOfItem = typeOfItem;
        this.user = user;
    }

    //getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfItem = nameOfItem;
    }

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(String typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //toString
    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", nameOfItem='" + nameOfItem + '\'' +
                ", typeOfItem='" + typeOfItem + '\'' +
                ", user=" + user +
                '}';
    }
}
