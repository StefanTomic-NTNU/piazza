package tdt4145.core.model;

/**
 * Tag class that stores tagid and label
 */
public class Tag {

    private final int tagID;
    private final String label;

    public Tag(int tagID, String label) {
        this.tagID = tagID;
        this.label = label;
    }

    public int getTagID() {
        return tagID;
    }

    public String getLabel() {
        return label;
    }
}
