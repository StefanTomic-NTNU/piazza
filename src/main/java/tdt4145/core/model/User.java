package tdt4145.core.model;

/**
 * User class that stores the userID, name, email, and instructor_privileges
 */

public class User {

    private final int userID;
    private final String name;
    private final String email;
    private final boolean instructor_privileges;

    public User(int userID, String name, String email, boolean instructor_privileges) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.instructor_privileges= instructor_privileges;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasInstructor_privileges() {
        return instructor_privileges;
    }
}