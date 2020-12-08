package edu.uga.cs.roommateshoppinglist;

/**
 * This Shopping Item class allows for an shopping item object to be accessed.
 */
public class ShoppingItem {

    private String name;
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ShoppingItem(String name) {
        this.name = name;
    }

    public ShoppingItem(){ this.name = null; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
