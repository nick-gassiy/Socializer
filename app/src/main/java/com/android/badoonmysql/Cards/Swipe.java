package com.android.badoonmysql.Cards;

public class Swipe {
    int id;
    int wasVisitedBy;
    int whoWasVisited;
    boolean isLeftSwipe;

    public Swipe(int wasVisitedBy, int whoWasVisited, boolean isLeftSwipe) {
        this.wasVisitedBy = wasVisitedBy;
        this.whoWasVisited = whoWasVisited;
        this.isLeftSwipe = isLeftSwipe;
    }

    public Swipe() {

    }

    public int getId() {
        return id;
    }

    public int getWasVisitedBy() {
        return wasVisitedBy;
    }

    public int getWhoWasVisited() {
        return whoWasVisited;
    }

    public boolean isLeftSwipe() {
        return isLeftSwipe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWasVisitedBy(int wasVisitedBy) {
        this.wasVisitedBy = wasVisitedBy;
    }

    public void setWhoWasVisited(int whoWasVisited) {
        this.whoWasVisited = whoWasVisited;
    }

    public void setLeftSwipe(boolean leftSwipe) {
        isLeftSwipe = leftSwipe;
    }
}
