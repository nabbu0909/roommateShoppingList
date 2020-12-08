package edu.uga.cs.roommateshoppinglist;

public class ShoppingItem {

    private String name;
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ShoppingItem( ) {
        this.name = null;
    }

    public ShoppingItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
