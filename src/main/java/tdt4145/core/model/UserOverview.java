package tdt4145.core.model;

/**
 * Class that stores the name and number of post created (nbpost) and number of post read by a user (nbreadposts)
 */

public class UserOverview {

    private final String name;
    private final int nbpost;
    private final int nbreadposts;

    public UserOverview(String name, int nbreadposts, int nbpost) {
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

    @Override
    public String toString() {
        return name + "\t" + nbreadposts + "\t" + nbpost;
    }
}
