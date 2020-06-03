package com.android.badoonmysql.Cards;

public class Card {
    private String userID;
    private String userName;
    //private String userMainPhotoUrl;

    public Card(String userID, String userName/*, String userMainPhotoUrl*/) {
        this.userID = userID;
        this.userName = userName;
        //this.userMainPhotoUrl = userMainPhotoUrl;
    }

    public String getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    //public String getUserMainPhotoUrl() {return userMainPhotoUrl;}

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    //public void setUserMainPhotoUrl(String userMainPhotoUrl) { this.userMainPhotoUrl = userMainPhotoUrl; }
}
