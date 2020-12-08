package edu.uga.cs.roommateshoppinglist;

/**
 * This User class allows a user object to be accessed.
 */
public class User {

    String userId;
    String name;
    String email;

    public User(){

    }

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
