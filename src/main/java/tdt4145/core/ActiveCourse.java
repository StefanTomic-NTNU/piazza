package tdt4145.core;

public class ActiveCourse {

    private final String term;
    private final int year;
    private final int courseID;
    private final boolean allow_anoymous;


    public ActiveCourse(String term, int year, int courseID, boolean allow_anoymous) {
        this.term = term;
        this.year = year;
        this.courseID = courseID;
        this.allow_anoymous = allow_anoymous;
    }

    public String getTerm() {
        return term;
    }

    public int getYear(){
        return year;
    }

    public int getCourseID(){
        return courseID;
    }

    public boolean isAllow_anoymous(){
        return allow_anoymous;
    }


}
