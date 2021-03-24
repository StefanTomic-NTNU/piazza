package tdt4145.core;

public class UserOverview {

    private String name;
    private int nbpost;
    private int nbreadposts;

    public UserOverview(String name, int nbreadposts, int nbpost){
        this.name = name;
        this.nbpost = nbreadposts;
        this.nbreadposts = nbpost;
    }

    public String getName() {
        return name;
    }

    public int getNbpost() {
        return nbpost;
    }

    public int getNbreadposts() {
        return nbreadposts;
    }
}
