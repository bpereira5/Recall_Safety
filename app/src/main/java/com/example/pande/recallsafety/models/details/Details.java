package com.example.pande.recallsafety.models.details;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("recallId")
    @Expose
    public String recallId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("start_date")
    @Expose
    public Long startDate;
    @SerializedName("date_published")
    @Expose
    public Long datePublished;
    @SerializedName("category")
    @Expose
    public List<String> category = new ArrayList<String>();
    @SerializedName("panels")
    @Expose
    public List<Panel> panels = new ArrayList<Panel>();

    public String getUrl() {
        return url;
    }

    public String getRecallId() {
        return recallId;
    }

    public String getTitle() {
        return title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getDatePublished() {
        return datePublished;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<Panel> getPanels() {
        return panels;
    }
}