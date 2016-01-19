package com.productions.ortal.familytree.models;

/**
 * Created by user on 1/19/2016.
 */
public class Relation {
    private String person1Id;
    private String person2Id;
    private String relative;

    public Relation(String person1Id, String person2Id, String relative) {
        this.person1Id = person1Id;
        this.person2Id = person2Id;
        this.relative = relative;
    }

    public String getPerson1Id() {
        return person1Id;
    }

    public void setPerson1Id(String person1Id) {
        this.person1Id = person1Id;
    }

    public String getPerson2Id() {
        return person2Id;
    }

    public void setPerson2Id(String person2Id) {
        this.person2Id = person2Id;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }
}
