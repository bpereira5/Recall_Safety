package com.example.pande.recallsafety.models.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Panel {

    @SerializedName("panelName")
    @Expose
    public String panelName;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("text")
    @Expose
    public String text;

    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();

    public String getPanelName() {
        return panelName;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<Datum> getData() {
        return data;
    }

    public class Datum {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("fullUrl")
        @Expose
        public String fullUrl;
        @SerializedName("thumbUrl")
        @Expose
        public String thumbUrl;

        public String getTitle() {
            return title;
        }

        public String getFullUrl() {
            return fullUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }
    }
}
