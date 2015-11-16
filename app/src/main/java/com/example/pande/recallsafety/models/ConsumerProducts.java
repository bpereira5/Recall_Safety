package com.example.pande.recallsafety.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ConsumerProducts {

    @SerializedName("recallId")
    @Expose
    public String recallId;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("category")
    @Expose
    public List<String> category = new ArrayList<String>();

    @SerializedName("date_published")
    @Expose
    public int datePublished;

    @SerializedName("url")
    @Expose
    public String url;

    public String getRecallId() {
        return recallId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getCategory() {
        return category;
    }

    public int getDatePublished() {
        return datePublished;
    }

    public String getUrl() {
        return url;
    }
}

