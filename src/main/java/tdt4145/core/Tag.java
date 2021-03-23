package tdt4145.core;

public class Tag {
    private int tagID;
    private String label;

    public Tag(int tagID, String label){
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
