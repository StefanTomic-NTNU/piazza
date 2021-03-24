package tdt4145.core;

public class Course {

    public int courseID;
    public String name;

    public Course(int courseID, String name) {
        this.courseID = courseID;
        this.name = name;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getName() {
        return name;
    }
}
