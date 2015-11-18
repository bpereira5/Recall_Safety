package com.example.pande.recallsafety.models.details;

import io.realm.RealmObject;


public class TrackedRecall extends RealmObject {

    private String recallID;
    private String Title;

    public  TrackedRecall(){}

    public TrackedRecall(String recallID, String title) {
        this.recallID = recallID;
        Title = title;
    }

    public String getRecallID() {
        return recallID;
    }

    public String getTitle() {
        return Title;
    }

    public void setRecallID(String recallID) {
        this.recallID = recallID;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
