package edu.uga.cs.roommateshoppinglist;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class Item {

    private String itemName;
    private String itemPrice;
    private String itemID;
    private String date;
    private String roommateName;

    //default constructor
    public Item(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Item(String itemName, String itemPrice, String roommateName){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.roommateName = roommateName;
        this.itemID = null;
        this.date = (java.time.LocalDate.now()).toString();
    }

    public String getItemName(){ return itemName; }

    public String getItemPrice(){ return itemPrice; }

    public String getItemID(){ return itemID; }

    public String getDate(){ return date; }

    public String getRoommateName(){ return roommateName; }

    public void setItemName(String itemName){ this.itemName = itemName; }

    public void setItemPrice(String itemPrice){ this.itemPrice = itemPrice; }

    public void setItemID(String itemID){ this.itemID = itemID; }

    public void setDate(String date){ this.date = date; }

    public void setRoommateName(String roommateName) { this.roommateName = roommateName; }
}
