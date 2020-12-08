package edu.uga.cs.roommateshoppinglist;

import java.util.ArrayList;

/**
 * This roommate class allows for a roommate object to be accessed.
 */
public class Roommates {

    private String roommateName;
    private String amountPaid;
    private ArrayList<Item> items;

    //default constructor
    public Roommates() { }

    public Roommates(String roommateName, String amountPaid){
        this.roommateName = roommateName;
        this.amountPaid = amountPaid;
    }

    public String getName() { return roommateName; }

    public String getAmountPaid() { return amountPaid; }

    public void setAmountPaid(String amountPaid) { this.amountPaid = amountPaid; }


}
