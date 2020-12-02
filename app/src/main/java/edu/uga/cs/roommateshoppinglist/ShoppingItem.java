package edu.uga.cs.roommateshoppinglist;

public class ShoppingItem {

    private String name;

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
