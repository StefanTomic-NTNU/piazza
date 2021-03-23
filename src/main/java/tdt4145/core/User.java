package tdt4145.core;

public class User {

    private final int userID;
    private final String name;
    private final String email;
    private final boolean instructor_priviligies;

    public User(int userID, String name, String email, boolean instructor_priviligies) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.instructor_priviligies = instructor_priviligies;
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

    public boolean getInstructorPriviligies() {
        return instructor_priviligies;
    }

}
